package mintychochip.mintychochip.horsepoop.container;

import static net.md_5.bungee.chat.ComponentSerializer.toJson;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import mintychochip.genesis.Genesis;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.genesis.util.MathUtil;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.HorseMarker;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.config.EntityConfig;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianAllele;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianType;
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
  @SerializedName("gene-type")
  private final GeneType geneType;

  private Gene(String trait, String value, boolean conserved, boolean crossable,
      GeneType geneType) {
    this.trait = trait;
    this.value = value;
    this.conserved = conserved;
    this.crossable = crossable;
    this.geneType = geneType;
  }

  public static Gene createInstance(@NotNull Trait trait, @NotNull String value, boolean conserved,
      boolean crossable, @NotNull GeneType geneType, GeneFactory geneFactory) {
    return new Gene(HorsePoop.GSON.toJson(trait), value, conserved, crossable, geneType);
  }

  public static Gene createInstance(@NotNull Trait trait, @NotNull EntityType entityType,
      @NotNull ConfigManager configManager, @NotNull GeneFactory geneFactory) {
    GenesisConfigurationSection attribute = configManager.getEntityConfig()
        .getAttribute(trait, entityType);
    if (attribute.isNull()) {
      return null;
    }
    if (!configManager.getEntityConfig().isEntityEnabled(entityType)) {
      return null;
    }
    if (!configManager.getEntityConfig().isTraitEnabled(trait, entityType)) {
      return null;
    }
    String traitString = HorsePoop.GSON.toJson(trait);
    boolean crossable = attribute.getBoolean(HorseMarker.crossable);
    boolean conserved = attribute.getBoolean(HorseMarker.conserved);
    GeneType geneType = attribute.enumFromSection(GeneType.class, HorseMarker.gene_type);
    GenesisConfigurationSection meta = configManager.getEntityConfig().getMeta(trait, entityType);
    if (meta.isNull()) {
      return null;
    }
    String value = GeneFactory.generateValue(trait, meta, geneType);
    if (value == null) {
      return null;
    }
    return new Gene(traitString, value, conserved, crossable, geneType);
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

  public GeneType getGeneType() {
    return geneType;
  }

  public Gene crossGene(@NotNull Gene mother, EntityType entityType) {
    Gene.GeneType motherGeneType = mother.getGeneType();
    if (this.geneType != motherGeneType) {
      return null;
    }
    Trait trait = this.getTrait();
    if (trait != mother.getTrait()) {
      return null;
    }
    if (this.isCrossable() && mother.isCrossable()) {
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
    }
    if(value == null) {
        return null;
    }
    return new Gene(HorsePoop.GSON.toJson(trait),value,this.conserved,this.crossable,geneType);
  }

  public MendelianGene getMendelian() {
    if (geneType != GeneType.MENDELIAN) {
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
    return switch (this.geneType) {
      case MENDELIAN -> gson.fromJson(this.value, MendelianGene.class).toString();
      case NUMERIC ->
          Double.toString(MathUtil.roundToDecimals(gson.fromJson(this.value, Double.class), 3));
      case ENUM -> this.value;
      case INTEGER -> Integer.toString(gson.fromJson(this.value, Integer.class));
    };
  }
}
