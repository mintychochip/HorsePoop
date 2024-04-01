package mintychochip.mintychochip.horsepoop.container.enums.attributes;

public enum TraitType {

  SHEEP("sheep"),
  HORSE("horse"),
  GENERIC("generic"),
  COW("cow"),
  CHARACTERISTIC("char"),
  GENE("gene");

  private final String key;

  TraitType(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}