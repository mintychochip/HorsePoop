package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.steps;

import java.util.List;
import java.util.Random;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.AbstractInstancingStep;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.InstancingStep;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

public class InstanceGeneStep extends AbstractInstancingStep {

  public InstanceGeneStep(GeneFactory geneFactory, Random random) {
    super(geneFactory, random);
  }
  @Override
  public List<BaseTrait> instanceTrait(EntityType entityType, List<BaseTrait> traits) {
    return geneFactory.getConfigManager().getEntityConfig()
        .getAllGeneTraits(entityType).stream()
        .map(trait -> geneFactory.createInstance(trait, entityType)).toList();
  }
}
