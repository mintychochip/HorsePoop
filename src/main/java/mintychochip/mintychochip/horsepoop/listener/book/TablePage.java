package mintychochip.mintychochip.horsepoop.listener.book;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;

public class TablePage implements Page {

  private final Map<Integer, String> traitButtons = new HashMap<>();

  @Override
  public Component getContent() {
    Component content = Component.empty().append(Component.text("Table of Contents:"))
        .appendNewline().append(Component.text("-------------------")).appendNewline();
    String previousText = null;
    int count = 1;
    for (int key : traitButtons.keySet()) {
      final String text = traitButtons.get(key);

      StringBuilder newText = new StringBuilder(text);
      if (previousText != null) {
        if (text.equals(previousText)) {
          newText.append(" ").append("pt.").append(++count);
        } else {
          count = 1;
        }
      }
      previousText = text;
      content = content.append(Component.text(newText.toString())
          .hoverEvent(HoverEvent.showText(Component.text("Navigate to: " + text)))
          .clickEvent(ClickEvent.changePage(key))).appendNewline();
    }
    return content;
  }

  public void addTraitButton(int page, String header) {
    traitButtons.put(page, header);
  }
}
