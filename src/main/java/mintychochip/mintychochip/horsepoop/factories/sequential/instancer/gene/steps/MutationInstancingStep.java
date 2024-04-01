package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.steps;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mintychochip.mintychochip.horsepoop.config.configs.EntityConfig;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.GeneTrait;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.AbstractInstancingStep;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.InstancingStep;
import org.bukkit.entity.EntityType;

public class MutationInstancingStep extends AbstractInstancingStep {

  public MutationInstancingStep(
      GeneFactory geneFactory,
      Random random) {
    super(geneFactory, random);
  }
  @Override
  public List<BaseTrait> instanceTrait(EntityType entityType, List<BaseTrait> traits) {
    int mutations = 3; // Magic number until characteristics are added
    //mutations = (mutations == -1) ? 3 : mutations;
    if(traits.isEmpty()) {
      return null;
    }
    EntityConfig entityConfig = geneFactory.getConfigManager().getEntityConfig();
    List<GeneTrait> allGeneTraits = entityConfig.getAllGeneTraits(entityType);

    List<Gene> list = traits.stream().filter(trait -> trait instanceof Gene)
        .map(trait -> (Gene) trait).toList();

    List<BaseTrait> genes = new ArrayList<>();
    for (int i = 0; i < mutations; i++) {
      int j = random.nextInt(allGeneTraits.size());
      GeneTrait geneTrait = allGeneTraits.get(j);
      if (geneTrait != null && !geneFactory.getTraitFetcher()
          .geneTraitInList(list, geneTrait)) {
        if (geneFactory.createInstance(geneTrait, entityType) instanceof Gene gene) {
          genes.add(gene);
        }
      }
    }
    return genes;
  }
}
