package mintychochip.mintychochip.horsepoop.config;

import mintychochip.genesis.config.abstraction.GenericConfig;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public class SettingsConfig extends GenericConfig {

    private class Marker {
        public static final String recombinanceSettings = "recombinance-settings";

        public static final String chance = "chance";

        public static final String maxCount = "max-count";
    }

    private final GenesisConfigurationSection recombinanceSettings = getMainConfigurationSection(Marker.recombinanceSettings);

    public SettingsConfig(String path, JavaPlugin plugin) {
        super(path, plugin);
    }

    public GenesisConfigurationSection getRecombinanceSettings() {
        return recombinanceSettings;
    }

    public double getRecombinanceChance() {
        return recombinanceSettings.getDouble(Marker.chance);
    }

    public int getRecombinanceMaxCount() {
        return recombinanceSettings.getInt(Marker.maxCount);
    }

}
