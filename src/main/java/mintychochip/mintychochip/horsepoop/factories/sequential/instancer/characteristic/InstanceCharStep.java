package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.characteristic;

import java.util.List;
import java.util.Random;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.factories.GeneFactory;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.AbstractInstancingStep;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

public class InstanceCharStep extends AbstractInstancingStep {
  public InstanceCharStep(
      GeneFactory geneFactory,
      Random random) {
    super(geneFactory, random);
  }
  @Override
  public List<BaseTrait> instanceTrait(EntityType entityType, List<BaseTrait> traits) {
    return geneFactory.getConfigManager().getEntityConfig().getAllCharacteristicTraits(entityType)
        .stream().map(trait -> geneFactory.createInstance(trait,entityType)).toList();
  }
}
