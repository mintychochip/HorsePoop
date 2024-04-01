package mintychochip.mintychochip.horsepoop.api;

import mintychochip.mintychochip.horsepoop.api.events.FawnyInteractEntityEvent;
import mintychochip.mintychochip.horsepoop.api.events.FawnyMilkEvent;
import mintychochip.mintychochip.horsepoop.api.events.FawnyRidingMoveEvent;
import mintychochip.mintychochip.horsepoop.api.events.FawnyShearEvent;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

public class EventCreator {// find a better name
  public FawnyMilkEvent instanceMilkEvent(AnimalGenome animalGenome, EntityType entityType,
      Player player, ItemStack bucket) {
    return FawnyMilkEvent.createInstance(animalGenome, entityType, player, 
        bucket,
        this);
  }

  public FawnyInteractEntityEvent instanceInteractionEvent(AnimalGenome animalGenome,
      LivingEntity livingEntity, Player player) {
    return FawnyInteractEntityEvent.createInstance(animalGenome, livingEntity,  player,
        this);
  }

  public FawnyShearEvent instanceShearEvent(AnimalGenome animalGenome, LivingEntity livingEntity,
      Player player, ItemStack shears) {
    return FawnyShearEvent.createInstance(animalGenome, livingEntity,  player, shears, this);
  }

  public FawnyRidingMoveEvent instanceRidingMoveEvent(AnimalGenome animalGenome,
      EntityType entityType, Player player) {
    return FawnyRidingMoveEvent.createInstance(animalGenome, entityType, player,
        this);
  }
}