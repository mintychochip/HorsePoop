package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.steps;

import java.util.List;

import mintychochip.mintychochip.horsepoop.config.TraitMeta;
import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.TraitGenerator;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.InstancingStep;
import org.bukkit.entity.EntityType;

public class InstanceGeneStep<T extends TraitMeta> implements InstancingStep<T> {
  @Override
  public <U extends Trait> List<BaseTrait<T>> instanceTrait(EntityType entityType, List<BaseTrait<T>> baseTraits, TraitConfig<U, T> config, TraitGenerator<T> generator) {
    return config.getAllTraits(entityType).stream().map(trait -> generator.createInstance(trait, entityType, config)).toList();
  }
}
