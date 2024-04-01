package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import mintychochip.genesis.Genesis;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.config.configs.EntityConfig;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.GenomeInstancer;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

public class AnimalGenome {

  @SerializedName("characteristics")
  private List<Characteristic> characteristics;
  @SerializedName("genes")
  private List<Gene> genes;
  @SerializedName("time-alive")
  private long birthTime;
  @SerializedName("name")
  @Nullable
  private String name = null;

  private AnimalGenome(List<Characteristic> characteristics, List<Gene> genes,
      long birthTime) {
    this.characteristics = characteristics;
    this.genes = genes;
    this.birthTime = birthTime;
  }

  public void setName(@Nullable String name) {
    this.name = name;
  }

  @Nullable
  public String getName() {
    return name;
  }

  public static AnimalGenome createInstance(List<Characteristic> characteristics, List<Gene> genes, GenomeInstancer genomeInstancer) {
    if(genes == null) {
      return null;
    }
    return new AnimalGenome(characteristics,genes,System.currentTimeMillis());
  }
  public static AnimalGenome createInstance(EntityType entityType,
      GeneFactory geneFactory) {
    Random random = Genesis.RANDOM;
    ConfigManager configManager = geneFactory.getConfigManager();
    EntityConfig entityConfig = configManager.getEntityConfig();
    List<CharacteristicTrait> allCharacteristicTraits = entityConfig.getAllCharacteristicTraits(
        entityType);

    List<GeneTrait> allGeneTraits = entityConfig.getAllGeneTraits(entityType);
    List<Gene> genes = new ArrayList<>(entityConfig.getAllGeneTraits(entityType).stream()
        .map(trait -> geneFactory.createInstance(trait, entityType))
        .filter(baseTrait -> baseTrait instanceof Gene gene && gene.isConserved())
        .map(bt -> (Gene) bt).toList());
//    if (rarity == Rarity.ARTIFACT && entityConfig
//        .isTraitEnabledForEntity(GeneticAttribute.PARTICLE, entityType)) {
//      genes.add(geneFactory.createInstance(GeneticAttribute.PARTICLE, entityType, rarity));
//    }configManager.getSettingsConfig().getMutationsByRarity(rarity);
    int mutations = 3;
    if (mutations == -1) {
      mutations = 3;
    }
    for (int i = 0; i < mutations; i++) {
      int j = random.nextInt(allGeneTraits.size());
      GeneTrait geneTrait = allGeneTraits.get(j);
      if (geneTrait != null) {
        if (!geneFactory.getTraitFetcher().geneTraitInList(genes, geneTrait)) {
          if(geneFactory.createInstance(geneTrait, entityType) instanceof Gene gene) {
            genes.add(gene);
          }
        }
      }
    }
    return new AnimalGenome(null, genes, System.currentTimeMillis());
  }
  public List<Gene> getGenes() {
    return genes;
  }

  public List<Characteristic> getCharacteristics() {
    return characteristics;
  }

  public long getBirthTime() {
    return birthTime;
  }
}