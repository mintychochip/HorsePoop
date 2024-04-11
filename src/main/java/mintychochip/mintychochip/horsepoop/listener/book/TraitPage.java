package mintychochip.mintychochip.horsepoop.listener.book;

import java.util.List;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.listener.display.Componentifier;
import net.kyori.adventure.text.Component;

public record TraitPage<U extends TraitEnum>(String header, int pageNumber,
                                             List<BaseTrait<U>> traits) implements Page {
  @Override
  public Component getContent() {
    return Component.empty().append(Component.text(header)).appendNewline()
        .append(new Componentifier<>(traits).getComponent());
  }
}
