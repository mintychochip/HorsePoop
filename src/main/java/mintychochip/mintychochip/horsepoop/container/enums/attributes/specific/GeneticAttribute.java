package mintychochip.mintychochip.horsepoop.container.enums.attributes.specific;

import mintychochip.mintychochip.horsepoop.api.Gene;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.TraitType;
import mintychochip.mintychochip.horsepoop.metas.MetaType;

public enum GeneticAttribute implements Gene {
  CONSTITUTION("constitution", MetaType.DOUBLE, "asd"),
  SPEED("speed", MetaType.DOUBLE, "asd"),
  PARTICLE("particle", MetaType.ENUM, "asd"),
  STRENGTH("strength", MetaType.CROSSABLE_DOUBLE, "asd"),
  JUMP_STRENGTH("jumping", MetaType.DOUBLE, "asd"),
  FIRE_RESISTANT("fire",  MetaType.MENDELIAN, "asd"), //lava and fire walking
  LAVA_RESISTANT("lava",MetaType.POLYGENIC_MENDELIAN,"asd"),
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
  public String getDescription() {
    return description;
  }

  @Override
  public MetaType getMetaType() {
    return metaType;
  }
}
