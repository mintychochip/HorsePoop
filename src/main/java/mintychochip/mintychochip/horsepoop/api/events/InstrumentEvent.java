package mintychochip.mintychochip.horsepoop.api.events;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface InstrumentEvent {
  Player getPlayer();
  ItemStack getInstrument();
}
