package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import mintychochip.mintychochip.horsepoop.config.TraitMeta;

public class BaseTrait<T extends TraitMeta> {
  @SerializedName("value")
  protected final String value;

  @SerializedName("trait")
  protected final String trait;
  private final T meta;
  protected BaseTrait(String value, String trait, T meta) {
    this.value = value;
    this.trait = trait;
    this.meta = meta;
  }

  public T getMeta() {
    return meta;
  }

  public static <T extends TraitMeta> BaseTrait<T> createInstance(String value, String trait, T meta, TraitGenerator<T> generator) {
    return new BaseTrait<>(value,trait,meta);
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
