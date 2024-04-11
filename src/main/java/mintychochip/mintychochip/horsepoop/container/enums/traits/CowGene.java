package mintychochip.mintychochip.horsepoop.container.enums.traits;

import mintychochip.mintychochip.horsepoop.api.markers.Gene;
import mintychochip.mintychochip.horsepoop.metas.MetaType;

public enum CowGene implements Gene {

  MOOSHROOM_GENE("mooshroom", MetaType.MENDELIAN, "asd"),
  STRAWBERRY_MILK("strawberry-milk", MetaType.POLYGENIC_MENDELIAN,
      "asd"); //gives a different item if it has the gene
  private final String key;
  private final MetaType metaType;
  private final String description;

  CowGene(String key, MetaType metaType, String description) {
    this.key = key;
    this.metaType = metaType;
    this.description = description;
  }

  @Override
  public String getNamespacedKey() {
    return this.key;
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
