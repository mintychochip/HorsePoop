package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction;

import java.util.List;

import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.TraitGenerator;
import org.bukkit.entity.EntityType;

public interface InstancingStep<T extends Characteristic> {
  <U extends Trait> List<BaseTrait<T>> instanceTrait(EntityType entityType, List<BaseTrait<T>> baseTraits, TraitConfig<U, T> config, TraitGenerator<T> generator);
}
