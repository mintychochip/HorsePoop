package mintychochip.mintychochip.horsepoop.util.string;

public class StringManipulation {
  public static String capitalizeFirstLetter(String input) {
    if (input == null) {
      return null;
    }
    input = input.replaceAll("-", " ").replaceAll("_", " ");
    StringBuilder sb = new StringBuilder();
    for (String s : input.split(" ")) {
      sb.append(s.substring(0, 1).toUpperCase()).append(s.substring(1)).append(" ");
    }
    return sb.toString().trim();
  }
}
