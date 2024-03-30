package mintychochip.mintychochip.horsepoop.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mintychochip.genesis.Genesis;
import mintychochip.genesis.config.GenesisConfig;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.genesis.items.container.AbstractItem;
import mintychochip.genesis.items.container.AppraisableItemData;
import mintychochip.genesis.util.WeightedRandom;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.api.events.FawnyMilkEvent;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.config.configs.AnimalItemConfig;
import mintychochip.mintychochip.horsepoop.config.configs.SettingsConfig;
import mintychochip.mintychochip.horsepoop.config.configs.SettingsConfig.Marker;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianType;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.CowTrait;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class MilkListener implements Listener {

  private List<ItemStack> dropTable = new ArrayList<>(); //add more orthodox way to add

  private Random random = new Random(System.currentTimeMillis());
  @EventHandler(priority = EventPriority.HIGHEST)
  private void onFawnyMilkEvent(final FawnyMilkEvent event) {
    if(event.isCancelled()) {
      return;
    }
    ItemStack bucket = event.getBucket();
    Player player = event.getPlayer();
    if (player.getGameMode() == GameMode.SURVIVAL) {
      bucket.setAmount(bucket.getAmount() - 1);
    }
    AnimalGenome animalGenome = event.getAnimalGenome();
    Gene milkTrait = animalGenome.getGeneFromTrait(CowTrait.STRAWBERRY_MILK);

    ConfigManager configManager = event.getConfigManager();
    SettingsConfig settingsConfig = configManager.getSettingsConfig();
    ItemStack itemStack = new ItemStack(Material.MILK_BUCKET);
    if (milkTrait != null) {

      MendelianGene mendelian = milkTrait.getMendelian();
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
    }
    dropTable.add(itemStack);
  }
  @EventHandler(priority = EventPriority.MONITOR)
  private void selectOneItemToAddToPlayer(final FawnyMilkEvent event) {
    if(event.isCancelled()) {
      return;
    }
    if (dropTable.isEmpty()) {
      return;
    }
    event.getPlayer().getInventory().addItem(dropTable.get(random.nextInt(dropTable.size())));
  }
}
