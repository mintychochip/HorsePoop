package mintychochip.mintychochip.horsepoop.container.enums.attributes.specific;

import mintychochip.mintychochip.horsepoop.container.Gene.GeneType;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.TraitType;

public enum GeneticAttribute implements Trait {
  CONSTITUTION("constitution", GeneType.NUMERIC, "asd"),
  SPEED("speed", GeneType.NUMERIC, "asd"),
  PARTICLE("particle", GeneType.ENUM, "asd"),
  STRENGTH("strength", GeneType.NUMERIC, "asd"),
  JUMP_STRENGTH("jumping-strength", GeneType.NUMERIC, "asd"),
  FIRE_RESISTANT("fire", GeneType.MENDELIAN, "asd"), //lava and fire walking
  GLOW("glow", GeneType.MENDELIAN, "asd"), //enum
  REGEN("regeneration", GeneType.MENDELIAN, "asd"),
  ICE_WALKER("ice-walker", GeneType.MENDELIAN,
      "When riding your horse on ice, generate a blanket of frosted ice beneath you."),
  SLOWFALL("slowfall", GeneType.MENDELIAN, "asd"),
  INVISIBLE("unseen", GeneType.MENDELIAN, "asd"),
  ATTRACTIVE("attractive", GeneType.MENDELIAN, "asd"),
  EGG_LAYER("egg", GeneType.MENDELIAN, "asd"),
  DESERTWALKING("desert-haste", GeneType.MENDELIAN, "asd"),
  SILENT("silence", GeneType.MENDELIAN, "asd"),
  ARMOR("base-armor", GeneType.MENDELIAN, "asd");

  private final String key;
  private final GeneType geneType;
  private final String description;
  GeneticAttribute(String key, GeneType geneType, String description) {
    this.key = key;
    this.geneType = geneType;
    this.description = description;
  }

  @Override
  public String getNamespacedKey() {
    return TraitType.HORSE.getKey() + ":" + key;
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
