package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction;

import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.CharacteristicTrait;
import mintychochip.mintychochip.horsepoop.container.GeneTrait;
import org.bukkit.entity.EntityType;

public interface GenomeGenerator {
  AnimalGenome instanceGenome(EntityType entityType);
  GeneratorHolder<CharacteristicTrait> getCharInstancer();
  GeneratorHolder<GeneTrait> getGeneGenerator();
}
