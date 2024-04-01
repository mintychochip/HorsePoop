package mintychochip.mintychochip.horsepoop.factories.sequential.instancer;

import java.util.List;

import mintychochip.mintychochip.horsepoop.config.CharacteristicTraitMeta;
import mintychochip.mintychochip.horsepoop.config.GeneTraitMeta;
import mintychochip.mintychochip.horsepoop.config.TraitMeta;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.GenomeInstancer;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.TraitInstancer;
import org.bukkit.entity.EntityType;
public class SequentialGenomeInstancer implements
    GenomeInstancer {
  private final TraitInstancer<GeneTraitMeta> geneInstancer;
  private final TraitInstancer<CharacteristicTraitMeta> characteristicInstancer;
  public SequentialGenomeInstancer(TraitInstancer<GeneTraitMeta> geneInstancer, TraitInstancer<CharacteristicTraitMeta> characteristicInstancer) {
    this.geneInstancer = geneInstancer;
    this.characteristicInstancer = characteristicInstancer;
  }
  @Override
  public AnimalGenome instanceGenome(EntityType entityType) {
//    List<Characteristic> characteristics = characteristicInstancer.instanceTraits(entityType).stream()
//        .filter(trait -> trait.getMeta())
//        .map(trait -> (Characteristic) trait).toList();
//    List<Gene> gene = geneInstancer.instanceTraits(entityType).stream()
//        .filter(trait -> trait instanceof Gene)
//        .map(trait -> (Gene) trait).toList();
    return AnimalGenome.createInstance(characteristics,gene,this);
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
