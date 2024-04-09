package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction;

import java.util.List;
import mintychochip.mintychochip.horsepoop.api.Generator;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import org.bukkit.entity.EntityType;

public interface InstancingStep<U extends TraitEnum> {
  List<BaseTrait<U>> instanceTrait(EntityType entityType, List<BaseTrait<U>> baseTraits, TraitConfig<U> config, Generator<U> generator);
}
