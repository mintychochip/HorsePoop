package mintychochip.mintychochip.horsepoop;

import mintychochip.genesis.config.abstraction.GenericConfig;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.genesis.util.EnumUtil;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.attributes.GeneticAttribute;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HorseConfig extends GenericConfig {

    private final GenesisConfigurationSection attributes = getMainConfigurationSection("attributes");

    private List<Trait> enabledAttributes = null;
    private final GenesisConfigurationSection settings = getMainConfigurationSection("settings");

    public HorseConfig(String path, JavaPlugin plugin) {
        super(path, plugin);
        if(!attributes.isNull()) {
            this.enabledAttributes = new ArrayList<>();
            for (String key : attributes.getKeys(false)) {
                if(isInEnum(key) != null) {
                    enabledAttributes.add(isInEnum(key));
                }
            }
        }
        Bukkit.broadcastMessage(enabledAttributes.toString());
    }
    private GeneticAttribute isInEnum(String key) {
        for (GeneticAttribute value : GeneticAttribute.values()) {
            if(Objects.equals(key, value.getKey())) {
                return value;
            }
        }
        return null;
    }

    public boolean traitIsEnabled(Trait trait) {
        return enabledAttributes.contains(trait);
    }

    public List<Trait> getEnabledAttributes() {
        return enabledAttributes;
    }

    public GenesisConfigurationSection getAttributes() {
        return attributes;
    }

    public int getMutations() {
        return settings.getInt(HorseMarker.mutations);
    }

    public GenesisConfigurationSection getAttribute(Trait trait) {
        GenesisConfigurationSection configurationSection = attributes.getConfigurationSection(trait.getKey());
        if(configurationSection.isNull()) {
            return null;
        }
        return configurationSection;
    }
    public GenesisConfigurationSection getMeta(Trait trait) {
        GenesisConfigurationSection configurationSection = this.getAttribute(trait);
        if(configurationSection.isNull()) {
            return null;
        }
        GenesisConfigurationSection meta = configurationSection.getConfigurationSection(HorseMarker.meta);
        if(meta.isNull()) {
            return null;
        }
        return meta;
    }
    public boolean getConserved(Trait trait) {
        GenesisConfigurationSection configurationSection = attributes.getConfigurationSection(trait.getKey());
        if(configurationSection.isNull()) {
            return false;
        }
        return configurationSection.getBoolean(HorseMarker.conserved);
    }
}
