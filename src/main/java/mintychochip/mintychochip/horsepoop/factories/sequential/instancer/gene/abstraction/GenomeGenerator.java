package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction;

import mintychochip.mintychochip.horsepoop.api.markers.Gene;
import mintychochip.mintychochip.horsepoop.api.markers.Intrinsic;
import mintychochip.mintychochip.horsepoop.api.markers.Phenotypic;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.entity.EntityType;

public interface GenomeGenerator {
  AnimalGenome instanceGenome(EntityType entityType);
  GeneratorHolder<Phenotypic> getCharGenerator();
  GeneratorHolder<Gene> getGeneGenerator();

  GeneratorHolder<Intrinsic> getIntrinsicGenerator();
}
