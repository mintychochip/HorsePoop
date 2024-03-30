package mintychochip.mintychochip.horsepoop.container.enums.attributes;

import mintychochip.mintychochip.horsepoop.container.Gene.GeneType;
import mintychochip.mintychochip.horsepoop.container.Trait;

public enum GenericTrait implements Trait {
  YIELD("yield", GeneType.INTEGER, "Determines the yield, based on the mob type for mob drops or farmable items."),
  TEMPERANCE("temperance", GeneType.ENUM, "asd");
  private final String key;

  private final String description;
  private final GeneType geneType;

  GenericTrait(String key, GeneType geneType, String description) {
    this.key = key;
    this.description = description;
    this.geneType = geneType;
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
  public String getShortDescription() {
    return description;
  }

  @Override
  public GeneType getGeneType() {
    return geneType;
  }
}
