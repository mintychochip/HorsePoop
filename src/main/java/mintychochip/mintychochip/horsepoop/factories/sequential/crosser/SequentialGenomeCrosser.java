package mintychochip.mintychochip.horsepoop.factories.sequential.crosser;

import java.util.List;

import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.api.Phenotypic;
import mintychochip.mintychochip.horsepoop.api.Gene;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction.GenomeCrosser;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction.TraitCrosserHolder;
import org.bukkit.entity.EntityType;

public class SequentialGenomeCrosser implements GenomeCrosser {

  private final TraitCrosserHolder<Gene> geneCrosser;

  private final TraitCrosserHolder<Phenotypic> charCrosser;
  public SequentialGenomeCrosser(TraitCrosserHolder<Gene> geneCrosser, TraitCrosserHolder<Phenotypic> charCrosser) {
    this.geneCrosser = geneCrosser;
    this.charCrosser = charCrosser;
  }

  @Override
  public AnimalGenome crossGenome(AnimalGenome father, AnimalGenome mother, EntityType entityType) {
    List<BaseTrait<Phenotypic>> chars = charCrosser.crossTraits(
        father.getChars(), mother.getChars(), entityType);
    List<BaseTrait<Gene>> genes = geneCrosser.crossTraits(father.getGenes(),
        mother.getGenes(), entityType);
    return AnimalGenome.createInstance(genes,chars,this);
  }

}
