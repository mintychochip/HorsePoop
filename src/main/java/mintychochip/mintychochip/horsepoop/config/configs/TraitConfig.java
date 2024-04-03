package mintychochip.mintychochip.horsepoop.config.configs;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import mintychochip.genesis.config.abstraction.GenericConfig;
import mintychochip.mintychochip.horsepoop.config.AnimalTraitWrapper;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.TypeAdapters.TraitMetaAdapter;
import org.bukkit.entity.EntityType;

public class TraitConfig<U extends Trait, T extends Characteristic> {

  private final List<U> traitEnums = new ArrayList<>();
  private Class<U> tClass;
  private Class<T> aClass;

  public TraitConfig(Class<U> tClass, Class<T> aClass) {
    this.tClass = tClass;
    this.aClass = aClass;
  }

  public U getTrait(String trait) {
      Optional<U> first = traitEnums.stream().filter(x -> x.getKey().equals(trait)).findFirst();
      return first.orElse(null);
  }
  public <Y extends U> void loadEnums(Class<Y> enumClass, Y[] values) {
    if (tClass.isAssignableFrom(enumClass) && enumClass.isEnum()) {
      Collections.addAll(this.traitEnums, values);
    }
  }

  private final Map<EntityType, List<AnimalTraitWrapper<T>>> entityTypeTraitMap = new HashMap<>();

  public void loadTraitConfigs(GenericConfig config) {
    Type type = new TypeToken<AnimalTraitWrapper<T>>() {
    }.getType();
    Gson gson = new GsonBuilder().registerTypeAdapter(new TypeToken<AnimalTraitWrapper<T>>() {
        }.getType(),
        new TraitMetaAdapter<>(aClass)).create();
    for (String key : config.getKeys(false)) { //entityType keys
      List<AnimalTraitWrapper<T>> wrappers = new ArrayList<>();
      for (String s : config.getStringList(key)) {
        AnimalTraitWrapper<T> animalTraitWrapper = gson.fromJson(s, type);
        if (animalTraitWrapper != null) {
          wrappers.add(animalTraitWrapper);
        }
      }
      entityTypeTraitMap.put(Enum.valueOf(EntityType.class, key.toUpperCase()), wrappers);
    }
  }


  public U getTraitFromWrapper(AnimalTraitWrapper<T> animalTraitWrapper) {
    U traitEnum = traitEnums.stream()
        .filter(x -> x.getKey().equalsIgnoreCase(animalTraitWrapper.trait())).findFirst()
        .orElse(null);
    if (traitEnum == null) {
      return null;
    }
    return traitEnum;
  }

  public List<U> getTraitEnums() {
    return traitEnums;
  }

  public List<U> getAllTraits(
      EntityType entityType) { //filters by trait type, so gene or characteristic
    if (!entityTypeTraitMap.containsKey(entityType)) {
      return null;
    }
    return entityTypeTraitMap.get(entityType).stream().map(
        this::getTraitFromWrapper).toList();
  }

  public T getMeta(U trait, EntityType entityType) {
    AnimalTraitWrapper<T> traitWrapper = this.getTraitWrapper(trait, entityType);
    if (traitWrapper == null) {
      return null;
    }
    return traitWrapper.meta();
  }


  public AnimalTraitWrapper<T> getTraitWrapper(U trait, EntityType entityType) {
    List<AnimalTraitWrapper<T>> animalTraitWrappers = entityTypeTraitMap.get(entityType);
    return animalTraitWrappers.stream().filter(x -> x.trait().equals(trait.getKey())).findFirst()
        .orElse(null);
  }

  public Map<EntityType, List<AnimalTraitWrapper<T>>> getEntityTypeTraitMap() {
    return entityTypeTraitMap;
  }
}
