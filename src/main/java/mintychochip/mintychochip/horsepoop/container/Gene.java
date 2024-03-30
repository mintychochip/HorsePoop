package mintychochip.mintychochip.horsepoop.container;

import static net.md_5.bungee.chat.ComponentSerializer.toJson;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import mintychochip.genesis.Genesis;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.genesis.util.MathUtil;
import mintychochip.genesis.util.Rarity;
import mintychochip.mintychochip.horsepoop.config.AnimalTraitWrapper;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.HorseMarker;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;
import org.bukkit.entity.EntityType;

import org.jetbrains.annotations.NotNull;


public class Gene {

  public enum GeneType { //determines the instancing sequence they will take
    ENUM, //list of values the gene can take
    MENDELIAN, //classical mendelian
    NUMERIC, //range of values the gene can take, for example, speed can have different numerical value,
    INTEGER
  }
  @SerializedName("trait")
  private final String trait;
  @SerializedName("value")
  private final String value;
  @SerializedName("conserved")
  private final boolean conserved; //the animal retains this after breeding always
  @SerializedName("crossable")
  private final boolean crossable; //prevents animal crossing from retaining this gene
  private Gene(String trait, String value, boolean conserved, boolean crossable) {
    this.trait = trait;
    this.value = value;
    this.conserved = conserved;
    this.crossable = crossable;
  }

  public static Gene createInstance(@NotNull Trait trait, @NotNull String value, boolean conserved,
      boolean crossable, GeneFactory geneFactory) {
    return new Gene(HorsePoop.GSON.toJson(trait), value, conserved, crossable);
  }

  public static Gene createInstance(@NotNull Trait trait, @NotNull EntityType entityType,
      @NotNull Rarity rarity, @NotNull GeneFactory geneFactory) {
    ConfigManager configManager = geneFactory.getConfigManager();
    AnimalTraitWrapper traitWrapper = configManager.getEntityConfig().getTrait(trait, entityType);
    if (traitWrapper == null) {
      return null;
    }
    if (!configManager.getEntityConfig().isEntityEnabled(entityType)) {
      return null;
    }
    if (!configManager.getEntityConfig().isTraitEnabled(trait, entityType)) {
      return null;
    }
    String traitString = HorsePoop.GSON.toJson(trait);
    boolean crossable = traitWrapper.crossable();
    boolean conserved = traitWrapper.conserved();
    String value = GeneFactory.generateValue(traitWrapper, configManager,rarity);
    if (value == null) {
      return null;
    }
    return new Gene(traitString, value, conserved, crossable);
  }

  public boolean isCrossable() {
    return crossable;
  }

  public Trait getTrait() {
    return HorsePoop.GSON.fromJson(trait, Trait.class);
  }

  public String getValue() {
    return value;
  }

  public boolean isConserved() {
    return conserved;
  }
  public Gene crossGene(@NotNull Gene mother, EntityType entityType) {
    GeneType motherGeneType = mother.getTrait().getGeneType();
    if (this.getTrait().getGeneType() != motherGeneType) {
      return null;
    }
    Trait trait = this.getTrait();
    if (trait != mother.getTrait()) {
      return null;
    }
    if (!(this.isCrossable() && mother.isCrossable())) {
      return null;
    }
      Gson gson = new Gson();
      String value = null;
      switch (motherGeneType) {
        case NUMERIC -> {
          Double fatherVal = gson.fromJson(this.value, double.class);
          Double motherVal = gson.fromJson(mother.getValue(), double.class);
          value = gson.toJson(
              fatherVal.equals(motherVal) ? fatherVal : rollNumberVal(fatherVal, motherVal));
        }
        case ENUM -> {
          //not gonna write it no enum classes yet
        }
        case MENDELIAN -> {
          value = gson.toJson(this.getMendelian().crossGenes(mother.getMendelian()));
        }
        case INTEGER -> {
          Integer fatherVal = gson.fromJson(this.value, int.class);
          Integer motherVal = gson.fromJson(mother.getValue(), int.class);
          value = gson.toJson(
              fatherVal.equals(motherVal) ? fatherVal : rollNumberVal(fatherVal, motherVal));
        }
      }
      if(value == null) {
        return null;
      }
      return new Gene(HorsePoop.GSON.toJson(trait),value,this.conserved,this.crossable);
  }

  public MendelianGene getMendelian() {
    if (this.getTrait().getGeneType() != GeneType.MENDELIAN) {
      return null;
    }
    return new Gson().fromJson(value, MendelianGene.class);
  }


  @SuppressWarnings("unchecked")

  private <T extends Number> T rollNumberVal(T a, T b) { //this name and function sucks
    T min;
    T max;
    if (a instanceof Double && b instanceof Double) {
      min = (T) (Double) Math.min(a.doubleValue(), b.doubleValue());
      max = (T) (Double) Math.max(a.doubleValue(), b.doubleValue());
      return (T) (Double) Genesis.RANDOM.nextDouble(min.doubleValue(), max.doubleValue());
    } else if (a instanceof Integer || b instanceof Integer) {
      min = (T) (Integer) Math.min(a.intValue(), b.intValue());
      max = (T) (Integer) Math.max(a.intValue(), b.intValue());
      return (T) (Integer) Genesis.RANDOM.nextInt(min.intValue(), max.intValue());
    } else {
      throw new IllegalArgumentException(
          "Unsupported type. Only double and int are supported.");
    }
  }

  @Override
  public String toString() {
    Gson gson = new Gson();
    return switch (this.getTrait().getGeneType()) {
      case MENDELIAN -> gson.fromJson(this.value, MendelianGene.class).toString();
      case NUMERIC ->
          Double.toString(MathUtil.roundToDecimals(gson.fromJson(this.value, Double.class), 3));
      case ENUM -> this.value;
      case INTEGER -> Integer.toString(gson.fromJson(this.value, Integer.class));
    };
  }
}
