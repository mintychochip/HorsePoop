package mintychochip.mintychochip.horsepoop.listener.book;

import java.util.List;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.listener.display.Componentifier;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

public record TraitPage<U extends TraitEnum>(String header, int pageNumber,
                                             List<BaseTrait<U>> traits) implements Page {
  @Override
  public Component getContent() {
    traits.stream().forEach(x -> Bukkit.broadcastMessage(x.getMeta().getTrait().toString()));
    return Component.empty().append(Component.text(header)).appendNewline()
        .append(new Componentifier<>(traits).getComponent());
  }
}
