package mintychochip.mintychochip.horsepoop.api;

import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.metas.MetaType;
import org.bukkit.entity.EntityType;

public interface Generator<U extends TraitEnum>
{

    /**
     *
     * @param traitEnum Enum value that is a member of type U
     * @param entityType any EntityType EnumValue that is loaded in TraitConfig
     * @param traitConfig Configuration instance of type U
     * @return A new BaseTrait instanceof type U
     */
    BaseTrait<U> createInstance(U traitEnum, EntityType entityType, TraitConfig<U> traitConfig);

    void addMetaGenerationType(MetaType metaType, MetaValueGeneration<U> generation);
}
