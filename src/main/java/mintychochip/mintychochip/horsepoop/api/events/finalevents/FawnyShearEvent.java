package mintychochip.mintychochip.horsepoop.api.events.finalevents;

import mintychochip.mintychochip.horsepoop.api.events.AbstractInstrumentEvent;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FawnyShearEvent extends AbstractInstrumentEvent implements Cancellable {

  private boolean cancelled = false;
  public FawnyShearEvent(@NotNull AnimalGenome animalGenome, @NotNull LivingEntity livingEntity,
      Player player, ItemStack itemStack) {
    super(animalGenome, livingEntity, player, itemStack);
  }

  @Override
  public boolean isCancelled() {
    return cancelled;
  }

  @Override
  public void setCancelled(boolean b) {
    this.cancelled = b;
  }
}
