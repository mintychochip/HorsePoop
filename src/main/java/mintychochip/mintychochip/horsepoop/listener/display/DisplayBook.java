package mintychochip.mintychochip.horsepoop.listener.display;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mintychochip.genesis.util.StringUtil;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.util.ImagePixelColorDecoder;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.EntityType;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public class DisplayBook {

  @NonNull
  private final EntityType entityType;
  private final Map<String, Integer> headerIntegerMap = new HashMap<>();
  private int lastPageNum = 2;
  public DisplayBook(@NotNull EntityType entityType, @NotNull AnimalGenome animalGenome) {
    this.entityType = entityType;
  }

  private <U extends TraitEnum> Component getTraitComponent(List<BaseTrait<U>> traits) {
    return new Componentifier<U>(traits).getComponent();
  }

  public <U extends TraitEnum> Component createBookPage(String header, List<BaseTrait<U>> traits) {
    headerIntegerMap.put(header, lastPageNum++);
    return Component.empty().append(
            Component.text(header).hoverEvent(HoverEvent.showText(Component.text("Navigate to: [Main]")))
                .clickEvent(ClickEvent.changePage(1)))
        .append(Component.newline()).append(this.getTraitComponent(traits));
  }

  public Component pageButtonComponent(String header) {
    int i = headerIntegerMap.get(header);
    return Component.empty()
        .append(Component.text(header).clickEvent(ClickEvent.changePage(i)).hoverEvent(
            HoverEvent.showText(Component.text("Navigate to: " + header))));
  }

  public Component getEntityTypeComponentImage(int padding) {
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
}
