package mintychochip.mintychochip.horsepoop.factories.crosser.steps.abstraction;

import java.util.List;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;
import org.bukkit.entity.EntityType;

public abstract class AbstractGenomeCrossingStep implements GenomeCrossingStep {


  protected GeneFactory geneFactory;

  protected AbstractGenomeCrossingStep(GeneFactory geneFactory) {
    this.geneFactory = geneFactory;
  }
}

