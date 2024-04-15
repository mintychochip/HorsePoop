package mintychochip.mintychochip.horsepoop.api.events.finalevents;

import mintychochip.mintychochip.horsepoop.api.events.AbstractInstrumentEvent;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.bukkit.entity.Player;
public final class FrogDartEvent extends AbstractInstrumentEvent {
  public FrogDartEvent(@NotNull AnimalGenome animalGenome, @NotNull LivingEntity livingEntity,
      Player player, ItemStack itemStack) {
    super(animalGenome, livingEntity, player, itemStack);
  }
}
