package mintychochip.mintychochip.horsepoop.api.events;

import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

public class EventCreator {// find a better name
  private final ConfigManager configManager;

  public EventCreator(ConfigManager configManager) {
    this.configManager = configManager;
  }

  public FawnyMilkEvent instanceMilkEvent(AnimalGenome animalGenome, EntityType entityType,
      Player player, ItemStack bucket) {
    return FawnyMilkEvent.createInstance(animalGenome, entityType, player, this.configManager,
        bucket,
        this);
  }

  public FawnyRidingMoveEvent instanceRidingMoveEvent(AnimalGenome animalGenome,
      EntityType entityType, Player player) {
    return FawnyRidingMoveEvent.createInstance(animalGenome, entityType, player, this.configManager,
        this);
  }
}