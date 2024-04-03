package mintychochip.mintychochip.horsepoop.container;

import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import org.bukkit.entity.EntityType;

public interface TraitGenerator<T extends Characteristic>
{
    <U extends Trait> BaseTrait<T> createInstance(U trait, EntityType entityType, TraitConfig<U, T> traitConfig);
}
