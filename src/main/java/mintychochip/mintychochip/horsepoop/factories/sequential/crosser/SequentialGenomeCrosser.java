package mintychochip.mintychochip.horsepoop.factories.sequential.crosser;

import java.util.ArrayList;
import java.util.List;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction.GenomeCrosser;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction.GenomeCrossingStep;
import org.bukkit.entity.EntityType;

public class SequentialGenomeCrosser implements GenomeCrosser {
  private final List<GenomeCrossingStep> steps;
  public SequentialGenomeCrosser(List<GenomeCrossingStep> steps) {
    this.steps = steps;
  }
  public List<Gene> crossGenomes(AnimalGenome father, AnimalGenome mother, EntityType entityType) {
    List<Gene> genes = new ArrayList<>();
    for (GenomeCrossingStep step : steps) {
      List<Gene> produced = step.crossGene(father, mother, entityType, genes);
      if (produced != null && !produced.isEmpty()) {
        genes.addAll(produced);
      }
    }
    return genes;
  }

  @Override
  public boolean addCrossingStep(GenomeCrossingStep step) {
    return steps.add(step);
  }
}
