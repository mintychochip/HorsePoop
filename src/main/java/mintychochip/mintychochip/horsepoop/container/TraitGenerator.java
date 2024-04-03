package mintychochip.mintychochip.horsepoop.container;

import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import org.bukkit.entity.EntityType;

public interface TraitGenerator<T extends Meta>
{
    <U extends Trait> BaseTrait<T> createInstance(U trait, EntityType entityType, TraitConfig<U, T> traitConfig);
}
