package mintychochip.mintychochip.horsepoop.container.enums.characteristics;

import mintychochip.mintychochip.horsepoop.container.CharacteristicTrait;
import mintychochip.mintychochip.horsepoop.container.ValueType;

public enum GenericCharacteristicTrait implements CharacteristicTrait {
  GENDER("gender", ValueType.ENUM, "gender of the animal"),
  RARITY("rarity",ValueType.ENUM,"rarity of the animal");
  private final String key;
  private final String description;
  private final ValueType valueType;
  GenericCharacteristicTrait(String key, ValueType valueType, String description) {
    this.key = key;
    this.valueType = valueType;
    this.description = description;
  }
  @Override
  public String getNamespacedKey() {
    return key;
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
