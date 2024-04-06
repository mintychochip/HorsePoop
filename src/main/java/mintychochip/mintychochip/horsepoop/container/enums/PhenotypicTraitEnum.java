package mintychochip.mintychochip.horsepoop.container.enums;

import mintychochip.mintychochip.horsepoop.api.Phenotypic;
import mintychochip.mintychochip.horsepoop.metas.MetaType;

public enum PhenotypicTraitEnum implements Phenotypic {
  HEIGHT("height", MetaType.CROSSABLE_DOUBLE, "Arbitrary height of the animal");

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
