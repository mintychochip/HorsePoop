package mintychochip.mintychochip.horsepoop.factories.sequential.instancer;

import java.util.List;

import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.CharacteristicTrait;
import mintychochip.mintychochip.horsepoop.container.GeneTrait;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.GenomeGenerator;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.GeneratorHolder;
import org.bukkit.entity.EntityType;
public class SequentialGenomeGenerator implements
    GenomeGenerator {
  private final GeneratorHolder<GeneTrait> geneGenerator;
  private final GeneratorHolder<CharacteristicTrait> charGenerator;
  public SequentialGenomeGenerator(
      GeneratorHolder<GeneTrait> geneGenerator, GeneratorHolder<CharacteristicTrait> charGenerator) {
    this.geneGenerator = geneGenerator;
    this.charGenerator = charGenerator;
  }
  @Override
  public AnimalGenome instanceGenome(EntityType entityType) {
    List<BaseTrait<CharacteristicTrait>> characteristics = charGenerator.instanceTraits(entityType);
    List<BaseTrait<GeneTrait>> gene = geneGenerator.instanceTraits(entityType);
    return AnimalGenome.createInstance(gene,characteristics, this);
  }
  @Override
  public GeneratorHolder<CharacteristicTrait> getCharInstancer() {
    return charGenerator;
  }
  @Override
  public GeneratorHolder<GeneTrait> getGeneGenerator() {
    return geneGenerator;
  }
}
