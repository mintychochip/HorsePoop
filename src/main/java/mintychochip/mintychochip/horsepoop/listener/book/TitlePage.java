package mintychochip.mintychochip.horsepoop.listener.book;

import java.util.ArrayList;
import java.util.List;
import mintychochip.mintychochip.horsepoop.listener.display.RGB;
import mintychochip.mintychochip.horsepoop.util.ImagePixelColorDecoder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.EntityType;

public class TitlePage implements Page {

  private final EntityType entityType;

  public TitlePage(EntityType entityType) {
    this.entityType = entityType;
  }
  private Component getEntityTypeComponentImage(int padding) {
    RGB[][] decode = ImagePixelColorDecoder.decode(
        "images\\" + entityType.toString().toLowerCase() + ".png");
    Component component = Component.text("");
    for (int x = 0; x < decode.length; x++) {
      List<RGB> rgbs = new ArrayList<>();
      for (int y = 0; y < decode.length; y++) {
        rgbs.add(decode[y][x]);
      }
      component = component.append(Component.newline()).append(buildLine(rgbs, padding));
    }
    return component;
  }
  private Component buildLine(List<RGB> rgbLine, int padding) {
    Component component = Component.text("");
    for (int i = 0; i < padding; i++) {
      component = component.append(Component.text(" "));
    }
    for (RGB rgb : rgbLine) {
      component = component.append(Component.text('█').color(TextColor.color(rgb)));
    }

    return component;
  }
  @Override
  public Component getContent() {
    return this.getEntityTypeComponentImage(3);
  }
}
