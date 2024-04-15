package mintychochip.mintychochip.horsepoop.container.enums.traits;

import mintychochip.mintychochip.horsepoop.api.markers.Gene;
import mintychochip.mintychochip.horsepoop.metas.MetaType;

public enum GenericGene implements Gene {
  YIELD("yield", MetaType.CROSSABLE_INTEGER, "Determines the yield, based on the mob type for mob drops or farmable items."),
  TOXIC("toxic",MetaType.MENDELIAN, "asd");
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
