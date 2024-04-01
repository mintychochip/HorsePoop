package mintychochip.mintychochip.horsepoop.api.events;

import mintychochip.genesis.events.AbstractEvent;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class FawnyEvent extends AbstractEvent {

  @NotNull
  protected final AnimalGenome animalGenome;
  @NotNull
  protected final EntityType entityType;
  protected FawnyEvent(@NotNull AnimalGenome animalGenome, @NotNull EntityType entityType) {
    this.animalGenome = animalGenome;
    this.entityType = entityType;
  }
  public @NotNull EntityType getEntityType() {
    return entityType;
  }

  public @NotNull AnimalGenome getAnimalGenome() {
    return animalGenome;
  }
}
