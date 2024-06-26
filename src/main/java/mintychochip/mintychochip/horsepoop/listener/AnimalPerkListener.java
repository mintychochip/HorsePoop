package mintychochip.mintychochip.horsepoop.listener;

import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;

public class AnimalPerkListener implements Listener {

//  private final ConfigManager configManager;
//
//  private final EventCreator eventCreator;
//
//  private final TraitFetcher traitFetcher;
//
//  public AnimalPerkListener(ConfigManager configManager, TraitFetcher traitFetcher) {
//    this.configManager = configManager;
//    this.eventCreator = new EventCreator();
//    this.traitFetcher = traitFetcher;
//  }
//@EventHandler(priority = EventPriority.MONITOR)
//  private void onFawnyAnimalSheared(final PlayerShearEntityEvent event) { //need blockShear too
//    if (event.isCancelled()) {
//      return;
//    }
//    Entity entity = event.getEntity();
//    if (!(entity instanceof LivingEntity livingEntity)) {
//      return;
//    }
//    Location entityLocation = entity.getLocation();
//    AnimalGenome animalGenome = grasper.grab(livingEntity);
//    if (animalGenome == null) {
//      return;
//    }
//    double yield = geneFetcher.getNumeric(animalGenome.getGenes(), GenericGene.YIELD);
//    if (yield == 0) {
//      return;
//    }
//    int rand = calculateRandomYield(new Gson().fromJson(yield.getValue(), int.class),
//        event.getItem());
//    switch (livingEntity.getType()) {
//      case SHEEP -> {
//        event.setCancelled(true);
//        Sheep sheep = (Sheep) livingEntity;
//        sheep.setSheared(true);
//        DyeColor dyeColor = SheepGeneTrait.calculateDyeColor(animalGenome, livingEntity);
//        if (dyeColor == null) {
//          return;
//        }
//        World world = entityLocation.getWorld();
//        if (world == null) {
//          return;
//        }
//        ItemStack wool = new ItemStack(Material.valueOf(dyeColor + "_WOOL"));
//        wool.setAmount(rand);
//        world.playSound(entityLocation, Sound.ENTITY_SHEEP_SHEAR, 1, 1);
//        world.dropItem(entityLocation, wool);
//      }
//    }
//  }
//  @EventHandler
//  private void onFawnyAnimalDeath(final EntityDeathEvent event) {
//    LivingEntity entity = event.getEntity();
//    if (!configManager.getEntityConfig().getEnabledEntityTypes()
//        .contains(entity.getType().toString())) {
//      return;
//    }
//    PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
//    if (!persistentDataContainer.has(HorsePoop.GENOME_KEY)) {
//      return;
//    }
//    String s = persistentDataContainer.get(HorsePoop.GENOME_KEY, PersistentDataType.STRING);
//    AnimalGenome animalGenome = new Gson().fromJson(s, AnimalGenome.class);
//    Gene yield = traitFetcher.getGeneFromList(animalGenome,GenericGeneTrait.YIELD);
//    if (yield == null) {
//      return;
//    }
//    switch (entity.getType()) {
//      case SHEEP -> {
//
//      }
//    }
//  }


//
//  @EventHandler
//  private void onFawnyAnimalBlockShearEvent(final BlockShearEntityEvent event) {
//    if (event.isCancelled()) {
//      return;
//    }
//    Entity entity = event.getEntity();
//    if (!(entity instanceof LivingEntity livingEntity)) {
//      return;
//    }
//    Location entityLocation = entity.getLocation();
//    AnimalGenome animalGenome = DataExtractor.extractGenomicData(livingEntity);
//    if (animalGenome == null) {
//      return;
//    }
//    Gene yield = animalGenome.getGeneFromTrait(GenericGeneTrait.YIELD);
//    if (yield == null) {
//      return;
//    }
//    int rand = calculateRandomYield(new Gson().fromJson(yield.getValue(), int.class),
//        event.getTool());
//    switch (livingEntity.getType()) {
//      case SHEEP -> {
//        event.setCancelled(true);
//        Sheep sheep = (Sheep) livingEntity;
//        sheep.setSheared(true);
//        DyeColor dyeColor = SheepGeneTrait.calculateDyeColor(animalGenome, livingEntity);
//        if (dyeColor == null) {
//          return;
//        }
//        World world = entityLocation.getWorld();
//        if (world == null) {
//          return;
//        }
//        ItemStack wool = new ItemStack(Material.valueOf(dyeColor + "_WOOL"));
//        wool.setAmount(rand);
//        world.playSound(entityLocation, Sound.ENTITY_SHEEP_SHEAR, 1, 1);
//        world.dropItem(entityLocation, wool);
//      }
//    }
//  }







}
