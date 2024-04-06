package mintychochip.mintychochip.horsepoop.container.enums.attributes.specific;

import mintychochip.mintychochip.horsepoop.api.Gene;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.TraitType;
import mintychochip.mintychochip.horsepoop.metas.MetaType;

public enum CowGene implements Gene {

  MOOSHROOM_GENE("mooshroom", MetaType.MENDELIAN, "asd"),
  STRAWBERRY_MILK("strawberry-milk", MetaType.MENDELIAN,
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
    return TraitType.COW.getKey() + ":" + this.key;
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
