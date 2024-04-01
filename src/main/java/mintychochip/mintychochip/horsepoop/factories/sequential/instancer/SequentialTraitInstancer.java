package mintychochip.mintychochip.horsepoop.factories.sequential.instancer;

import java.util.ArrayList;
import java.util.List;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.AbstractTraitInstancer;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.InstancingStep;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
public class SequentialTraitInstancer extends AbstractTraitInstancer {
  public SequentialTraitInstancer(List<InstancingStep> steps) {
    super(steps);
  }
  @Override
  public boolean addInstancingStep(InstancingStep step) {
    return steps.add(step);
  }
  @Override
  public List<BaseTrait> instanceTraits(EntityType entityType) {
    List<BaseTrait> traits = new ArrayList<>();
    for (InstancingStep step : steps) {
      List<BaseTrait> newTraits = step.instanceTrait(entityType, traits);
      if(newTraits != null && !newTraits.isEmpty()) {
        traits.addAll(newTraits);
      }
    }
    return traits;
  }

}
