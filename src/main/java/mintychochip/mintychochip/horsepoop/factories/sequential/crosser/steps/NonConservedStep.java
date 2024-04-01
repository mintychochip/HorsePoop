package mintychochip.mintychochip.horsepoop.factories.sequential.crosser.steps;

import java.util.List;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.TraitFetcher;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;
import mintychochip.mintychochip.horsepoop.factories.Step;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction.GenomeCrossingStep;
import org.bukkit.entity.EntityType;

public class NonConservedStep extends Step implements GenomeCrossingStep {

  public NonConservedStep(GeneFactory geneFactory) {
    super(geneFactory);
  }

  @Override
  public List<Gene> crossGene(AnimalGenome father, AnimalGenome mother, EntityType entityType,
      List<Gene> setOfGenes) {
    List<Gene> fatherGenes = father.getGenes();
    List<Gene> motherGenes = mother.getGenes();
    TraitFetcher traitFetcher = geneFactory.getTraitFetcher();

    return fatherGenes.stream().filter(gene ->
            !traitFetcher.geneTraitInList(setOfGenes, traitFetcher.getGeneTrait(gene.getTrait()))
                && !gene.isConserved())
        .filter(gene -> traitFetcher.getGeneFromGeneList(motherGenes,
            traitFetcher.getGeneTrait(gene.getTrait())) != null && gene.isCrossable())
        .toList();
  }
}
