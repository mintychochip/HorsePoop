package mintychochip.mintychochip.horsepoop.api.events;

import mintychochip.mintychochip.horsepoop.api.EventCreator;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

public class FawnyRidingMoveEvent extends FawnyEvent implements Cancellable {

  private boolean cancelled = false;

  private final Player player;
  public FawnyRidingMoveEvent(@NotNull AnimalGenome animalGenome,
      @NotNull LivingEntity livingEntity, @NotNull Player player) {
    super(animalGenome, livingEntity);
    this.player = player;
  }

  public Player getPlayer() {
    return player;
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
