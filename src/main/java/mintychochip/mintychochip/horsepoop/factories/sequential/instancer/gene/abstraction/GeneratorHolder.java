package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction;

import java.util.List;

import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import org.bukkit.entity.EntityType;

public interface GeneratorHolder<T extends Characteristic> {
  List<BaseTrait<T>> instanceTraits(EntityType entityType);

  boolean addStep(InstancingStep<T> step);
}
