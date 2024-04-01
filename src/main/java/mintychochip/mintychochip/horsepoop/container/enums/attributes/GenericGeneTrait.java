package mintychochip.mintychochip.horsepoop.container.enums.attributes;

import mintychochip.mintychochip.horsepoop.container.GeneTrait;
import mintychochip.mintychochip.horsepoop.container.ValueType;

public enum GenericGeneTrait implements GeneTrait {
  YIELD("yield", ValueType.INTEGER, "Determines the yield, based on the mob type for mob drops or farmable items."),
  TEMPERANCE("temperance", ValueType.ENUM, "asd"),
  FEATHER_COLOR("feather-color", ValueType.ENUM,"asasd");
  private final String key;
  private final String description;
  private final ValueType valueType;

  GenericGeneTrait(String key, ValueType valueType, String description) {
    this.key = key;
    this.description = description;
    this.valueType = valueType;
  }
  @Override
  public String getNamespacedKey() {
    return TraitType.GENERIC.getKey() + ":" + key;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public String getShortDescription() {
    return description;
  }

  @Override
  public ValueType getValueType() {
    return valueType;
  }
}
