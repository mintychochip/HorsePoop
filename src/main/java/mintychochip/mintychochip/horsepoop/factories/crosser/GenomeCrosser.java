package mintychochip.mintychochip.horsepoop.factories.crosser;

import java.util.List;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.factories.crosser.steps.abstraction.AbstractGenomeCrossingStep;
import org.bukkit.entity.EntityType;

public interface GenomeCrosser {
  List<Gene> crossGenomes(AnimalGenome father, AnimalGenome mother, EntityType entityType);

  boolean addCrossingStep(AbstractGenomeCrossingStep abstractGenomeCrossingStep);
}
