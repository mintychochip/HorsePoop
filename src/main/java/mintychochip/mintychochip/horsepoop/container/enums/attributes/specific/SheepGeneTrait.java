package mintychochip.mintychochip.horsepoop.container.enums.attributes.specific;

import mintychochip.mintychochip.horsepoop.container.GeneTrait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.TraitType;

public enum SheepGeneTrait implements GeneTrait { //traits designed for sheep
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

  SheepGeneTrait(String key, MetaType metaType, String description) {
    this.key = key;
    this.metaType = metaType;
    this.description = description;
  }
  @Override
  public String getNamespacedKey() {
    return TraitType.SHEEP.getKey() + ":" + key;
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
  public MetaType getMetaType() {
    return metaType;
  }
}
