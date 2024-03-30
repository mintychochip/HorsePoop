package mintychochip.mintychochip.horsepoop.container.enums.attributes.specific;

import mintychochip.mintychochip.horsepoop.container.Gene.GeneType;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.TraitType;

public enum CowTrait implements Trait {

  MOOSHROOM_GENE("mooshroom", GeneType.MENDELIAN, "asd"),
  STRAWBERRY_MILK("strawberry-milk", GeneType.MENDELIAN,
      "asd"); //gives a different item if it has the gene
  private final String key;

  private final GeneType geneType;
  private final String description;

  CowTrait(String key, GeneType geneType, String description) {
    this.key = key;
    this.geneType = geneType;
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
  public String getShortDescription() {
    return description;
  }

  @Override
  public GeneType getGeneType() {
    return geneType;
  }
}
