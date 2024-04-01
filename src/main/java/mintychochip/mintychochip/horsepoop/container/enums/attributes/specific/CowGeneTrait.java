package mintychochip.mintychochip.horsepoop.container.enums.attributes.specific;

import mintychochip.mintychochip.horsepoop.container.GeneTrait;
import mintychochip.mintychochip.horsepoop.container.ValueType;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.TraitType;

public enum CowGeneTrait implements GeneTrait {

  MOOSHROOM_GENE("mooshroom", ValueType.MENDELIAN, "asd"),
  STRAWBERRY_MILK("strawberry-milk", ValueType.MENDELIAN,
      "asd"); //gives a different item if it has the gene
  private final String key;

  private final ValueType valueType;
  private final String description;

  CowGeneTrait(String key, ValueType valueType, String description) {
    this.key = key;
    this.valueType = valueType;
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
  public ValueType getValueType() {
    return valueType;
  }
}
