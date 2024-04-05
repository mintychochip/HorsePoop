package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction;

import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.api.Phenotypic;
import mintychochip.mintychochip.horsepoop.api.Gene;
import org.bukkit.entity.EntityType;

public interface GenomeGenerator {
  AnimalGenome instanceGenome(EntityType entityType);
  GeneratorHolder<Phenotypic> getCharInstancer();
  GeneratorHolder<Gene> getGeneGenerator();
}
