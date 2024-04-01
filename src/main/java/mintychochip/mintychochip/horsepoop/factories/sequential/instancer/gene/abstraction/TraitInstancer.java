package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction;

import java.util.List;

import mintychochip.mintychochip.horsepoop.config.TraitMeta;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import org.bukkit.entity.EntityType;

public interface TraitInstancer<T extends TraitMeta> {
  List<BaseTrait<T>> instanceTraits(EntityType entityType);

  boolean addStep(InstancingStep<T> step);
}
