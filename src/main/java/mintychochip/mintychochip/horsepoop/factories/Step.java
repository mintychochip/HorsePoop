package mintychochip.mintychochip.horsepoop.factories;

public abstract class Step {
  protected GeneFactory geneFactory;

  protected Step(GeneFactory geneFactory) {
    this.geneFactory = geneFactory;
  }
}

