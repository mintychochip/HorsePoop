package mintychochip.mintychochip.horsepoop.factories.sequential.crosser.steps;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mintychochip.mintychochip.horsepoop.config.configs.SettingsConfig;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.GenomeComparer;
import mintychochip.mintychochip.horsepoop.container.TraitFetcher;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;
import mintychochip.mintychochip.horsepoop.factories.Step;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction.GenomeCrossingStep;
import org.bukkit.entity.EntityType;

public class MutationStep extends Step implements GenomeCrossingStep {
  private final GenomeComparer genomeComparer;
  public MutationStep(GeneFactory geneFactory, GenomeComparer genomeComparer) {
    super(geneFactory);
    this.genomeComparer = genomeComparer;
  }
  @Override
  public List<Gene> crossGene(AnimalGenome father, AnimalGenome mother, EntityType entityType,
      List<Gene> setOfGenes) {
    SettingsConfig settingsConfig = geneFactory.getConfigManager().getSettingsConfig();
    double recombinanceChance = Math.max(settingsConfig.getRecombinanceChance(), 0.5);
    int maxCount = Math.max(settingsConfig.getRecombinanceMaxCount(), 1);
    Random random = new Random();
    TraitFetcher traitFetcher = geneFactory.getTraitFetcher();
    List<Gene> genes = new ArrayList<>();
    int count = 0; //counts recombinance
    for (Gene gene : genomeComparer.uniqueGenes(father, mother)) {
      if (gene.isCrossable() && random.nextDouble() <= recombinanceChance && count <= maxCount) {
        BaseTrait instance = geneFactory.createInstance(traitFetcher.getGeneTrait(gene.getTrait()),
            entityType);
        if (instance instanceof Gene gene2) {
          Gene generated = geneFactory.crossAndCreateGene(gene, gene2);
          if (generated != null) {
            genes.add(generated);
            count++;
          }
        }
      }
    }
    return genes;
  }
}