package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.annotations.SerializedName;
import mintychochip.mintychochip.horsepoop.metas.Meta;

public class BaseTrait<U extends Trait,T extends Meta<U>> {
  @SerializedName("value")
  protected final String value; //value in json
  private final T meta;

  protected BaseTrait(String value, T meta) {
    this.value = value;
    this.meta = meta;
  }

  public T getMeta() {
    return meta;
  }

  public String getValue() {
    return value;
  }
}
