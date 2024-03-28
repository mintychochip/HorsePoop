package mintychochip.mintychochip.horsepoop.listener;

import com.google.gson.Gson;
import mintychochip.genesis.Genesis;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;

import mintychochip.genesis.items.container.AbstractItem;
import mintychochip.genesis.items.container.AppraisableItemData;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.config.AnimalItemConfig;
import mintychochip.mintychochip.horsepoop.config.SettingsConfig;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome.Gender;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianType;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.GenericTrait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.CowTrait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.SheepTrait;
import mintychochip.mintychochip.horsepoop.util.DataExtractor;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockShearEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class AnimalPerkListener implements Listener {

  private final ConfigManager configManager;

  public AnimalPerkListener(ConfigManager configManager) {
    this.configManager = configManager;
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
        Bukkit.broadcastMessage(rand + "");
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
    ItemStack itemStack = new ItemStack(Material.MILK_BUCKET);
    if (player.isSneaking()) {
      event.setCancelled(true);
      return;
    }

    ItemStack item;
    if (event.getHand() == EquipmentSlot.HAND) {
      item = inventory.getItemInMainHand();
    } else {
      item = inventory.getItemInOffHand();
    }
    if (item.getType() != Material.BUCKET) {
      return;
    }
    int i = item.getAmount(); //count of buckets
    if (i < 1) {
      event.setCancelled(true);
      return;
    }
    if (player.getGameMode() == GameMode.SURVIVAL) {
      item.setAmount(--i);
    }
    Gene milkTrait = animalGenome.getGeneFromTrait(CowTrait.STRAWBERRY_MILK);
    SettingsConfig settingsConfig = configManager.getSettingsConfig();
    if (animalGenome.getGender() == Gender.MALE && !settingsConfig.getMaleMilked()) {
      player.sendMessage(ChatColor.RED + "You can't milk a male!");
      event.setCancelled(true);
      return;
    }
    if (milkTrait != null) {
      MendelianGene mendelian = milkTrait.getMendelian();
      if (mendelian == null) {
        return;
      }
      if (mendelian.getPhenotype() == MendelianType.MENDELIAN_RECESSIVE) {
        AnimalItemConfig itemConfig = configManager.getItemConfig();

        double random = Genesis.RANDOM.nextDouble();
        double strawberryMilkChance = settingsConfig.getDouble(
            settingsConfig.getMainConfigurationSection(SettingsConfig.Marker.COW_TRAITS),
            SettingsConfig.Marker.STRAWBERRY_MILK_CHANCE);
        double goldenMilkChance = settingsConfig.getDouble(
            settingsConfig.getMainConfigurationSection(SettingsConfig.Marker.COW_TRAITS),
            SettingsConfig.Marker.GOLDEN_MILK_CHANCE);

        GenesisConfigurationSection itemConfiguration = null;

        if (random < strawberryMilkChance) {
          itemConfiguration = itemConfig.getMainConfigurationSection("strawberry-milk");
        } else if (random < goldenMilkChance) {
          itemConfiguration = itemConfig.getMainConfigurationSection("golden-milk");
        }
        if (itemConfiguration != null) {
          itemStack = new AbstractItem.EmbeddedDataBuilder(
              HorsePoop.getInstance(), itemConfiguration, false,
              new AppraisableItemData()).defaultBuild()
              .getItemStack();
        }
      }
    }
    inventory.addItem(itemStack);
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
