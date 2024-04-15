package mintychochip.mintychochip.horsepoop.api.events;

import mintychochip.genesis.events.AbstractEvent;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractInstrumentEvent extends FawnyEvent implements InstrumentEvent {

  protected final Player player;
  protected final ItemStack itemStack;

  protected AbstractInstrumentEvent(
      @NotNull AnimalGenome animalGenome,
      @NotNull LivingEntity livingEntity, Player player, ItemStack itemStack) {
    super(animalGenome, livingEntity);
    this.player = player;
    this.itemStack = itemStack;
  }

  @Override
  public Player getPlayer() {
    return player;
  }

  @Override
  public ItemStack getInstrument() {
    return itemStack;
  }
}
