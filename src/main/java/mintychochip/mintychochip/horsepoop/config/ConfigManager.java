package mintychochip.mintychochip.horsepoop.config;

import mintychochip.genesis.manager.GenericConfigManager;
import mintychochip.mintychochip.horsepoop.config.configs.AnimalItemConfig;
import mintychochip.mintychochip.horsepoop.config.configs.EntityConfig;
import mintychochip.mintychochip.horsepoop.config.configs.PerkConfig;
import mintychochip.mintychochip.horsepoop.config.configs.SettingsConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager extends GenericConfigManager {
  private final EntityConfig entityConfig;
  private final SettingsConfig settingsConfig;
  private final AnimalItemConfig itemConfig;

  private final PerkConfig perkConfig;
  private ConfigManager(JavaPlugin plugin) {
    super(plugin);
    entityConfig = new EntityConfig("entities.yml", plugin);
    settingsConfig = new SettingsConfig("settings.yml", plugin);
    itemConfig = new AnimalItemConfig("items.yml", plugin);
    perkConfig = new PerkConfig("perks.yml",plugin);
  }

  public PerkConfig getPerkConfig() {
    return perkConfig;
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
