package mintychochip.mintychochip.horsepoop.config.configs;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import mintychochip.genesis.config.abstraction.GenericConfig;
import mintychochip.genesis.util.ConfigReader;
import mintychochip.mintychochip.horsepoop.config.AnimalTraitWrapper;
import mintychochip.mintychochip.horsepoop.config.GeneTraitMeta;
import mintychochip.mintychochip.horsepoop.config.TraitMeta;
import mintychochip.mintychochip.horsepoop.container.GeneTrait;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.TypeAdapters.TraitMetaAdapter;
import mintychochip.mintychochip.horsepoop.container.TypeAdapters.TraitTypeAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

public class TraitConfig<T extends Trait, U extends TraitMeta> {

  private final List<T> traitEnums = new ArrayList<>();
  private Class<T> tClass;
  private Class<U> aClass;

  public TraitConfig(Class<T> tClass, Class<U> aClass) {
    this.tClass = tClass;
    this.aClass = aClass;
  }

  public T getTrait(String trait) {
      Optional<T> first = traitEnums.stream().filter(x -> x.getKey().equals(trait)).findFirst();
      return first.orElse(null);
  }
  public <Y extends T> void loadEnums(Class<Y> enumClass, Y[] values) {
    if (tClass.isAssignableFrom(enumClass) && enumClass.isEnum()) {
      Collections.addAll(this.traitEnums, values);
    }
  }

  private final Map<EntityType, List<AnimalTraitWrapper<U>>> entityTypeTraitMap = new HashMap<>();

  public void loadTraitConfigs(GenericConfig config) {
    Type type = new TypeToken<AnimalTraitWrapper<U>>() {
    }.getType();
    Gson gson = new GsonBuilder().registerTypeAdapter(new TypeToken<AnimalTraitWrapper<U>>() {
        }.getType(),
        new TraitMetaAdapter<>(aClass)).create();
    for (String key : config.getKeys(false)) { //entityType keys
      List<AnimalTraitWrapper<U>> wrappers = new ArrayList<>();
      for (String s : config.getStringList(key)) {
        AnimalTraitWrapper<U> animalTraitWrapper = gson.fromJson(s, type);
        if (animalTraitWrapper != null) {
          wrappers.add(animalTraitWrapper);
        }
      }
      entityTypeTraitMap.put(Enum.valueOf(EntityType.class, key.toUpperCase()), wrappers);
    }
  }


  public T getTraitFromWrapper(AnimalTraitWrapper<U> animalTraitWrapper) {
    T traitEnum = traitEnums.stream()
        .filter(x -> x.getKey().equalsIgnoreCase(animalTraitWrapper.trait())).findFirst()
        .orElse(null);
    if (traitEnum == null) {
      return null;
    }
    return traitEnum;
  }

  public List<T> getTraitEnums() {
    return traitEnums;
  }

  public List<T> getAllTraits(
      EntityType entityType) { //filters by trait type, so gene or characteristic
    if (!entityTypeTraitMap.containsKey(entityType)) {
      return null;
    }
    return entityTypeTraitMap.get(entityType).stream().map(
        this::getTraitFromWrapper).toList();
  }

  public U getMeta(T trait, EntityType entityType) {
    AnimalTraitWrapper<U> traitWrapper = this.getTraitWrapper(trait, entityType);
    if (traitWrapper == null) {
      return null;
    }
    return traitWrapper.meta();
  }


  public AnimalTraitWrapper<U> getTraitWrapper(T trait, EntityType entityType) {
    List<AnimalTraitWrapper<U>> animalTraitWrappers = entityTypeTraitMap.get(entityType);
    return animalTraitWrappers.stream().filter(x -> x.trait().equals(trait.getKey())).findFirst()
        .orElse(null);
  }

  public Map<EntityType, List<AnimalTraitWrapper<U>>> getEntityTypeTraitMap() {
    return entityTypeTraitMap;
  }
}
