package mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction;

import java.util.List;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import org.bukkit.entity.EntityType;

public interface GenomeCrosser {
  List<Gene> crossGenomes(AnimalGenome father, AnimalGenome mother, EntityType entityType);

  boolean addCrossingStep(GenomeCrossingStep step);
}
