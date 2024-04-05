package mintychochip.mintychochip.horsepoop.api;

import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.metas.MetaType;
import org.bukkit.entity.EntityType;

public interface Generator<U extends TraitEnum>
{
    BaseTrait<U> createInstance(U trait, EntityType entityType, TraitConfig<U> traitConfig);

    void addMetaGenerationType(MetaType metaType, MetaValueGeneration<U> generation);
}
