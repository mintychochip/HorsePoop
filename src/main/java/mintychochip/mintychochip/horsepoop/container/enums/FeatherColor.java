package mintychochip.mintychochip.horsepoop.container.enums;

public enum FeatherColor {
  ORANGE("orange-feather"),
  EMERALD("emerald-feather"),
  CYAN("violet-feather"),
  FADED("faded-feather"),
  BLUE("blue-feather"),
  RED("red-feather"),
  AUTUMN("autumn-feather"),
  SILVER("silver-feather"),
  RUST("rusted-feather");

  private String featherKey; //returns the key for the config for the feather

  FeatherColor(String featherKey) {
    this.featherKey = featherKey;
  }

  public String getFeatherKey() {
    return featherKey;
  }
}
