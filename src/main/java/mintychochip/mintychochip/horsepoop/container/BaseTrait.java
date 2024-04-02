package mintychochip.mintychochip.horsepoop.container;

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

}
