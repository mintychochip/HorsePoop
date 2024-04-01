package mintychochip.mintychochip.horsepoop.factories.crosser;

import java.util.ArrayList;
import java.util.List;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.factories.crosser.steps.abstraction.AbstractGenomeCrossingStep;
import org.bukkit.entity.EntityType;

public class NewGenomeCrosser implements GenomeCrosser {
  private final List<AbstractGenomeCrossingStep> genomeCrossingSteps;
  public NewGenomeCrosser(List<AbstractGenomeCrossingStep> genomeCrossingSteps) {
    this.genomeCrossingSteps = genomeCrossingSteps;
  }
  public NewGenomeCrosser() {
    genomeCrossingSteps = new ArrayList<>();
  }
  public List<Gene> crossGenomes(AnimalGenome father, AnimalGenome mother, EntityType entityType) {
    List<Gene> genes = new ArrayList<>();
    for (AbstractGenomeCrossingStep step : genomeCrossingSteps) {
      List<Gene> produced = step.crossGene(father, mother, entityType, genes);
      if (produced != null && !produced.isEmpty()) {
        genes.addAll(produced);
      }
    }
    return genes;
  }

  @Override
  public boolean addCrossingStep(AbstractGenomeCrossingStep abstractGenomeCrossingStep) {
    return genomeCrossingSteps.add(abstractGenomeCrossingStep);
  }
}
