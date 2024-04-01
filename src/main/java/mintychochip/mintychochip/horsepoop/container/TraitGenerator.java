package mintychochip.mintychochip.horsepoop.container;

import mintychochip.mintychochip.horsepoop.config.TraitMeta;
import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import org.bukkit.entity.EntityType;

public interface TraitGenerator<T extends TraitMeta>
{
    <U extends Trait> BaseTrait<T> createInstance(U trait, EntityType entityType, TraitConfig<U, T> traitConfig);
}
