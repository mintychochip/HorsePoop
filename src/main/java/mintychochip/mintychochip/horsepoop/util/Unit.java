package mintychochip.mintychochip.horsepoop.util;

public enum Unit {
  NEWTONS("N"),
  METER ("m"),
  SECOND ("s"),
  FEET ("ft"),
  METER_PER_SECOND("m/s");
  private final String abbreviation;
  Unit(String abbreviation) {
    this.abbreviation = abbreviation;
  }
  public String getAbbreviation() {
    return abbreviation;
  }

}
