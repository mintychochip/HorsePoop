package mintychochip.mintychochip.horsepoop.config;

import mintychochip.genesis.config.abstraction.GenericConfig;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.genesis.util.EnumUtil;
import mintychochip.mintychochip.horsepoop.HorseMarker;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.CowTrait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.GenericTrait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.GeneticAttribute;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.SheepTrait;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class EntityConfig extends GenericConfig {

  public static class Marker {

    public static final String enabledEntities = "enabled-entities";
  }

  private final List<Trait> allTraits;

  private Set<EntityType> enabledEntityTypes = new HashSet<>();
  private Map<EntityType, List<Trait>> entityTypeTraitMap = new HashMap<>();

  private List<Trait> loadAllTraits() {
    List<Trait> traits = new ArrayList<>();
    traits.addAll(Arrays.stream(GeneticAttribute.values()).toList());
    traits.addAll(Arrays.stream(SheepTrait.values()).toList());
    traits.addAll(Arrays.stream(GenericTrait.values()).toList());
    traits.addAll(Arrays.stream(CowTrait.values()).toList());
    return traits;
  }

  public EntityConfig(String path, JavaPlugin plugin) {
    super(path, plugin);

    this.allTraits = loadAllTraits();
    reload();
  }

  public boolean isEntityEnabled(EntityType entityType) {
    return enabledEntityTypes.contains(entityType);
  }

  public Set<EntityType> getEnabledEntityTypes() {
    return enabledEntityTypes;
  }
  @Override
  public void reload() {
    super.reload();
    this.enabledEntityTypes.clear();
    for (String key : this.getMainConfigurationSection(
        Marker.enabledEntities).getKeys(false)) {
      String upperCase = key.toUpperCase();
      if (EnumUtil.isInEnum(EntityType.class, upperCase)) {
        enabledEntityTypes.add(EntityType.valueOf(upperCase));
      }
    }
    this.entityTypeTraitMap = new HashMap<>();
    GenesisConfigurationSection enabledEntitySection = this.getMainConfigurationSection(
        Marker.enabledEntities);
    for (String key : this.getStringEnabledEntityTypes()) {
      GenesisConfigurationSection entity = enabledEntitySection.getConfigurationSection(
          key);
      List<Trait> entityTypeTraits = new ArrayList<>();
      for (String trait : entity.getKeys(false)) {
        Trait aTrait = this.isATrait(trait);
        if (aTrait != null) {
          entityTypeTraits.add(aTrait);
        }
      }
      this.entityTypeTraitMap.put(EntityType.valueOf(key), entityTypeTraits);
    }
  }

  public Map<EntityType, List<Trait>> getEntityTypeTraitMap() {
    return entityTypeTraitMap;
  }

  public Set<String> getStringEnabledEntityTypes() {
    Set<String> stringEnabledEntityTypes = new HashSet<>();
    for (EntityType enabledEntityType : this.enabledEntityTypes) {
      stringEnabledEntityTypes.add(enabledEntityType.toString());
    }
    return stringEnabledEntityTypes;
  }

  public boolean isTraitEnabled(Trait trait, EntityType type) {
    return getEnabledAttributes(type).stream().anyMatch(x -> x == trait);
  }

  private Trait isATrait(String key) {
    for (Trait allTrait : allTraits) {
      if (allTrait.getKey().equalsIgnoreCase(key)) {
        return allTrait;
      }
    }
    return null;
  }

  public List<Trait> getAllTraits() {
    return allTraits;
  }

  public List<Trait> getEnabledAttributes(EntityType entityType) {
    return this.getEntityTypeTraitMap().get(entityType);
  }

  public GenesisConfigurationSection getAttribute(Trait trait, EntityType entityType) {
    GenesisConfigurationSection configurationSection = this.getMainConfigurationSection(
        Marker.enabledEntities).getConfigurationSection(
        entityType.toString()).getConfigurationSection(trait.getKey());
    if (configurationSection.isNull()) {
      return null;
    }
    return configurationSection;
  }

  public GenesisConfigurationSection getMeta(GenesisConfigurationSection attribute) {
    if (attribute.isNull()) {
      return null;
    }
    GenesisConfigurationSection configurationSection = attribute.getConfigurationSection(
        HorseMarker.meta);
    if (configurationSection.isNull()) {
      return null;
    }
    return configurationSection;
  }

  public GenesisConfigurationSection getMeta(Trait trait, EntityType entityType) {
    GenesisConfigurationSection configurationSection = this.getAttribute(trait, entityType);
    Bukkit.broadcastMessage(trait.getKey());
    if (configurationSection.isNull()) {
      return null;
    }
    return this.getMeta(configurationSection);
  }

  public boolean getConserved(Trait trait, EntityType entityType) {
    GenesisConfigurationSection configurationSection = getAttribute(trait, entityType);
    if (configurationSection.isNull()) {
      return false;
    }
    return configurationSection.getBoolean(HorseMarker.conserved);
  }
}
