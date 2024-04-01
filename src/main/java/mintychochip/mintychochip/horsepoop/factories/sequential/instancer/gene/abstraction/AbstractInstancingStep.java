package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction;

import java.util.Random;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;
import mintychochip.mintychochip.horsepoop.factories.Step;

public abstract class AbstractInstancingStep extends Step implements InstancingStep {

  protected final Random random;
  protected AbstractInstancingStep(
      GeneFactory geneFactory, Random random) {
    super(geneFactory);
    this.random = random;
  }
}
