package mintychochip.mintychochip.horsepoop.container.enums.characteristics;

import mintychochip.mintychochip.horsepoop.container.CharacteristicTrait;

public enum GenericCharacteristicTrait implements CharacteristicTrait {
  GENDER("gender", MetaType.ENUM, "gender of the animal"),
  RARITY("rarity", MetaType.ENUM,"rarity of the animal"),
  HEIGHT("height", MetaType.NUMERIC,"Height of the animal"),
  COCK_SIZE("penis", MetaType.NUMERIC,"biggus");
  private final String key;
  private final String description;
  private final MetaType metaType;
  GenericCharacteristicTrait(String key, MetaType metaType, String description) {
    this.key = key;
    this.metaType = metaType;
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
  public MetaType getMetaType() {
    return metaType;
  }
}
