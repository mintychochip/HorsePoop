package mintychochip.mintychochip.horsepoop.factories.sequential.crosser.steps;

import java.util.List;
import java.util.stream.Collectors;

import mintychochip.mintychochip.horsepoop.config.GeneTraitMeta;
import mintychochip.mintychochip.horsepoop.config.TraitMeta;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.TraitFetcher;
import mintychochip.mintychochip.horsepoop.container.TraitGenerator;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction.GenomeCrossingStep;
import org.bukkit.entity.EntityType;

public class ConservationStep<T extends TraitMeta> implements GenomeCrossingStep<T> {


  public ConservationStep(GeneFactory geneFactory) {
    super(geneFactory);
  }

  @Override
  public List<Gene> crossGene(AnimalGenome father, AnimalGenome mother, EntityType entityType, List<Gene> setOfGenes) {
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

  @Override
  public List<BaseTrait<T>> crossTraits(AnimalGenome father, AnimalGenome mother, EntityType entityType, List<BaseTrait<T>> baseTraits, TraitGenerator<T> generator, TraitFetcher<T> fetcher) {
    List<BaseTrait<GeneTraitMeta>> genes = father.getGenes();
    List<BaseTrait<GeneTraitMeta>> genes1 = mother.getGenes();

    return genes.stream()
            .filter(trait -> fetcher.traitInList(genes1,fetcher.getTrait(trait.getTrait())))
    return null;
  }
}