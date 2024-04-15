package mintychochip.mintychochip.horsepoop.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.genesis.items.container.AbstractItem;
import mintychochip.genesis.items.container.AbstractItem.ItemBuilder;
import mintychochip.genesis.items.container.AppraisableItemData;
import mintychochip.genesis.util.WeightedRandom;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.api.Fetcher;
import mintychochip.mintychochip.horsepoop.api.events.finalevents.FawnyMilkEvent;
import mintychochip.mintychochip.horsepoop.api.events.finalevents.FawnyShearEvent;
import mintychochip.mintychochip.horsepoop.api.markers.Gene;
import mintychochip.mintychochip.horsepoop.api.markers.Phenotypic;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.config.configs.AnimalItemConfig;
import mintychochip.mintychochip.horsepoop.config.configs.SettingsConfig;
import mintychochip.mintychochip.horsepoop.config.configs.SettingsConfig.Marker;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;
import mintychochip.mintychochip.horsepoop.container.ValueFetcher;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianType;
import mintychochip.mintychochip.horsepoop.container.enums.traits.CowGene;
import mintychochip.mintychochip.horsepoop.container.enums.traits.GenericGene;
import mintychochip.mintychochip.horsepoop.factories.DyeSelector;
import mintychochip.mintychochip.horsepoop.util.math.RandomYield;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class MilkListener implements Listener {

  private final ConfigManager configManager;

  private final Fetcher<Gene> geneFetcher;

  private final DyeSelector dyeSelector;

  private final Fetcher<Phenotypic> phenotypicFetcher = new ValueFetcher<>();

  public MilkListener(ConfigManager configManager, Fetcher<Gene> geneFetcher, DyeSelector dyeSelector) {
    this.configManager = configManager;
    this.geneFetcher=  geneFetcher;
    this.dyeSelector = dyeSelector;
  }
  private List<ItemStack> dropTable = new ArrayList<>(); //add more orthodox way to add
  @EventHandler(priority = EventPriority.HIGHEST)
  private void onFawnyMilkEvent(final FawnyMilkEvent event) {
    Bukkit.broadcastMessage("milk");

    if (event.isCancelled()) {
      return;
    }
    ItemStack bucket = event.getInstrument();
    Player player = event.getPlayer();

    if (player.getGameMode() == GameMode.SURVIVAL) {
      bucket.setAmount(bucket.getAmount() - 1);
    }
    AnimalGenome animalGenome = event.getAnimalGenome();

    SettingsConfig settingsConfig = configManager.getSettingsConfig();
    ItemStack itemStack = new ItemStack(Material.MILK_BUCKET);
    BaseTrait<Gene> strawberryTrait = geneFetcher.getTraitFromList(animalGenome.getGenes(),
        CowGene.STRAWBERRY_MILK);
    MendelianGene mendelian = geneFetcher.getMendelian(strawberryTrait);
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
      itemStack = new AbstractItem.EmbeddedDataBuilder(
          HorsePoop.getInstance(), weightedRandom.chooseOne(), false,
          new AppraisableItemData()).defaultBuild()
          .getItemStack(); //both configSections are notnull
    }
    dropTable.add(itemStack);
  }

//  @EventHandler
//  private void onShearChickenEvent(final FawnyShearEvent event) {
//    if(event.isCancelled()) {
//      return;
//    }
//
//    ItemStack shears = event.getShears();
//    LivingEntity livingEntity = event.getLivingEntity();
//    if(livingEntity.getType() != EntityType.CHICKEN) {
//      return;
//    }
//    int damage = 1;
//    if(livingEntity.getType() == EntityType.PARROT) {
//      damage = 2;
//    }
//    livingEntity.damage(damage,event.getPlayer());
//    AnimalGenome animalGenome = event.getAnimalGenome();
//    Gene yield = traitFetcher.getGeneFromList(animalGenome,GenericGeneTrait.YIELD);
//    ItemStack itemStack = new ItemStack(Material.FEATHER);
//    itemStack.setAmount(this.calculateRandomYield(yield != null ? Integer.parseInt(yield.getValue()) : 1, shears));
//
//    Location location = event.getLivingEntity().getLocation();
//    World world = location.getWorld();
//    if(world == null) {
//      return;
//    }
//    world.dropItem(location,itemStack);
//  }
//  @EventHandler
//  private void onShearSheepEvent(final FawnyShearEvent event) {
//
//  }
  @EventHandler(priority = EventPriority.MONITOR)
  private void selectOneItemToAddToPlayer(final FawnyMilkEvent event) {
    if (event.isCancelled()) {
      return;
    }
    if (dropTable.isEmpty()) {
      return;
    }
    event.getPlayer().getInventory().addItem(dropTable.get(new Random().nextInt(dropTable.size())));
    dropTable.clear();
  }
  @EventHandler
  private void onFauwnyShearSheepEvent(final FawnyShearEvent event) {
    AnimalGenome animalGenome = event.getAnimalGenome();
    LivingEntity livingEntity = event.getLivingEntity();
    ItemStack shears = event.getInstrument();
    if(livingEntity instanceof Sheep sheep && !sheep.isSheared()) {
      double numeric = geneFetcher.getNumeric(animalGenome.getGenes(), GenericGene.YIELD);
      int max = numeric != 0 ? (int) numeric : 1;
      sheep.setSheared(true);

      DyeColor dyeColor = dyeSelector.calculateDyeColor(animalGenome, livingEntity);
      if(dyeColor == null) {
        return;
      }
      AbstractItem abstractItem = new ItemBuilder(HorsePoop.getInstance(),
          Material.valueOf(dyeColor + "_WOOL"), false).setAmount(
          RandomYield.calculateRandomYield(max, shears)).build();
      Location location = livingEntity.getLocation();
      World world = location.getWorld();
      world.playSound(location, Sound.ENTITY_SHEEP_SHEAR,1,1);
      world.dropItem(location,abstractItem.getItemStack());
    }
  }
}
