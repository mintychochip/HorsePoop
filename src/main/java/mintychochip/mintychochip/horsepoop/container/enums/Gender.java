package mintychochip.mintychochip.horsepoop.container.enums;

import mintychochip.genesis.util.Characteristic;
import mintychochip.mintychochip.horsepoop.listener.display.RGB;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public enum Gender implements Characteristic {
  MALE('♂'),
  FEMALE('♀');

  private final char unicode;

  Gender(char unicode) {
    this.unicode = unicode;
  }

  public Component getUnicode() {
    return this.getPlainUnicode().color(this.getTextColor());
  }
  public Component getPlainUnicode() {
    return Component.text(unicode);
  }
  public TextColor getTextColor() {
    return this == FEMALE ? TextColor.color(new RGB(205, 125, 205))
        : TextColor.color(NamedTextColor.BLUE);
  }
  @Override
  public String toString() {
    return super.toString();
  }
}