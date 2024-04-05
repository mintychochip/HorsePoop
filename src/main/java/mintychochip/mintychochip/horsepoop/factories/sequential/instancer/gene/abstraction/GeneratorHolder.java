package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction;

import java.util.List;

import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import org.bukkit.entity.EntityType;

public interface GeneratorHolder<U extends TraitEnum> {
  List<BaseTrait<U>> instanceTraits(EntityType entityType);

  boolean addStep(InstancingStep<U> step);
}
