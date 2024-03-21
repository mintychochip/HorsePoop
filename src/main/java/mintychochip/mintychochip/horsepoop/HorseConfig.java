package mintychochip.mintychochip.horsepoop;

import mintychochip.genesis.config.abstraction.GenericConfig;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.genesis.util.EnumUtil;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.attributes.GeneticAttribute;
import mintychochip.mintychochip.horsepoop.container.attributes.SheepAttribute;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class HorseConfig extends GenericConfig {

    private final Map<EntityType,List<Trait>> entityTypeTraitMap = new HashMap<>();
    private final Set<String> enabledEntityTypes = new HashSet<>();
    private final List<Trait> allTraits = new ArrayList<>();
    private final GenesisConfigurationSection enabledEntities = getMainConfigurationSection("enabled-entities");
    private final GenesisConfigurationSection settings = getMainConfigurationSection("settings");

    public HorseConfig(String path, JavaPlugin plugin) {
        super(path, plugin);
        allTraits.addAll(Arrays.stream(GeneticAttribute.values()).toList());
        allTraits.addAll(Arrays.stream(SheepAttribute.values()).toList());

        for (String key : enabledEntities.getKeys(false)) {
            if(EnumUtil.isInEnum(EntityType.class,key)) {
                enabledEntityTypes.add(key);
            }
        }
        for (String enabledEntityType : enabledEntityTypes) {
            GenesisConfigurationSection configurationSection = enabledEntities.getConfigurationSection(enabledEntityType);
            List<Trait> entityTypeTraits = new ArrayList<>();
            for (String key : configurationSection.getKeys(false)) {
                Trait aTrait = isATrait(key);
                if(aTrait != null) {
                    entityTypeTraits.add(aTrait);
                }
            }
            entityTypeTraitMap.put(EntityType.valueOf(enabledEntityType),entityTypeTraits);
        }

    }
    private Trait isATrait(String key) {
        for (Trait allTrait : allTraits) {
            if(allTrait.getKey().equalsIgnoreCase(key)) {
                return allTrait;
            }
        }
        return null;
    }

    public Map<EntityType, List<Trait>> getEntityTypeTraitMap() {
        return entityTypeTraitMap;
    }

    public Set<String> getEnabledEntityTypes() {
        return enabledEntityTypes;
    }

    public List<Trait> getAllTraits() {
        return allTraits;
    }

    public GenesisConfigurationSection getEnabledEntities() {
        return enabledEntities;
    }

    public List<Trait> getEnabledAttributes(EntityType entityType) {
        return entityTypeTraitMap.get(entityType);
    }
    public int getMutations() {
        return settings.getInt(HorseMarker.mutations);
    }

    public GenesisConfigurationSection getAttribute(Trait trait, EntityType entityType) {
        GenesisConfigurationSection configurationSection = enabledEntities.getConfigurationSection(entityType.toString()).getConfigurationSection(trait.getKey());
        if(configurationSection.isNull()) {
            return null;
        }
        return configurationSection;
    }

    public GenesisConfigurationSection getMeta(GenesisConfigurationSection attribute) {
        if(attribute.isNull()) {
            return null;
        }
        GenesisConfigurationSection configurationSection = attribute.getConfigurationSection(HorseMarker.meta);
        if(configurationSection.isNull()) {
            return null;
        }
        return configurationSection;
    }
    public GenesisConfigurationSection getMeta(Trait trait, EntityType entityType) {
        GenesisConfigurationSection configurationSection = this.getAttribute(trait,entityType);
        if(configurationSection.isNull()) {
            return null;
        }
        return this.getMeta(configurationSection);
    }
    public boolean getConserved(Trait trait, EntityType entityType) {
        GenesisConfigurationSection configurationSection = getAttribute(trait,entityType);
        if(configurationSection.isNull()) {
            return false;
        }
        return configurationSection.getBoolean(HorseMarker.conserved);
    }
}
