package mintychochip.mintychochip.horsepoop.factories.sequential.instancer;

import java.util.List;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Characteristic;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.GenomeInstancer;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.TraitInstancer;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
public class SequentialGenomeInstancer implements
    GenomeInstancer {
  private final TraitInstancer geneInstancer;
  private final TraitInstancer characteristicInstancer;
  public SequentialGenomeInstancer(TraitInstancer geneInstancer, TraitInstancer characteristicInstancer) {
    this.geneInstancer = geneInstancer;
    this.characteristicInstancer = characteristicInstancer;
  }
  @Override
  public AnimalGenome instanceGenome(EntityType entityType) {
    List<Characteristic> characteristics = characteristicInstancer.instanceTraits(entityType).stream()
        .filter(trait -> trait instanceof Characteristic)
        .map(trait -> (Characteristic) trait).toList();
    List<Gene> gene = geneInstancer.instanceTraits(entityType).stream()
        .filter(trait -> trait instanceof Gene)
        .map(trait -> (Gene) trait).toList();
    return AnimalGenome.createInstance(characteristics,gene,this);
  }

  @Override
  public TraitInstancer getCharInstancer() {
    return characteristicInstancer;
  }
  @Override
  public TraitInstancer getGeneInstancer() {
    return geneInstancer;
  }
}
