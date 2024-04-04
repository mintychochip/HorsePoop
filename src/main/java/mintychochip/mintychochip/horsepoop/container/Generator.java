package mintychochip.mintychochip.horsepoop.container;

import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import mintychochip.mintychochip.horsepoop.metas.MetaType;
import org.bukkit.entity.EntityType;

public interface Generator<U extends Trait>
{
    BaseTrait<U> createInstance(U trait, EntityType entityType, TraitConfig<U> traitConfig);

    void addMetaGenerationType(MetaType metaType, MetaValueGeneration<U> generation);
}
