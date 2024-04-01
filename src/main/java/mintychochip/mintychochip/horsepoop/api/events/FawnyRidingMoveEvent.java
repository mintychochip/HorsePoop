package mintychochip.mintychochip.horsepoop.api.events;

import mintychochip.mintychochip.horsepoop.api.EventCreator;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

public class FawnyRidingMoveEvent extends FawnyEvent implements Cancellable {

  private boolean cancelled = false;

  private final Player player;
  private FawnyRidingMoveEvent(@NotNull AnimalGenome animalGenome,
      @NotNull EntityType entityType, @NotNull Player player) {
    super(animalGenome, entityType);
    this.player = player;
  }

  public Player getPlayer() {
    return player;
  }

  public static FawnyRidingMoveEvent createInstance(AnimalGenome animalGenome,
      EntityType entityType, Player player,
      EventCreator eventCreator) {
    return new FawnyRidingMoveEvent(animalGenome, entityType, player);
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
