package mintychochip.mintychochip.horsepoop.factories;

import java.util.HashMap;
import java.util.Map;
import mintychochip.mintychochip.horsepoop.api.Generator;
import mintychochip.mintychochip.horsepoop.api.MetaValueGeneration;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import mintychochip.mintychochip.horsepoop.metas.MetaType;
import org.bukkit.entity.EntityType;

public class TraitGeneratorImpl<U extends TraitEnum> implements
        Generator<U> { //rename this class

    private final Map<MetaType, MetaValueGeneration<U>> typeGenerationMap = new HashMap<>();
    @Override
    public BaseTrait<U> createInstance(U traitEnum, EntityType entityType, TraitConfig<U> traitConfig) {
        Meta<U> meta = traitConfig.getMeta(traitEnum, entityType);

        String value = this.typeGenerationMap.get(traitEnum.getMetaType()).generateValue(meta);
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
