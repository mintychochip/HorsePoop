package mintychochip.mintychochip.horsepoop.config.configs;

import com.google.gson.Gson;
import mintychochip.genesis.config.abstraction.GenericConfig;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.genesis.util.EnumUtil;
import mintychochip.mintychochip.horsepoop.HorseMarker;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.config.AnimalTraitMeta;
import mintychochip.mintychochip.horsepoop.config.AnimalTraitWrapper;
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

  private Set<EntityType> enabledEntityTypes;
  private Map<EntityType, List<AnimalTraitWrapper>> entityTypeTraitMap = new HashMap<>();

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
    this.enabledEntityTypes = resetEnabledEntityTypes();
    this.entityTypeTraitMap = resetEntityTypeTraitMap();
  }

  private Set<EntityType> resetEnabledEntityTypes() {
    Set<EntityType> entityTypes = new HashSet<>();
    for (String key : configReader.getConfig().getKeys(false)) {
      String upperCase = key.toUpperCase();
      if (EnumUtil.isInEnum(EntityType.class, upperCase)) {
        entityTypes.add(EntityType.valueOf(upperCase));
      }
    }
    return entityTypes;
  }
  public boolean isTraitEnabled(Trait trait, EntityType entityType) {
    return this.getEntityTraits(entityType).stream().anyMatch(x -> x == trait);
  }
  public List<Trait> getEntityTraits(EntityType entityType) {
    Gson gson = new Gson();
    List<Trait> traits = new ArrayList<>();
    for (String s : this.getStringList(entityType.toString())) {
      AnimalTraitWrapper animalTraitWrapper = gson.fromJson(s, AnimalTraitWrapper.class);
      traits.add(isATrait(animalTraitWrapper.trait()));
    }
    return traits;
  }

  private Map<EntityType, List<AnimalTraitWrapper>> resetEntityTypeTraitMap() {
    Map<EntityType, List<AnimalTraitWrapper>> entityTypeListHashMap = new HashMap<>();
    Gson gson = new Gson();
    for (String key : this.getKeys(false)) {
      String upperCase = key.toUpperCase();
      if (EnumUtil.isInEnum(EntityType.class, upperCase)) {
        EntityType entityType = EntityType.valueOf(upperCase);
        List<AnimalTraitWrapper> traitWrappers = new ArrayList<>();
        for (String json : this.getStringList(key)) {
          AnimalTraitWrapper animalTraitWrapper = gson.fromJson(json, AnimalTraitWrapper.class);
          if (animalTraitWrapper != null) {
            traitWrappers.add(animalTraitWrapper);
          }
        }
        entityTypeListHashMap.put(entityType, traitWrappers);
      }
    }
    return entityTypeListHashMap;
  }

  public Map<EntityType, List<AnimalTraitWrapper>> getEntityTypeTraitMap() {
    return entityTypeTraitMap;
  }

  public Trait getTraitFromWrapper(AnimalTraitWrapper animalTraitWrapper) {
    Trait aTrait = this.isATrait(animalTraitWrapper.trait());
    if(aTrait == null) {
      return null;
    }
    return aTrait;
  }
  public Trait isATrait(String key) {
    for (Trait allTrait : allTraits) {
      if (allTrait.getKey().equalsIgnoreCase(key)) {
        return allTrait;
      }
    }
    return null;
  }
  public AnimalTraitWrapper getTrait(Trait trait, EntityType entityType) {
    List<AnimalTraitWrapper> traitWrappers = this.entityTypeTraitMap.get(entityType);
    Optional<AnimalTraitWrapper> optionalWrapper = traitWrappers.stream()
        .filter(x -> x.trait().equals(trait.getKey()))
        .findFirst();

    return optionalWrapper.orElse(null);
  }
  public List<Trait> getAllTraits() {
    return allTraits;
  }
}
