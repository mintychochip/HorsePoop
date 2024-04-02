package mintychochip.mintychochip.horsepoop.listener;

import mintychochip.mintychochip.horsepoop.api.events.FawnyEvent;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.TraitFetcher;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

interface FauwnyListener extends Listener {
  <T extends FawnyEvent> void callEvent(T event);
}
