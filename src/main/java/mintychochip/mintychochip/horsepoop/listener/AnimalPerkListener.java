package mintychochip.mintychochip.horsepoop.listener;

import com.google.gson.Gson;

import mintychochip.mintychochip.horsepoop.api.EventCreator;
import mintychochip.mintychochip.horsepoop.api.events.FawnyEvent;
import mintychochip.mintychochip.horsepoop.api.events.FawnyInteractEntityEvent;
import mintychochip.mintychochip.horsepoop.api.events.FawnyRidingMoveEvent;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.TraitFetcher;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.GenericGeneTrait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.CowGeneTrait;
import mintychochip.mintychochip.horsepoop.util.DataExtractor;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class AnimalPerkListener implements Listener {

  private final ConfigManager configManager;

  private final EventCreator eventCreator;

  private final TraitFetcher traitFetcher;

  public AnimalPerkListener(ConfigManager configManager, TraitFetcher traitFetcher) {
    this.configManager = configManager;
    this.eventCreator = new EventCreator();
    this.traitFetcher = traitFetcher;
  }

  @EventHandler
  private void onPlayerRidingMoveEvent(final PlayerMoveEvent event) {
    if (event.isCancelled()) {
      return;
    }
    Player player = event.getPlayer();
    if (!player.isInsideVehicle()) {
      return;
    }
    Entity vehicle = player.getVehicle();
    if (!(vehicle instanceof LivingEntity livingEntity)) {
      return;
    }
    PersistentDataContainer pdc = livingEntity.getPersistentDataContainer();
    if (!pdc.has(HorsePoop.GENOME_KEY, PersistentDataType.STRING)) {
      return;
    }
    AnimalGenome animalGenome = DataExtractor.extractGenomicData(livingEntity);
    FawnyRidingMoveEvent fawnyRidingMoveEvent = eventCreator.instanceRidingMoveEvent(animalGenome,
        livingEntity.getType(), player);
    if (fawnyRidingMoveEvent != null) {
      Bukkit.getPluginManager().callEvent(fawnyRidingMoveEvent);
    }
  }


  @EventHandler
  private void onFawnyAnimalDeath(final EntityDeathEvent event) {
    LivingEntity entity = event.getEntity();
    if (!configManager.getEntityConfig().getEnabledEntityTypes()
        .contains(entity.getType().toString())) {
      return;
    }
    PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
    if (!persistentDataContainer.has(HorsePoop.GENOME_KEY)) {
      return;
    }
    String s = persistentDataContainer.get(HorsePoop.GENOME_KEY, PersistentDataType.STRING);
    AnimalGenome animalGenome = new Gson().fromJson(s, AnimalGenome.class);
    Gene yield = traitFetcher.getGeneFromGeneList(animalGenome,GenericGeneTrait.YIELD);
    if (yield == null) {
      return;
    }
    switch (entity.getType()) {
      case SHEEP -> {

      }
    }
  }

//  @EventHandler(priority = EventPriority.MONITOR)
//  private void onFawnyAnimalSheared(final PlayerShearEntityEvent event) { //need blockShear too
//    Bukkit.broadcastMessage("here");
//
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

  @EventHandler(priority = EventPriority.MONITOR)
  private void onPlayerInteractEntityEvent(final PlayerInteractEntityEvent event) {
    if (event.isCancelled()) {
      return;
    }
    Entity rightClicked = event.getRightClicked();

    if (!(rightClicked instanceof LivingEntity livingEntity)) {
      return;
    }
    AnimalGenome animalGenome = DataExtractor.extractGenomicData(livingEntity);
    if (animalGenome == null) {
      return;
    }
    Player player = event.getPlayer();
    PlayerInventory inventory = player.getInventory();
    FawnyInteractEntityEvent fawnyInteractEntityEvent = eventCreator.instanceInteractionEvent(
        animalGenome, livingEntity, player);
    ItemStack instrument = event.getHand() == EquipmentSlot.HAND ? inventory.getItemInMainHand() : inventory.getItemInOffHand();
    fawnyInteractEntityEvent.setInstrument(instrument);
    if(instrument.getType() == Material.BUCKET || instrument.getType() == Material.SHEARS) {
      event.setCancelled(true);
    }
    if(rightClicked instanceof AbstractHorse abstractHorse && player.isSneaking()) { //ghetto fixc
      event.setCancelled(true);
    }
    this.callEvent(fawnyInteractEntityEvent);
  }
  private <T extends FawnyEvent> void callEvent(T fawnyEvent) {
    if (fawnyEvent != null) {
      Bukkit.getPluginManager().callEvent(fawnyEvent);
    }
  }
  @EventHandler
  private void onFawnyInteractEvent(final FawnyInteractEntityEvent event) {
    AnimalGenome animalGenome = event.getAnimalGenome();
    Player player = event.getPlayer();
    if (player.isSneaking()) {
      return;
    }
    LivingEntity livingEntity = event.getLivingEntity();
    if (livingEntity instanceof Ageable ageable) {
      if (!ageable.isAdult()) {
        return;
      }
    }
    Gene milkTrait = traitFetcher.getGeneFromGeneList(animalGenome,CowGeneTrait.STRAWBERRY_MILK);
    if (milkTrait == null) {
      return;
    }
//    if (animalGenome.getGender() == Gender.MALE && !configManager.getSettingsConfig()
//        .getMaleMilked()) {
//      player.sendMessage(ChatColor.RED + "You can't milk a male!");
//      event.setCancelled(true);
//      return;
//    }
    ItemStack instrument = event.getInstrument();
    if (instrument == null) {
      return;
    }
    if (instrument.getType() != Material.BUCKET) {
      return;
    }
    int i = instrument.getAmount(); //count of buckets
    if (i < 1) {
      return;
    }
    this.callEvent(eventCreator.instanceMilkEvent(animalGenome,
        event.getEntityType(), player, instrument));
  }

  @EventHandler
  private void onShearBird(final FawnyInteractEntityEvent event) {
    AnimalGenome animalGenome = event.getAnimalGenome();
    Player player = event.getPlayer();
    if(player.isSneaking()) {
      return;
    }
    LivingEntity livingEntity = event.getLivingEntity();
    if(livingEntity instanceof Tameable tameable) {
      if(tameable.getOwner() != player) {
        return;
      }
    }
    ItemStack instrument = event.getInstrument();
    if (instrument == null) {
      return;
    }
    if (instrument.getType() != Material.SHEARS) {
      return;
    }
    ItemMeta itemMeta = instrument.getItemMeta();
    if(itemMeta instanceof Damageable damageable && event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
      damageable.setDamage(damageable.getDamage() + 1);
      instrument.setItemMeta(damageable);
    }
    int damage = 1;
    if(livingEntity.getType() == EntityType.PARROT) {
      damage = 2;
    }
    livingEntity.damage(damage,event.getPlayer());
    this.callEvent(eventCreator.instanceShearEvent(animalGenome, event.getLivingEntity(), player,
        instrument));
  }


}
