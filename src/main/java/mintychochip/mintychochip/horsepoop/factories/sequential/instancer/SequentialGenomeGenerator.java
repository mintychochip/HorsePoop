package mintychochip.mintychochip.horsepoop.factories.sequential.instancer;

import java.util.List;

import mintychochip.mintychochip.horsepoop.api.Intrinsic;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.api.Phenotypic;
import mintychochip.mintychochip.horsepoop.api.Gene;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.GenomeGenerator;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.GeneratorHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

public class SequentialGenomeGenerator implements
    GenomeGenerator {

  private final GeneratorHolder<Gene> geneGenerator;
  private final GeneratorHolder<Phenotypic> charGenerator;

  private final GeneratorHolder<Intrinsic> intrinsicGenerator;

  public SequentialGenomeGenerator(
      GeneratorHolder<Gene> geneGenerator, GeneratorHolder<Phenotypic> charGenerator,
      GeneratorHolder<Intrinsic> intrinsicGenerator) {
    this.geneGenerator = geneGenerator;
    this.charGenerator = charGenerator;
    this.intrinsicGenerator = intrinsicGenerator;
  }

  @Override
  public AnimalGenome instanceGenome(EntityType entityType) {
    List<BaseTrait<Phenotypic>> characteristics = charGenerator.instanceTraits(entityType);
    List<BaseTrait<Intrinsic>> intrinsics = intrinsicGenerator.instanceTraits(entityType);
    List<BaseTrait<Gene>> gene = geneGenerator.instanceTraits(entityType);
    return AnimalGenome.createInstance(gene, characteristics, intrinsics, this);
  }

  @Override
  public GeneratorHolder<Phenotypic> getCharGenerator() {
    return charGenerator;
  }

  @Override
  public GeneratorHolder<Gene> getGeneGenerator() {
    return geneGenerator;
  }

  @Override
  public GeneratorHolder<Intrinsic> getIntrinsicGenerator() {
    return intrinsicGenerator;
  }
}
