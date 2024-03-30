package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.util.stream.Collectors;
import mintychochip.genesis.Genesis;
import mintychochip.genesis.util.Rarity;
import mintychochip.genesis.util.WeightedRandom;
import mintychochip.mintychochip.horsepoop.config.AnimalTraitWrapper;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.config.configs.EntityConfig;
import mintychochip.mintychochip.horsepoop.container.Gene.GeneType;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.GeneticAttribute;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;
import mintychochip.mintychochip.horsepoop.listener.display.RGB;
import mintychochip.mintychochip.horsepoop.util.GeneUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

import java.util.*;

public class AnimalGenome {

  public enum Gender {
    MALE('♂'),
    FEMALE('♀');

    private final char unicode;

    Gender(char unicode) {
      this.unicode = unicode;
    }

    public Component getUnicode() {
      TextColor textColor = this == FEMALE ? TextColor.color(new RGB(255, 192, 203))
          : TextColor.color(NamedTextColor.BLUE);
      return Component.text(unicode).color(textColor);
    }

    @Override
    public String toString() {
      return super.toString();
    }
  }

  @SerializedName("genes")
  private List<Gene> genes;
  @SerializedName("gender")
  private Gender gender;
  @SerializedName("time-alive")
  private long birthTime;
  @SerializedName("rarity")
  private Rarity rarity;

  private AnimalGenome(List<Gene> genes, Gender gender, long birthTime, Rarity rarity) {
    this.genes = genes;
    this.gender = gender;
    this.birthTime = birthTime;
    this.rarity = rarity;
  }

  public static AnimalGenome createInstance(List<Gene> genes, Gender gender, Rarity rarity) {
    return new AnimalGenome(genes, gender, System.currentTimeMillis(), rarity);
  }

  public static AnimalGenome createInstance(EntityType entityType,
      GeneFactory geneFactory) {
    Random random = Genesis.RANDOM;
    ConfigManager configManager = geneFactory.getConfigManager();
    EntityConfig entityConfig = configManager.getEntityConfig();
    Rarity rarity = configManager.getRandomWeight().chooseOne();

    List<AnimalTraitWrapper> traitWrappers = entityConfig
        .getEntityTypeTraitMap().get(entityType);
    List<Gene> genes = new ArrayList<>(traitWrappers.stream()
        .filter(AnimalTraitWrapper::conserved)
        .map(trait -> geneFactory.createInstance(trait, entityType, rarity)).toList());

    if (rarity == Rarity.ARTIFACT && entityConfig
        .isTraitEnabled(GeneticAttribute.PARTICLE, entityType)) {
      genes.add(geneFactory.createInstance(GeneticAttribute.PARTICLE, entityType, rarity));
    }

    Bukkit.broadcastMessage(rarity.toString());
    int mutations = configManager.getSettingsConfig().getMutationsByRarity(rarity);
    if (mutations == -1) {
      mutations = 3;
    }

    for (int i = 0; i < mutations; i++) {
      int j = random.nextInt(traitWrappers.size());
      Trait traitFromWrapper = entityConfig.getTraitFromWrapper(traitWrappers.get(j));
      if (!GeneUtil.isTraitInList(genes, traitFromWrapper)) {
        genes.add(geneFactory.createInstance(traitFromWrapper, entityType, rarity));
      }
    }

    return new AnimalGenome(genes, random.nextBoolean() ? Gender.MALE : Gender.FEMALE,
        System.currentTimeMillis(), rarity);
  }

  private boolean isTraitInList(List<Gene> genes, Trait trait) {
    for (Gene gene : genes) {
      if (trait == gene.getTrait()) {
        return true;
      }
    }
    return false;
  }

