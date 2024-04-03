package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction;

import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.entity.EntityType;

public interface GenomeGenerator {
  AnimalGenome instanceGenome(EntityType entityType);
  GeneratorHolder<CharacteristicTraitMeta> getCharInstancer();
  GeneratorHolder<GeneTraitMeta> getGeneGenerator();
}
