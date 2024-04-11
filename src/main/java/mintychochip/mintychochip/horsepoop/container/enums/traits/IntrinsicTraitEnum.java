package mintychochip.mintychochip.horsepoop.container.enums.traits;

import mintychochip.mintychochip.horsepoop.api.markers.Intrinsic;
import mintychochip.mintychochip.horsepoop.metas.MetaType;

public enum IntrinsicTraitEnum implements Intrinsic {
  GENDER("gender", MetaType.ENUM, "Only heterosexual (male and female) breeding produces offspring"),
  RARITY("rarity", MetaType.WEIGHTED_ENUM,"Higher rarity increases the traits that can appear on an animal"),
  TEMPERANCE("temperament", MetaType.ENUM, "asdasd");
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
