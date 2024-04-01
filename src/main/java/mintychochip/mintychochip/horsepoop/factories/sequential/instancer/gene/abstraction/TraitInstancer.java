package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction;

import java.util.List;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import org.bukkit.entity.EntityType;

public interface TraitInstancer {

  boolean addInstancingStep(InstancingStep step);

  List<BaseTrait> instanceTraits(EntityType entityType);
}
