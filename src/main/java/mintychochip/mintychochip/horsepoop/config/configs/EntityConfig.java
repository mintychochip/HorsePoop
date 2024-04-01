package mintychochip.mintychochip.horsepoop.config.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mintychochip.genesis.config.abstraction.GenericConfig;
import mintychochip.genesis.util.EnumUtil;
import mintychochip.mintychochip.horsepoop.config.AnimalTraitWrapper;
import mintychochip.mintychochip.horsepoop.config.CharacteristicTraitMeta;
import mintychochip.mintychochip.horsepoop.config.TraitMeta;
import mintychochip.mintychochip.horsepoop.container.CharacteristicTrait;
import mintychochip.mintychochip.horsepoop.container.GeneTrait;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.TypeAdapters.TraitMetaAdapter;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.TraitType;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.CowGeneTrait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.GenericGeneTrait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.GeneticAttribute;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.SheepGeneTrait;
import mintychochip.mintychochip.horsepoop.container.enums.characteristics.GenericCharacteristicTrait;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class EntityConfig extends GenericConfig {
  private final List<Trait> allTraitEnums = new ArrayList<>();
  private Set<EntityType> enabledEntityTypes;
  private Map<EntityType, List<AnimalTraitWrapper>> entityTypeTraitMap = new HashMap<>();

  private void loadAllTraits() {
    this.allTraitEnums.clear();
    allTraitEnums.addAll(Arrays.stream(GeneticAttribute.values()).toList());
    allTraitEnums.addAll(Arrays.stream(SheepGeneTrait.values()).toList());
    allTraitEnums.addAll(Arrays.stream(GenericGeneTrait.values()).toList());
    allTraitEnums.addAll(Arrays.stream(CowGeneTrait.values()).toList());
    allTraitEnums.addAll(Arrays.stream(GenericCharacteristicTrait.values()).toList());
  }
  public EntityConfig(String path, JavaPlugin plugin) {
    super(path, plugin);
    loadAllTraits();
    Bukkit.getScheduler().runTaskLater(plugin,x -> {
      reload();
    },3L);
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
  public boolean isTraitEnabledForEntity(Trait trait, EntityType entityType) {
    return this.entityTypeTraitMap.get(entityType).stream().anyMatch(x -> x.trait().equalsIgnoreCase(trait.getKey()));
  }
  public List<Trait> getAllTraits(EntityType entityType, TraitType traitType) { //filters by trait type, so gene or characteristic
    if(!entityTypeTraitMap.containsKey(entityType)) {
      return null;
    }
    return entityTypeTraitMap.get(entityType).stream()
        .filter(x -> x.meta().type().equalsIgnoreCase(traitType.getKey())).map(
            this::getTraitFromWrapper).toList();
  }

  public List<GeneTrait> getAllGeneTraits(EntityType entityType) {
    return this.getAllTraits(entityType,TraitType.GENE).stream().filter(x -> x instanceof GeneTrait).map(x -> (GeneTrait) x).toList();
  }
  public List<CharacteristicTrait> getAllCharacteristicTraits(EntityType entityType) {
    return this.getAllTraits(entityType,TraitType.CHARACTERISTIC).stream().map(x -> (CharacteristicTrait) x).toList();
  }
  private Map<EntityType, List<AnimalTraitWrapper>> resetEntityTypeTraitMap() {
    Map<EntityType, List<AnimalTraitWrapper>> entityTypeListHashMap = new HashMap<>();
    Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(TraitMeta.class,new TraitMetaAdapter()).create();
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
}
