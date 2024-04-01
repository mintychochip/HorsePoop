package mintychochip.mintychochip.horsepoop.container.enums.attributes.specific;

import mintychochip.mintychochip.horsepoop.container.GeneTrait;
import mintychochip.mintychochip.horsepoop.container.ValueType;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.TraitType;

public enum GeneticAttribute implements GeneTrait {
  CONSTITUTION("constitution", ValueType.NUMERIC, "asd"),
  SPEED("speed", ValueType.NUMERIC, "asd"),
  PARTICLE("particle", ValueType.ENUM, "asd"),
  STRENGTH("strength", ValueType.NUMERIC, "asd"),
  JUMP_STRENGTH("jumping-strength", ValueType.NUMERIC, "asd"),
  FIRE_RESISTANT("fire", ValueType.MENDELIAN, "asd"), //lava and fire walking
  GLOW("glow", ValueType.MENDELIAN, "asd"), //enum
  REGEN("regeneration", ValueType.MENDELIAN, "asd"),
  ICE_WALKER("ice-walker", ValueType.MENDELIAN,
      "Riding your horse on water generates frosted ice under you."),
  SLOWFALL("slowfall", ValueType.MENDELIAN, "asd"),
  INVISIBLE("unseen", ValueType.MENDELIAN, "asd"),
  ATTRACTIVE("attractive", ValueType.MENDELIAN, "asd"),
  EGG_LAYER("egg", ValueType.MENDELIAN, "asd"),
  DESERTWALKING("desert-haste", ValueType.MENDELIAN, "asd"),
  SILENT("silence", ValueType.MENDELIAN, "asd"),
  ARMOR("base-armor", ValueType.MENDELIAN, "asd");

  private final String key;
  private final ValueType valueType;
  private final String description;
  GeneticAttribute(String key, ValueType valueType, String description) {
    this.key = key;
    this.valueType = valueType;
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
  public ValueType getValueType() {
    return valueType;
  }
}
