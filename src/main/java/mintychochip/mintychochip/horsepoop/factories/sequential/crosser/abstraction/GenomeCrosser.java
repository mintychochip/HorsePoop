package mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction;

import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.entity.EntityType;

public interface GenomeCrosser {

  AnimalGenome crossGenome(AnimalGenome father, AnimalGenome mother, EntityType entityType);
}
