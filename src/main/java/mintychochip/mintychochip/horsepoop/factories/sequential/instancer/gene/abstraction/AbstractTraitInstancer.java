package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction;

import java.util.List;

public abstract class AbstractTraitInstancer implements TraitInstancer {

  protected List<InstancingStep> steps;

  protected AbstractTraitInstancer(List<InstancingStep> steps) {
    this.steps = steps;
  }
}
