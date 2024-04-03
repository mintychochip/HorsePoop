package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.annotations.SerializedName;
import mintychochip.mintychochip.horsepoop.metas.Meta;

public final class BaseTrait<U extends Trait> {
  @SerializedName("value")
  private final String value; //value in json
  private final Meta<U> meta;

  private BaseTrait(String value, Meta<U> meta) {
    this.value = value;
    this.meta = meta;
  }

  public static <U extends Trait> BaseTrait<U> create(String value, Meta<U> meta, Crosser<U> crosser){
    return new BaseTrait<>(value,meta);
  }
  public Meta<U> getMeta() {
    return meta;
  }

  public String getValue() {
    return value;
  }
}
