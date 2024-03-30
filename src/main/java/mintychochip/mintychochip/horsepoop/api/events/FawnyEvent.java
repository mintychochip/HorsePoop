package mintychochip.mintychochip.horsepoop.api.events;

import mintychochip.genesis.events.AbstractEvent;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

abstract class FawnyEvent extends AbstractEvent {

  @NotNull
  protected final AnimalGenome animalGenome;
  @NotNull
  protected final EntityType entityType;
  @NotNull
  protected final ConfigManager configManager;

  protected FawnyEvent(@NotNull AnimalGenome animalGenome, @NotNull EntityType entityType,
      @NotNull ConfigManager configManager) {
    this.animalGenome = animalGenome;
    this.entityType = entityType;
    this.configManager = configManager;
  }

  public @NotNull ConfigManager getConfigManager() {
    return configManager;
  }
  public @NotNull EntityType getEntityType() {
    return entityType;
  }

  public @NotNull AnimalGenome getAnimalGenome() {
    return animalGenome;
  }
}
