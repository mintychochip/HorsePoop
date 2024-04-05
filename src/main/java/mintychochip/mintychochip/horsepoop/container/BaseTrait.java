package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.annotations.SerializedName;
import mintychochip.mintychochip.horsepoop.api.Generator;
import mintychochip.mintychochip.horsepoop.api.TraitCrosser;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.metas.Meta;

public final class BaseTrait<U extends TraitEnum> {
  @SerializedName("value")
  private final String value; //value in json
  private final Meta<U> meta;

  private BaseTrait(String value, Meta<U> meta) {
    this.value = value;
    this.meta = meta;
  }

  public static <U extends TraitEnum> BaseTrait<U> create(String value, Meta<U> meta, TraitCrosser<U> traitCrosser){
    return new BaseTrait<>(value,meta);
  }
  public static <U extends TraitEnum> BaseTrait<U> create(String value, Meta<U> meta, Generator<U> generator) {
    return new BaseTrait<>(value,meta);
  }
  public Meta<U> getMeta() {
    return meta;
  }

  public String getValue() {
    return value;
  }
}
