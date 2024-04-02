package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.steps;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import mintychochip.mintychochip.horsepoop.config.GeneTraitMeta;
import mintychochip.mintychochip.horsepoop.config.TraitMeta;
import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.TraitGenerator;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.InstancingStep;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

public class InstanceGeneStep<T extends TraitMeta> implements InstancingStep<T> {

  @Override
  public <U extends Trait> List<BaseTrait<T>> instanceTrait(EntityType entityType,
      List<BaseTrait<T>> baseTraits, TraitConfig<U, T> config, TraitGenerator<T> generator) {
    List<U> allTraits = config.getAllTraits(entityType);

    List<BaseTrait<T>> list = allTraits.stream()
        .map(trait -> generator.createInstance(trait, entityType, config)).toList();
    if (allTraits.stream().anyMatch(
        trait -> config.getMeta(trait, entityType) instanceof GeneTraitMeta gtm && gtm.conserved())) {
      return list.stream().filter(baseTrait -> baseTrait.getMeta() instanceof GeneTraitMeta gtm && gtm.conserved()).collect(
          Collectors.toList());
    } else {
      return list;
    }
  }
}