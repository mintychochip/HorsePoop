package mintychochip.mintychochip.horsepoop.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mintychochip.genesis.Genesis;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.genesis.items.container.AbstractItem;
import mintychochip.genesis.items.container.AbstractItem.EmbeddedDataBuilder;
import mintychochip.genesis.items.container.AppraisableItemData;
import mintychochip.genesis.util.WeightedRandom;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.api.events.FawnyMilkEvent;
import mintychochip.mintychochip.horsepoop.api.events.FawnyShearEvent;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.config.configs.AnimalItemConfig;
import mintychochip.mintychochip.horsepoop.config.configs.SettingsConfig;
import mintychochip.mintychochip.horsepoop.config.configs.SettingsConfig.Marker;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;
import mintychochip.mintychochip.horsepoop.container.TraitFetcher;
import mintychochip.mintychochip.horsepoop.container.enums.FeatherColor;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianType;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.GenericGeneTrait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.CowGeneTrait;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class MilkListener implements Listener {

  private final ConfigManager configManager;

  private final TraitFetcher traitFetcher;

  public MilkListener(ConfigManager configManager, TraitFetcher traitFetcher) {
    this.configManager = configManager;
    this.traitFetcher = traitFetcher;
  }
  private List<ItemStack> dropTable = new ArrayList<>(); //add more orthodox way to add

  private Random random = new Random(System.currentTimeMillis());

  @EventHandler(priority = EventPriority.HIGHEST)
  private void onFawnyMilkEvent(final FawnyMilkEvent event) {
    if (event.isCancelled()) {
      return;
    }
    ItemStack bucket = event.getBucket();
    Player player = event.getPlayer();

    if (player.getGameMode() == GameMode.SURVIVAL) {
      bucket.setAmount(bucket.getAmount() - 1);
    }
    AnimalGenome animalGenome = event.getAnimalGenome();

    SettingsConfig settingsConfig = configManager.getSettingsConfig();
    ItemStack itemStack = new ItemStack(Material.MILK_BUCKET);
    Gene milkTrait = traitFetcher.getGeneFromGeneList(animalGenome,
        CowGeneTrait.STRAWBERRY_MILK);
    MendelianGene mendelian = traitFetcher.getMendelian(milkTrait);
    if (mendelian.getPhenotype() == MendelianType.MENDELIAN_RECESSIVE) {
      AnimalItemConfig itemConfig = configManager.getItemConfig();
      WeightedRandom<GenesisConfigurationSection> weightedRandom = new WeightedRandom<>();
      GenesisConfigurationSection strawberry = itemConfig.getMainConfigurationSection(
          "strawberry-milk");
      if (strawberry.isNull()) {
        throw new RuntimeException(
            "Strawberry milk section is null, and the trait is enabled check the config.");
      }
      GenesisConfigurationSection golden = itemConfig.getMainConfigurationSection(
          "golden-milk");
      if (golden.isNull()) {
        throw new RuntimeException(
            "Golden milk section is null, and the trait is enabled check the config.");
      }
      weightedRandom.addItem(strawberry, settingsConfig.getDouble(Marker.STRAWBERRY_WEIGHT))
          .addItem(golden, settingsConfig.getDouble(Marker.GOLDEN_WEIGHT));
      Bukkit.broadcastMessage(weightedRandom.getTotalWeight() + "");
      itemStack = new AbstractItem.EmbeddedDataBuilder(
          HorsePoop.getInstance(), weightedRandom.chooseOne(), false,
          new AppraisableItemData()).defaultBuild()
          .getItemStack(); //both configSections are notnull
    }
    dropTable.add(itemStack);
  }
  @EventHandler
  private void onShearParrotEvent(final FawnyShearEvent event) {
    if (event.isCancelled()) {
      return;
    }
    ItemStack shears = event.getShears();
    LivingEntity livingEntity = event.getLivingEntity();
    if(livingEntity.getType() != EntityType.PARROT) {
      return;
    }
    AnimalGenome animalGenome = event.getAnimalGenome();
    Gene featherColor = traitFetcher.getGeneFromGeneList(animalGenome,GenericGeneTrait.FEATHER_COLOR);
    if(featherColor == null) {
      return;
    }
    String featherKey = FeatherColor.valueOf(featherColor.getValue()).getFeatherKey();
    GenesisConfigurationSection featherSection = configManager.getItemConfig()
        .getMainConfigurationSection(featherKey
            );
    if(featherSection.isNull()) {
      throw new RuntimeException("Feather section: " + featherKey + " is null.");
    }
    ItemStack itemStack = new EmbeddedDataBuilder(HorsePoop.getInstance(), featherSection, false,
        new AppraisableItemData()).defaultBuild().getItemStack();
    Gene yield = traitFetcher.getGeneFromGeneList(animalGenome,GenericGeneTrait.YIELD);
    int max = yield != null ? Integer.parseInt(yield.getValue()) : 1;
    itemStack.setAmount(this.calculateRandomYield(max,shears));

    Location location = event.getLivingEntity().getLocation();
    World world = location.getWorld();
    if(world == null) {
      return;
    }
    world.dropItem(location,itemStack);
  }
  @EventHandler
  private void onShearChickenEvent(final FawnyShearEvent event) {
    if(event.isCancelled()) {
      return;
    }
    Bukkit.broadcastMessage("here");
    ItemStack shears = event.getShears();
    LivingEntity livingEntity = event.getLivingEntity();
    if(livingEntity.getType() != EntityType.CHICKEN) {
      return;
    }
    AnimalGenome animalGenome = event.getAnimalGenome();
    Gene yield = traitFetcher.getGeneFromGeneList(animalGenome,GenericGeneTrait.YIELD);
    ItemStack itemStack = new ItemStack(Material.FEATHER);
    itemStack.setAmount(this.calculateRandomYield(yield != null ? Integer.parseInt(yield.getValue()) : 1, shears));

    Location location = event.getLivingEntity().getLocation();
    World world = location.getWorld();
    if(world == null) {
      return;
    }
    world.dropItem(location,itemStack);
  }
  @EventHandler
  private void onShearSheepEvent(final FawnyShearEvent event) {

  }
  @EventHandler(priority = EventPriority.MONITOR)
  private void selectOneItemToAddToPlayer(final FawnyMilkEvent event) {
    if (event.isCancelled()) {
      return;
    }
    if (dropTable.isEmpty()) {
      return;
    }
    event.getPlayer().getInventory().addItem(dropTable.get(random.nextInt(dropTable.size())));
    dropTable.clear();
  }
  private int calculateRandomYield(int max , int min, ItemStack itemStack) {
    Integer fortuneLevel = itemStack.getEnchantments().get(Enchantment.LOOT_BONUS_BLOCKS);
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
  private int calculateRandomYield(int max, ItemStack itemStack) {
    return this.calculateRandomYield(max,1,itemStack);
  }

  private int calculateMaxYield(int fortuneLevel, int i) {
    return (int) (i * (((double) 1 / (fortuneLevel + 2)) + ((double) (fortuneLevel + 1) / 2)));
  }
}
