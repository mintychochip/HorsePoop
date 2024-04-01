package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import mintychochip.genesis.util.MathUtil;
import mintychochip.mintychochip.horsepoop.config.TraitMeta;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;

public class BaseTrait {
  @SerializedName("value")
  protected final String value;

  @SerializedName("trait")
  protected final String trait;

  protected BaseTrait(String value, String trait) {
    this.value = value;
    this.trait = trait;
  }

  public static BaseTrait createInstance(Trait trait, TraitMeta traitMeta, GeneFactory geneFactory) {
    if(traitMeta == null) {
      return null;
    }
    String traitString = geneFactory.getTraitFetcher().toJson(trait);
    String value = geneFactory.getValueFactory().generateValue(trait,traitMeta);

    if(traitString == null) {
      throw new RuntimeException("Trait String is null");
    }
    if(value == null) {
      throw new RuntimeException("Value is null");
    }
    return new BaseTrait(value,traitString);
  }

  public String getTrait() {
    return trait;
  }

  public String getValue() {
    return value;
  }
  public String toString(TraitFetcher traitFetcher) {
    Gson gson = new Gson();
    return switch (traitFetcher.getGeneTrait(trait).getValueType()) {
      case MENDELIAN -> gson.fromJson(this.value, MendelianGene.class).toString();
      case NUMERIC ->
          Double.toString(MathUtil.roundToDecimals(gson.fromJson(this.value, Double.class), 3));
      case ENUM, WEIGHTED_ENUM -> this.value;
      case INTEGER -> Integer.toString(gson.fromJson(this.value, Integer.class));
    };
  }
}
