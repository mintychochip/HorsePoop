package mintychochip.mintychochip.horsepoop.factories.sequential.crosser;

import java.util.List;
import mintychochip.mintychochip.horsepoop.api.Gene;
import mintychochip.mintychochip.horsepoop.api.Intrinsic;
import mintychochip.mintychochip.horsepoop.api.Phenotypic;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction.GenomeCrosser;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction.TraitCrosserHolder;
import org.bukkit.entity.EntityType;

public class SequentialGenomeCrosser implements GenomeCrosser {

  private final TraitCrosserHolder<Gene> geneCrosser;

  private final TraitCrosserHolder<Phenotypic> charCrosser;

  private final TraitCrosserHolder<Intrinsic> intrinsicCrosser;
  public SequentialGenomeCrosser(TraitCrosserHolder<Gene> geneCrosser, TraitCrosserHolder<Phenotypic> charCrosser, TraitCrosserHolder<Intrinsic> intrinsicCrosser) {
    this.geneCrosser = geneCrosser;
    this.charCrosser = charCrosser;
    this.intrinsicCrosser = intrinsicCrosser;
  }

  @Override
  public AnimalGenome crossGenome(AnimalGenome father, AnimalGenome mother, EntityType entityType) {
    List<BaseTrait<Phenotypic>> chars = charCrosser.crossTraits(
        father.getPhenotypics(), mother.getPhenotypics(), entityType);
    List<BaseTrait<Intrinsic>> intrinsics = intrinsicCrosser.crossTraits(father.getIntrinsics(),mother.getIntrinsics(),entityType);
    List<BaseTrait<Gene>> genes = geneCrosser.crossTraits(father.getGenes(),
        mother.getGenes(), entityType);
    return AnimalGenome.createInstance(genes,chars,intrinsics,this);
  }

}
