package mintychochip.mintychochip.horsepoop.util.math;

public class Round {

  /**
   * @param decimal The number being rounded,
   * @param places  The number of places to round to,
   * @return returns a double that is rounded to places.
   */
  public static double roundDecimal(double decimal, int places) {
    int v = (int) (decimal * Math.pow(10, places));
    return ((double) v) / Math.pow(10, places);
  }
}
