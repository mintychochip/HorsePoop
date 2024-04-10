package mintychochip.mintychochip.horsepoop.listener.display;

import net.kyori.adventure.text.Component;

public interface HoverDisplay {
  /**
   *
   * @param padding Moves the beginning of the component text to the right by n padding
   * @return A component for hover text
   */
  Component getBody(int padding);
  Component getHeader();
}
