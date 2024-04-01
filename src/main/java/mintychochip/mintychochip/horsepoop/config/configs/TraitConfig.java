package mintychochip.mintychochip.horsepoop.config.configs;

import mintychochip.mintychochip.horsepoop.config.AnimalTraitWrapper;
import mintychochip.mintychochip.horsepoop.config.TraitMeta;
import mintychochip.mintychochip.horsepoop.container.GeneTrait;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.TraitType;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TraitConfig<T extends Trait, U extends TraitMeta> {

    private final List<T> traitEnums = new ArrayList<>();
    Map<EntityType, List<AnimalTraitWrapper<U>>> entityTypeTraitMap = new HashMap<>();

    public T getTraitFromWrapper(AnimalTraitWrapper<U> animalTraitWrapper) {
        T traitEnum = traitEnums.stream().filter(x -> x.getKey().equalsIgnoreCase(animalTraitWrapper.trait())).findFirst().orElse(null);
        if (traitEnum == null) {
            return null;
        }
        return traitEnum;
    }

    public List<T> getTraitEnums() {
        return traitEnums;
    }

    public List<T> getAllTraits(EntityType entityType) { //filters by trait type, so gene or characteristic
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
        return animalTraitWrappers.stream().filter(x -> x.trait().equals(trait.getKey())).findFirst().orElse(null);
    }

    public Map<EntityType, List<AnimalTraitWrapper<U>>> getEntityTypeTraitMap() {
        return entityTypeTraitMap;
    }
}
