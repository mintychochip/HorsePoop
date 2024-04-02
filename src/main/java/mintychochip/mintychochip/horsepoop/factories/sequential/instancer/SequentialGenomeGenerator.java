package mintychochip.mintychochip.horsepoop.factories.sequential.instancer;

import java.util.List;
import mintychochip.mintychochip.horsepoop.config.CharacteristicTraitMeta;
import mintychochip.mintychochip.horsepoop.config.GeneTraitMeta;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.GenomeGenerator;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.TraitInstancer;
import org.bukkit.entity.EntityType;
public class SequentialGenomeGenerator implements
    GenomeGenerator {
  private final TraitInstancer<GeneTraitMeta> geneInstancer;
  private final TraitInstancer<CharacteristicTraitMeta> characteristicInstancer;
  public SequentialGenomeGenerator(TraitInstancer<GeneTraitMeta> geneInstancer, TraitInstancer<CharacteristicTraitMeta> characteristicInstancer) {
    this.geneInstancer = geneInstancer;
    this.characteristicInstancer = characteristicInstancer;
  }
  @Override
  public AnimalGenome instanceGenome(EntityType entityType) {
    List<BaseTrait<CharacteristicTraitMeta>> characteristics = characteristicInstancer.instanceTraits(entityType);
    List<BaseTrait<GeneTraitMeta>> gene = geneInstancer.instanceTraits(entityType);
    return AnimalGenome.createInstance(gene,characteristics, this);
  }

  @Override
  public TraitInstancer<CharacteristicTraitMeta> getCharInstancer() {
    return characteristicInstancer;
  }
  @Override
  public TraitInstancer<GeneTraitMeta> getGeneInstancer() {
    return geneInstancer;
  }
}
