package mintychochip.mintychochip.horsepoop.container.enums.attributes;

import mintychochip.mintychochip.horsepoop.api.Gene;
import mintychochip.mintychochip.horsepoop.metas.MetaType;

public enum GenericGene implements Gene {
  YIELD("yield", MetaType.CROSSABLE_INTEGER, "Determines the yield, based on the mob type for mob drops or farmable items."),
  TEMPERANCE("temperance", MetaType.ENUM, "asd"),
  FEATHER_COLOR("feather-color", MetaType.ENUM,"asasd");
  private final String key;
  private final String description;
  private final MetaType metaType;

  GenericGene(String key, MetaType metaType, String description) {
    this.key = key;
    this.description = description;
    this.metaType = metaType;
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
  public String getDescription() {
    return description;
  }

  @Override
  public MetaType getMetaType() {
    return metaType;
  }
}
