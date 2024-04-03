package mintychochip.mintychochip.horsepoop.factories.sequential.instancer;

import java.util.List;

import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.GenomeGenerator;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.GeneratorHolder;
import org.bukkit.entity.EntityType;
public class SequentialGenomeGenerator implements
    GenomeGenerator {
  private final GeneratorHolder<GeneTraitMeta> geneGenerator;
  private final GeneratorHolder<CharacteristicTraitMeta> charGenerator;
  public SequentialGenomeGenerator(
      GeneratorHolder<GeneTraitMeta> geneGenerator, GeneratorHolder<CharacteristicTraitMeta> charGenerator) {
    this.geneGenerator = geneGenerator;
    this.charGenerator = charGenerator;
  }
  @Override
  public AnimalGenome instanceGenome(EntityType entityType) {
    List<BaseTrait<CharacteristicTraitMeta>> characteristics = charGenerator.instanceTraits(entityType);
    List<BaseTrait<GeneTraitMeta>> gene = geneGenerator.instanceTraits(entityType);
    return AnimalGenome.createInstance(gene,characteristics, this);
  }

  @Override
  public GeneratorHolder<CharacteristicTraitMeta> getCharInstancer() {
    return charGenerator;
  }
  @Override
  public GeneratorHolder<GeneTraitMeta> getGeneGenerator() {
    return geneGenerator;
  }
}
