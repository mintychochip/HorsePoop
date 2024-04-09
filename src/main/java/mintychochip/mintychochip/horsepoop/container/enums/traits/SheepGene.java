package mintychochip.mintychochip.horsepoop.container.enums.traits;

import mintychochip.mintychochip.horsepoop.api.Gene;
import mintychochip.mintychochip.horsepoop.metas.MetaType;

public enum SheepGene implements Gene { //traits designed for sheep
  RED("red", MetaType.MENDELIAN,
      "Encodes for sheep phenotypic color, reference the wiki for the different color combinations."),
  BLUE("blue", MetaType.MENDELIAN,
      "Encodes for sheep phenotypic color, reference the wiki for the different color combinations."),
  GREEN("green", MetaType.MENDELIAN,
      "Encodes for sheep phenotypic color, reference the wiki for the different color combinations."),
  WHITE_OVERRIDE("override", MetaType.MENDELIAN,
      "Epistatic "), //dominant phenotypes always exhibit white, allows for masking of colors
  BRIGHTNESS("brightness", MetaType.INTEGER, "Provided 'override' is recessive, if there are no color genes, then the color will be selected on the brightness.");

  // numeric
  private final String key;
  private final MetaType metaType;
  private final String description;

  SheepGene(String key, MetaType metaType, String description) {
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
