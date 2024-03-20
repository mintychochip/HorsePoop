package mintychochip.mintychochip.horsepoop;

import mintychochip.genesis.manager.GenericConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager extends GenericConfigManager {
    private final HorseConfig horseConfig;
    private ConfigManager(JavaPlugin plugin) {
        super(plugin);
        horseConfig = new HorseConfig("horse.yml", plugin);
    }

    public static ConfigManager instanceConfigManager(JavaPlugin plugin) {
        if(!plugin.getName().equalsIgnoreCase("horsepoop")) {
            return null;
        }
        return new ConfigManager(plugin);
    }

    public HorseConfig getHorseConfig() {
        return horseConfig;
    }
}
