package mintychochip.mintychochip.horsepoop.container.enums.characteristics;

import mintychochip.mintychochip.horsepoop.api.Intrinsic;
import mintychochip.mintychochip.horsepoop.metas.MetaType;

public enum IntrinsicTraitEnum implements Intrinsic {
  GENDER("gender", MetaType.ENUM, "gender of the animal"),
  RARITY("rarity", MetaType.WEIGHTED_ENUM,"rarity of the animal");
  private final String key;
  private final String description;
  private final MetaType metaType;
  IntrinsicTraitEnum(String key, MetaType metaType, String description) {
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
  public String getDescription() {
    return description;
  }
  @Override
  public MetaType getMetaType() {
    return metaType;
  }
}
