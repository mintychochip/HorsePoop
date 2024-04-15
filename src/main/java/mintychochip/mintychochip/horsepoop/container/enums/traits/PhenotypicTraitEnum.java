package mintychochip.mintychochip.horsepoop.container.enums.traits;

import mintychochip.mintychochip.horsepoop.api.markers.Phenotypic;
import mintychochip.mintychochip.horsepoop.metas.MetaType;

public enum PhenotypicTraitEnum implements Phenotypic {
  HEIGHT("height", MetaType.CROSSABLE_DOUBLE, "Arbitrary height of the animal"),
  CONSTITUTION("constitution", MetaType.DOUBLE, "asd"),
  SPEED("speed", MetaType.DOUBLE, "asd"),
  STRENGTH("strength", MetaType.CROSSABLE_DOUBLE, "asd"),
  JUMP_STRENGTH("jumping", MetaType.DOUBLE, "asd"),
  PARROT_FEATHER_COLOR("feather-color",MetaType.ENUM,"Parrot feather color");
  private final String key;
  private final String description;
  private final MetaType metaType;

  PhenotypicTraitEnum(String key, MetaType metaType, String description) {
    this.key = key;
    this.metaType = metaType;
    this.description = description;
  }

  @Override
  public String getNamespacedKey() {
    return null;
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
