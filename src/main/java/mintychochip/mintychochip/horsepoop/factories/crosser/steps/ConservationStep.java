package mintychochip.mintychochip.horsepoop.factories.crosser.steps;

import java.util.List;
import java.util.stream.Collectors;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.TraitFetcher;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;
import mintychochip.mintychochip.horsepoop.factories.crosser.steps.abstraction.AbstractGenomeCrossingStep;
import org.bukkit.entity.EntityType;

public class ConservationStep extends AbstractGenomeCrossingStep {


  public ConservationStep(GeneFactory geneFactory) {
    super(geneFactory);
  }

  @Override
  public List<Gene> crossGene(AnimalGenome father, AnimalGenome mother, EntityType entityType, List<Gene> previousResult) {
    List<Gene> fatherGenes = father.getGenes();
    List<Gene> motherGenes = mother.getGenes();

    TraitFetcher traitFetcher = geneFactory.getTraitFetcher();

    return fatherGenes.stream()
        .filter(gene -> gene.isConserved() && traitFetcher
            .geneTraitInList(motherGenes, traitFetcher.getGeneTrait(gene.getTrait())))
        .map(gene -> geneFactory.crossAndCreateGene(gene, traitFetcher.getGeneFromGeneList(motherGenes,
            traitFetcher.getGeneTrait(gene.getTrait()))))
        .collect(Collectors.toList());
  }

}