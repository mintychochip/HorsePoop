package mintychochip.mintychochip.horsepoop;

import mintychochip.genesis.manager.GenericConfigManager;
import mintychochip.mintychochip.horsepoop.config.AnimalItemConfig;
import mintychochip.mintychochip.horsepoop.config.SettingsConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager extends GenericConfigManager {
    private final EntityConfig entityConfig;

    private final SettingsConfig settingsConfig;

    private final AnimalItemConfig itemConfig;

    private ConfigManager(JavaPlugin plugin) {
        super(plugin);
        entityConfig = new EntityConfig("entities.yml", plugin);
        settingsConfig = new SettingsConfig("settings.yml", plugin);
        itemConfig = new AnimalItemConfig("items.yml",plugin);
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

    public EntityConfig getHorseConfig() {
        return entityConfig;
    }

    public SettingsConfig getSettingsConfig() {
        return settingsConfig;
    }
}
