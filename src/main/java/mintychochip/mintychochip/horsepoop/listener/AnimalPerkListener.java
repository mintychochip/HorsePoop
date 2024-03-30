package mintychochip.mintychochip.horsepoop.listener;

import com.google.gson.Gson;
import mintychochip.genesis.Genesis;

import mintychochip.mintychochip.horsepoop.api.events.EventCreator;
import mintychochip.mintychochip.horsepoop.api.events.FawnyMilkEvent;
import mintychochip.mintychochip.horsepoop.api.events.FawnyRidingMoveEvent;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome.Gender;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.GenericTrait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.SheepTrait;
import mintychochip.mintychochip.horsepoop.util.DataExtractor;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockShearEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class AnimalPerkListener implements Listener {

  private final ConfigManager configManager;

  private final EventCreator eventCreator;

  public AnimalPerkListener(ConfigManager configManager) {
    this.configManager = configManager;
    this.eventCreator = new EventCreator(configManager);
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
    Gene yield = animalGenome.getGeneFromTrait(GenericTrait.YIELD);
    if (yield == null) {
      return;
    }
    switch (entity.getType()) {
      case SHEEP -> {

      }
    }
  }

  @EventHandler(priority = EventPriority.MONITOR)
  private void onFawnyAnimalSheared(final PlayerShearEntityEvent event) { //need blockShear too
    if (event.isCancelled()) {
      return;
    }
    Entity entity = event.getEntity();
    if (!(entity instanceof LivingEntity livingEntity)) {
      return;
    }
    Location entityLocation = entity.getLocation();
    AnimalGenome animalGenome = DataExtractor.extractGenomicData(livingEntity);
    if (animalGenome == null) {
      return;
    }
    Gene yield = animalGenome.getGeneFromTrait(GenericTrait.YIELD);
    if (yield == null) {
      return;
    }
    int rand = calculateRandomYield(new Gson().fromJson(yield.getValue(), int.class),
        event.getItem());
    switch (livingEntity.getType()) {
      case SHEEP -> {
        event.setCancelled(true);
        Sheep sheep = (Sheep) livingEntity;
        sheep.setSheared(true);
        DyeColor dyeColor = SheepTrait.calculateDyeColor(animalGenome, livingEntity);
        if (dyeColor == null) {
          return;
        }
        World world = entityLocation.getWorld();
        if (world == null) {
          return;
        }
        ItemStack wool = new ItemStack(Material.valueOf(dyeColor + "_WOOL"));
        wool.setAmount(rand);
        world.playSound(entityLocation, Sound.ENTITY_SHEEP_SHEAR, 1, 1);
        world.dropItem(entityLocation, wool);
      }
    }
  }

  @EventHandler
  private void onFawnyAnimalBlockShearEvent(final BlockShearEntityEvent event) {
    if (event.isCancelled()) {
      return;
    }
    Entity entity = event.getEntity();
    if (!(entity instanceof LivingEntity livingEntity)) {
      return;
    }
    Location entityLocation = entity.getLocation();
    AnimalGenome animalGenome = DataExtractor.extractGenomicData(livingEntity);
    if (animalGenome == null) {
      return;
    }
    Gene yield = animalGenome.getGeneFromTrait(GenericTrait.YIELD);
    if (yield == null) {
      return;
    }
    int rand = calculateRandomYield(new Gson().fromJson(yield.getValue(), int.class),
        event.getTool());
    switch (livingEntity.getType()) {
      case SHEEP -> {
        event.setCancelled(true);
        Sheep sheep = (Sheep) livingEntity;
        sheep.setSheared(true);
        DyeColor dyeColor = SheepTrait.calculateDyeColor(animalGenome, livingEntity);
        if (dyeColor == null) {
          return;
        }
        World world = entityLocation.getWorld();
        if (world == null) {
          return;
        }
        ItemStack wool = new ItemStack(Material.valueOf(dyeColor + "_WOOL"));
        wool.setAmount(rand);
        world.playSound(entityLocation, Sound.ENTITY_SHEEP_SHEAR, 1, 1);
        world.dropItem(entityLocation, wool);
      }
    }
  }

  @EventHandler(priority = EventPriority.MONITOR)
  private void fawnyCowHasMilkGene(final PlayerInteractEntityEvent event) {
    if (event.isCancelled()) {
      return;
    }
    Entity rightClicked = event.getRightClicked();

    if (!(rightClicked instanceof LivingEntity livingEntity)) {
      return;
    }
    if (livingEntity instanceof Ageable ageable) {
      if (!ageable.isAdult()) {
        return;
      }
    }
    AnimalGenome animalGenome = DataExtractor.extractGenomicData(livingEntity);
    if (animalGenome == null) {
      return;
    }
    Player player = event.getPlayer();
    PlayerInventory inventory = player.getInventory();
    if (player.isSneaking()) {
      event.setCancelled(true);
      return;
    }

    if (animalGenome.getGender() == Gender.MALE && !configManager.getSettingsConfig()
        .getMaleMilked()) {
      player.sendMessage(ChatColor.RED + "You can't milk a male!");
      event.setCancelled(true);
      return;
    }
    ItemStack item = event.getHand() == EquipmentSlot.HAND ? inventory.getItemInMainHand()
        : inventory.getItemInOffHand();
    if (item.getType() != Material.BUCKET) {
      return;
    }
    int i = item.getAmount(); //count of buckets
    if (i < 1) {
      return;
    }
    event.setCancelled(true);
    FawnyMilkEvent fawnyMilkEvent = eventCreator.instanceMilkEvent(animalGenome,
        livingEntity.getType(), player, item);
    if (fawnyMilkEvent != null) {
      Bukkit.getPluginManager().callEvent(fawnyMilkEvent);
    }
  }

  private int calculateRandomYield(int max, ItemStack shear) {
    int min = 1;
    Integer fortuneLevel = shear.getEnchantments().get(Enchantment.LOOT_BONUS_BLOCKS);
    if (fortuneLevel != null) {
      min = calculateMaxYield(fortuneLevel, min);
      max = calculateMaxYield(fortuneLevel, max);
      if (min < 1) {
        min = 1;
      }
    }
    if (min >= max) {
      max = min + 1;
    }
    return Genesis.RANDOM.nextInt(min, max);
  }

  private int calculateMaxYield(int fortuneLevel, int i) {
    return (int) (i * (((double) 1 / (fortuneLevel + 2)) + ((double) (fortuneLevel + 1) / 2)));
  }
}
