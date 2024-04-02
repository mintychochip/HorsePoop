package mintychochip.mintychochip.horsepoop.listener.display;

import java.util.List;
import java.util.stream.Collectors;
import mintychochip.mintychochip.horsepoop.config.TraitMeta;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.Fetcher;
import mintychochip.mintychochip.horsepoop.container.ValueFetcher;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEvent.Action;

public class Componentify <T extends TraitMeta> { //tabulates traits to components

  Fetcher<T> fetcher = new ValueFetcher<>();
  List<TextComponent> componentify(List<BaseTrait<T>> traits) {
    return traits.stream()
        .map(x -> Component.text(x.getTrait()).hoverEvent(HoverEvent.hoverEvent(
                Action.SHOW_TEXT, Component.text(fetcher.getTrait(x.getTrait()).getShortDescription())))
            .append(Component.text(": " + x.getValue()))).toList();
  }
}
