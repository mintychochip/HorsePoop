package mintychochip.mintychochip.horsepoop.listener.display;

import java.util.List;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import net.kyori.adventure.text.Component;

public interface HoverDisplay<U extends TraitEnum> {
  /**
   *
   * @param padding Moves the beginning of the component text to the right by n padding
   * @return A component for hover text
   */
  Component getBody(BaseTrait<U> trait, int padding);
  Component getHeader(BaseTrait<U> trait);
}
