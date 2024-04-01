package mintychochip.mintychochip.horsepoop.factories;

import org.bukkit.DyeColor;

public enum SheepColor { //temporary
  PINK("red0", DyeColor.PINK),
  RED("red1", DyeColor.RED),
  GREEN("green1", DyeColor.GREEN),
  BLUE("blue1", DyeColor.BLUE),
  LIGHT_BLUE("blue0", DyeColor.LIGHT_BLUE),
  LIME("green0", DyeColor.LIME),
  ORANGE("red1green0", DyeColor.ORANGE),
  ORANGE2("red0green1", DyeColor.ORANGE),
  ORANGE3("red1green1", DyeColor.ORANGE),
  CYAN("blue1green1", DyeColor.CYAN),
  CYAN2("blue1green0", DyeColor.CYAN),
  PURPLE("red0blue0", DyeColor.PURPLE),
  PURPLE2("red0blue1", DyeColor.PURPLE),
  MAGENTA("red1blue1", DyeColor.MAGENTA),
  MAGENTA2("red1blue0", DyeColor.MAGENTA);

  private final String key;

  private final DyeColor dyeColor;

  SheepColor(String key, DyeColor dyeColor) {
    this.key = key;
    this.dyeColor = dyeColor;
  }

  public String getKey() {
    return key;
  }

  public DyeColor getDyeColor() {
    return dyeColor;
  }
}