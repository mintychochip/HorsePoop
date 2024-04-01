package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction;

import java.util.List;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import org.bukkit.entity.EntityType;

public interface InstancingStep {
  List<BaseTrait> instanceTrait(EntityType entityType, List<BaseTrait> traits);
}
