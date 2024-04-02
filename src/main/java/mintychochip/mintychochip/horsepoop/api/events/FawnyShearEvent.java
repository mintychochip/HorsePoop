package mintychochip.mintychochip.horsepoop.api.events;

import mintychochip.mintychochip.horsepoop.api.EventCreator;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FawnyShearEvent extends FawnyEvent implements Cancellable {

  private boolean cancelled = false;
  @Nullable
  private final Player player;
  private final ItemStack shears;
  private final LivingEntity livingEntity;
  private FawnyShearEvent(@NotNull AnimalGenome animalGenome, @NotNull LivingEntity livingEntity, Player player,
      @Nullable ItemStack shears) {
    super(animalGenome, livingEntity.getType());
    this.livingEntity = livingEntity;
    this.player = player;
    this.shears = shears;
  }
  public LivingEntity getLivingEntity() {
    return livingEntity;
  }

  public ItemStack getShears() {
    return shears;
  }

  public Player getPlayer() {
    return player;
  }

  public static FawnyShearEvent createInstance(AnimalGenome animalGenome, LivingEntity livingEntity, Player player, ItemStack instrument, EventCreator eventCreator) {
    if(instrument.getType() != Material.SHEARS) {
      return null;
    }
    return new FawnyShearEvent(animalGenome,livingEntity,player,instrument);
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
