package mintychochip.mintychochip.horsepoop.factories;

import java.util.*;

import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.*;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import mintychochip.mintychochip.horsepoop.metas.MetaType;
import org.bukkit.entity.EntityType;

public class TraitGeneratorImpl<U extends Trait> implements
        Generator<U> { //rename this class

    private Map<MetaType, MetaValueGeneration<U>> typeGenerationMap = new HashMap<>();
    @Override
    public BaseTrait<U> createInstance(U trait, EntityType entityType, TraitConfig<U> traitConfig) {
        Meta<U> meta = traitConfig.getMeta(trait, entityType);

        String value = this.typeGenerationMap.get(trait.getMetaType()).generateValue(meta);
        if (value == null) {
            return null;
        }
        return BaseTrait.create(value,meta,this);
    }

    @Override
    public void addMetaGenerationType(MetaType metaType, MetaValueGeneration<U> generation) {
        typeGenerationMap.put(metaType, generation);
    }
}
