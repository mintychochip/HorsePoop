package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.steps;

import java.util.List;

import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.Generator;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.InstancingStep;
import mintychochip.mintychochip.horsepoop.metas.Conserved;
import org.bukkit.entity.EntityType;

public class InstanceConserved<U extends Trait> implements InstancingStep<U> {
  @Override
  public List<BaseTrait<U>> instanceTrait(EntityType entityType, List<BaseTrait<U>> baseTraits,
      TraitConfig<U> config, Generator<U> generator) {
    return config.getAllEntityTraits(entityType).stream()
        .map(trait -> generator.createInstance(trait,entityType,config))
        .filter(trait -> trait.getMeta() instanceof Conserved c && c.isConserved())
        .toList();
  }
}