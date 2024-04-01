package mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction;

import java.util.List;

import mintychochip.mintychochip.horsepoop.config.TraitMeta;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import org.bukkit.entity.EntityType;

public interface GenomeCrossingStep<T extends TraitMeta> {

  List<BaseTrait<T>> crossTraits(AnimalGenome father, AnimalGenome mother, EntityType entityType, List<BaseTrait<T>> baseTraits);

}
