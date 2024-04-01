package mintychochip.mintychochip.horsepoop.config;

import com.google.gson.Gson;
import mintychochip.genesis.manager.GenericConfigManager;
import mintychochip.genesis.util.Rarity;
import mintychochip.genesis.util.WeightedRandom;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.config.configs.AnimalItemConfig;
import mintychochip.mintychochip.horsepoop.config.configs.EntityConfig;
import mintychochip.mintychochip.horsepoop.config.configs.SettingsConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager extends GenericConfigManager {

  private final Gson gson = HorsePoop.GSON;
  private final WeightedRandom<Rarity> randomWeight;
  private final EntityConfig entityConfig;
  private final SettingsConfig settingsConfig;
  private final AnimalItemConfig itemConfig;
  private ConfigManager(JavaPlugin plugin) {
    super(plugin);
    entityConfig = new EntityConfig("entities.yml", plugin);
    settingsConfig = new SettingsConfig("settings.yml", plugin);
    itemConfig = new AnimalItemConfig("items.yml", plugin);
    randomWeight = new WeightedRandom<>();
    randomWeight.addItem(Rarity.COMMON, 0.50)
        .addItem(Rarity.UNCOMMON, 0.20)
        .addItem(Rarity.RARE, 0.15)
        .addItem(Rarity.EPIC, 0.10)
        .addItem(Rarity.LEGENDARY, 0.04)
        .addItem(Rarity.ARTIFACT, 0.01);  // Artifact with a weight of 0.01
  }

  public void reload() {
    entityConfig.reload();
    settingsConfig.reload();
    itemConfig.reload();
  }
  public static ConfigManager instanceConfigManager(JavaPlugin plugin) {
    if (!plugin.getName().equalsIgnoreCase("horsepoop")) {
      return null;
    }
    return new ConfigManager(plugin);
  }

  public WeightedRandom<Rarity> getRandomWeight() {
    return randomWeight;
  }

  public Gson getGson() {
    return gson;
  }

  public AnimalItemConfig getItemConfig() {
    return itemConfig;
  }


  public EntityConfig getEntityConfig() {
    return entityConfig;
  }

  public SettingsConfig getSettingsConfig() {
    return settingsConfig;
  }
}
