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
import mintychochip.genesis.config.abstraction.GenericConfig;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.TypeAdapters.TraitMetaAdapter;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

public class TraitConfig<U extends TraitEnum> {

  private final List<U> traitEnums = new ArrayList<>();

  private final Map<String, U> mapKeyTraitEnum = new HashMap<>();
  private Class<U> tClass;

  private final Map<EntityType, List<Meta<U>>> entityTypeMetaMap = new HashMap<>();
  public TraitConfig(Class<U> tClass) {
    this.tClass = tClass;
  }

  public <Y extends U> void loadEnums(Class<Y> enumClass, Y[] values) {
    if (tClass.isAssignableFrom(enumClass) && enumClass.isEnum()) {
      Arrays.stream(values).forEach(x -> mapKeyTraitEnum.put(x.getKey(), x));
      Collections.addAll(traitEnums, values);
    }
  }

  public U getTraitFromString(String key) {
    return mapKeyTraitEnum.get(key);
  }
  public void loadTraitConfigs(GenericConfig config) {
    Type type = new TypeToken<Meta<U>>() {
    }.getType();
    Gson gson = new GsonBuilder().registerTypeAdapter(new TypeToken<Meta<U>>() {
        }.getType(),
        new TraitMetaAdapter<>(this)).create();
    for (String key : config.getKeys(false)) { //entityType keys
      List<Meta<U>> metas = new ArrayList<>();
      for (String s : config.getStringList(key)) {
        Meta<U> meta = gson.fromJson(s, type);
        if (meta != null) {
          metas.add(meta);
        }
      }
      entityTypeMetaMap.put(Enum.valueOf(EntityType.class, key.toUpperCase()), metas);
    }
  }

  public List<U> getTraitEnums() {
    return traitEnums;
  }

  public List<U> getAllEntityTraits(EntityType entityType) {
    if(!entityTypeMetaMap.containsKey(entityType)) {
      return null;
    }
    return entityTypeMetaMap.get(entityType).stream().map(Meta::getTrait).toList();
  }
  public Meta<U> getMeta(U trait, EntityType entityType) {
    List<Meta<U>> metas = entityTypeMetaMap.get(entityType);
    return metas.stream().filter(x -> x.getTrait() == trait).findFirst().orElse(null);
  }

  public Map<EntityType, List<Meta<U>>> getEntityTypeMetaMap() {
    return entityTypeMetaMap;
  }
}
