package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import mintychochip.genesis.util.MathUtil;

public abstract class BaseTrait {
  @SerializedName("value")
  protected final String value;

  @SerializedName("trait")
  protected final String trait;

  protected BaseTrait(String value, String trait) {
    this.value = value;
    this.trait = trait;
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
