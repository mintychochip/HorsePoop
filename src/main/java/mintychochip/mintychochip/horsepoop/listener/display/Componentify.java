package mintychochip.mintychochip.horsepoop.listener.display;

import mintychochip.mintychochip.horsepoop.api.Fetcher;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.ValueFetcher;

import java.awt.*;

public class Componentify <U extends TraitEnum> { //tabulates traits to components

  Fetcher<U> fetcher = new ValueFetcher<>();
  private List<TextComponent> componentify(List<BaseTrait<U>> traits) {
    return traits.stream()
        .map(x -> Component.text(x.getTrait()).hoverEvent(HoverEvent.hoverEvent(
                Action.SHOW_TEXT, Component.text(fetcher.getTrait(x.getTrait()).getShortDescription())))
            .append(Component.text(": " + x.getValue()))).toList();
  }
}

