package mintychochip.mintychochip.horsepoop.container.enums.attributes.specific;

import mintychochip.mintychochip.horsepoop.container.GeneTrait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.TraitType;

public enum GeneticAttribute implements GeneTrait {
  CONSTITUTION("constitution", MetaType.NUMERIC, "asd"),
  SPEED("speed", MetaType.NUMERIC, "asd"),
  PARTICLE("particle", MetaType.ENUM, "asd"),
  STRENGTH("strength", MetaType.NUMERIC, "asd"),
  JUMP_STRENGTH("jumping-strength", MetaType.NUMERIC, "asd"),
  FIRE_RESISTANT("fire", MetaType.MENDELIAN, "asd"), //lava and fire walking
  GLOW("glow", MetaType.MENDELIAN, "asd"), //enum
  REGEN("regeneration", MetaType.MENDELIAN, "asd"),
  ICE_WALKER("ice-walker", MetaType.MENDELIAN,
      "Riding your horse on water generates frosted ice under you."),
  SLOWFALL("slowfall", MetaType.MENDELIAN, "asd"),
  INVISIBLE("unseen", MetaType.MENDELIAN, "asd"),
  ATTRACTIVE("attractive", MetaType.MENDELIAN, "asd"),
  EGG_LAYER("egg", MetaType.MENDELIAN, "asd"),
  DESERTWALKING("desert-haste", MetaType.MENDELIAN, "asd"),
  SILENT("silence", MetaType.MENDELIAN, "asd"),
  ARMOR("base-armor", MetaType.MENDELIAN, "asd");

  private final String key;
  private final MetaType metaType;
  private final String description;
  GeneticAttribute(String key, MetaType metaType, String description) {
    this.key = key;
    this.metaType = metaType;
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
  public MetaType getMetaType() {
    return metaType;
  }
}
