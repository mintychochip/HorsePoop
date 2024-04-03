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
import mintychochip.mintychochip.horsepoop.metas.Meta;
import org.bukkit.entity.EntityType;

public class TraitConfig<U extends Trait> {

  private final List<U> traitEnums = new ArrayList<>();
  private Class<U> tClass;
  public TraitConfig(Class<U> tClass) {
    this.tClass = tClass;
  }
  public <Y extends U> void loadEnums(Class<Y> enumClass, Y[] values) {
    if (tClass.isAssignableFrom(enumClass) && enumClass.isEnum()) {
      Collections.addAll(this.traitEnums, values);
    }
  }

  private final Map<EntityType, List<AnimalTraitWrapper<U>>> entityTypeTraitMap = new HashMap<>();

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


  public U getTraitFromWrapper(AnimalTraitWrapper<U> animalTraitWrapper) {
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

  public Meta<U> getMeta(U trait, EntityType entityType) {
    AnimalTraitWrapper<U> traitWrapper = this.getTraitWrapper(trait, entityType);
    if (traitWrapper == null) {
      return null;
    }
    return traitWrapper.meta();
  }


  public AnimalTraitWrapper<U> getTraitWrapper(U trait, EntityType entityType) {
    List<AnimalTraitWrapper<U>> animalTraitWrappers = entityTypeTraitMap.get(entityType);
    return animalTraitWrappers.stream().filter(x -> x.trait().equals(trait.getKey())).findFirst()
        .orElse(null);
  }

  public Map<EntityType, List<AnimalTraitWrapper<T>>> getEntityTypeTraitMap() {
    return entityTypeTraitMap;
  }
}
