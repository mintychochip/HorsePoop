package mintychochip.mintychochip.horsepoop.api.events;

import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FawnyMilkEvent extends FawnyEvent implements Cancellable {

  private final ItemStack bucket;

  private boolean cancelled = false;

  private final Player player;

  private FawnyMilkEvent(@NotNull AnimalGenome animalGenome,
      EntityType entityType, ConfigManager configManager, Player player, @NotNull ItemStack bucket) {
    super(animalGenome, entityType, configManager);
    this.player = player;
    this.bucket = bucket;
  }

  public Player getPlayer() {
    return player;
  }

  public ItemStack getBucket() {
    return bucket;
  }

  public static FawnyMilkEvent createInstance(@NotNull AnimalGenome animalGenome, EntityType entityType,
      Player player, ConfigManager configManager, @NotNull ItemStack bucket, EventCreator eventCreator) {
    if (bucket.getType() != Material.BUCKET) {
      return null;
    }
    return new FawnyMilkEvent(animalGenome, entityType, configManager, player, bucket);
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
