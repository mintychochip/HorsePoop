package mintychochip.mintychochip.horsepoop.container.enums;

public enum TraitType {
  PHENOTYPIC("phenotypic"),
  GENE("gene"),
  INTRINSIC("intrinsic");

  private final String key;

  TraitType(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
