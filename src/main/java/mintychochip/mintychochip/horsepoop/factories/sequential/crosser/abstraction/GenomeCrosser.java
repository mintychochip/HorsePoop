package mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction;

import java.util.List;

import mintychochip.mintychochip.horsepoop.config.TraitMeta;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import org.bukkit.entity.EntityType;

public interface GenomeCrosser<T extends TraitMeta> {
  List<BaseTrait<T>> crossGenomes(AnimalGenome father, AnimalGenome mother, EntityType entityType);

  boolean addCrossingStep(GenomeCrossingStep<T> step);
}
