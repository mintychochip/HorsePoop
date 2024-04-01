package mintychochip.mintychochip.horsepoop.factories.crosser.steps;

import java.util.List;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.TraitFetcher;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;
import mintychochip.mintychochip.horsepoop.factories.crosser.steps.abstraction.AbstractGenomeCrossingStep;
import org.bukkit.entity.EntityType;

public class NonConservedStep extends AbstractGenomeCrossingStep {

  public NonConservedStep(GeneFactory geneFactory) {
    super(geneFactory);
  }

  @Override
  public List<Gene> crossGene(AnimalGenome father, AnimalGenome mother, EntityType entityType,
      List<Gene> previousResult) {
    List<Gene> fatherGenes = father.getGenes();
    List<Gene> motherGenes = mother.getGenes();
    TraitFetcher traitFetcher = geneFactory.getTraitFetcher();

    return fatherGenes.stream().filter(gene ->
            !traitFetcher.geneTraitInList(previousResult, traitFetcher.getGeneTrait(gene.getTrait()))
                && !gene.isConserved())
        .filter(gene -> traitFetcher.getGeneFromGeneList(motherGenes,
            traitFetcher.getGeneTrait(gene.getTrait())) != null && gene.isCrossable())
        .toList();
  }
}
