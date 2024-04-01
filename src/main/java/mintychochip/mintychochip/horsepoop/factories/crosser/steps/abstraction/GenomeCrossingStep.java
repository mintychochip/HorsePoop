package mintychochip.mintychochip.horsepoop.factories.crosser.steps.abstraction;

import java.util.List;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import org.bukkit.entity.EntityType;

public interface GenomeCrossingStep {

  List<Gene> crossGene(AnimalGenome father, AnimalGenome mother, EntityType entityType, List<Gene> previousResult);

}
