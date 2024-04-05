package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction;

import java.util.List;

import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.Generator;
import org.bukkit.entity.EntityType;

public interface InstancingStep<U extends Trait> {
  List<BaseTrait<U>> instanceTrait(EntityType entityType, List<BaseTrait<U>> baseTraits, TraitConfig<U> config, Generator<U> generator);
}