  public AnimalGenome crossGenome(AnimalGenome mother, EntityType entityType,
      GeneFactory geneFactory) {
    List<Gene> motherGenes = mother.getGenes();
    ConfigManager configManager = geneFactory.getConfigManager();
    Random random = Genesis.RANDOM;
    //this first step crosses all 'conserved locus'
    List<Gene> genes = new ArrayList<>(this.genes.stream()
        .filter(gene -> gene.isConserved() && this.isTraitInList(motherGenes, gene.getTrait()))
        .map(gene -> gene.crossGene(mother.getGeneFromTrait(gene.getTrait()), entityType))
        .toList());
    //second step crosses all 'nonconserved'
    List<Gene> matchingNonConservedList = this.genes.stream().filter(gene ->
            !this.isTraitInList(genes, gene.getTrait()) && !gene.isConserved())
        .filter(gene -> mother.getGeneFromTrait(gene.getTrait()) != null && gene.isCrossable())
        .toList();
    //adds all if they do not return a null
    genes.addAll(matchingNonConservedList.stream().map(
            gene -> gene.crossGene(mother.getGeneFromTrait(gene.getTrait()), entityType))
        .filter(
            Objects::nonNull).toList());
    //
    Rarity rarity = geneFactory.getConfigManager().getRandomWeight().chooseOne();
    double recombinanceChance = configManager.getSettingsConfig().getRecombinanceChance();
    if (recombinanceChance < 0) {
      recombinanceChance = 0.5;
    }
    int maxCount = configManager.getSettingsConfig().getMutationsByRarity(rarity);
    if (maxCount < 0) {
      maxCount = 1;
    }
    //third step for all unique traits, we are given a chance to roll for recombinance
    int recombinanceCount = 0;
    for (Gene gene : uniqueGenes(mother)) {
      if (gene.isCrossable()) {
        if (random.nextDouble() <= recombinanceChance && recombinanceCount <= maxCount) {
          Gene generatedGene = gene.crossGene(
              geneFactory.createInstance(gene.getTrait(), entityType, rarity), entityType);
          if (generatedGene != null) {
            genes.add(generatedGene);
            Bukkit.broadcastMessage(gene.getTrait().getKey() + "successful added");
            recombinanceCount++;
          }
        }
      }
    }
    //fourth step accoutn for deletions
    double deletionChance = 0.5;
    return new AnimalGenome(genes, random.nextBoolean() ? Gender.MALE : Gender.FEMALE,
        System.currentTimeMillis(), rarity);
  }

  private Set<Gene> uniqueGenes(AnimalGenome mother) {
    Map<Trait, Gene> combinedAttributes = new HashMap<>(this.getAttributes());
    combinedAttributes.putAll(mother.getAttributes());

    return combinedAttributes.entrySet().stream()
        .filter(entry -> {
          Trait trait = entry.getKey();
          Gene fatherGene = this.getGeneFromTrait(trait);
          Gene motherGene = mother.getGeneFromTrait(trait);
          return (fatherGene == null && motherGene != null) || (fatherGene != null
              && motherGene == null);
        })
        .map(Map.Entry::getValue)
        .collect(Collectors.toSet());
  }

  public Map<Trait, Gene> getAttributes() {
    Map<Trait, Gene> attributes = new HashMap<>();
    for (Gene gene : genes) {
      attributes.put(gene.getTrait(), gene);
    }
    return attributes;
  }

  public List<Gene> getGenes() {
    return genes;
  }

  public double getNumericAttribute(GeneticAttribute geneticAttribute) {
    if (geneticAttribute == GeneticAttribute.PARTICLE) {
      return -1;
    }
    return new Gson().fromJson(this.getGeneFromTrait(geneticAttribute).getValue(), double.class);
  }

  public Gene getGeneFromTrait(Trait trait) {
    for (Gene gene : genes) {
      if (trait == gene.getTrait()) {
        return gene;
      }
    }
    return null;
  }

  public Rarity getRarity() {
    return rarity;
  }

  public Gender getGender() {
    return gender;
  }

  public long getBirthTime() {
    return birthTime;
  }
}