package mintychochip.mintychochip.horsepoop.listener;

import mintychochip.mintychochip.horsepoop.api.events.FawnyEvent;
import org.bukkit.event.Listener;

interface FauwnyListener extends Listener {
  <T extends FawnyEvent> void callEvent(T event);
}
