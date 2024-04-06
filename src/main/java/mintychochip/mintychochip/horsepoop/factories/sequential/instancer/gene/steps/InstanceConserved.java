package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.steps;

import java.util.List;
import java.util.stream.Collectors;

import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.api.Generator;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.InstancingStep;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

public class InstanceConserved<U extends TraitEnum> implements InstancingStep<U> {
  @Override
  public List<BaseTrait<U>> instanceTrait(EntityType entityType, List<BaseTrait<U>> baseTraits,
      TraitConfig<U> config, Generator<U> generator) {
    List<U> allEntityTraits = config.getAllEntityTraits(entityType);
    if(allEntityTraits == null) {
      return null;
    }
    return allEntityTraits
        .stream() //all traits for trait type U
        .filter(trait -> config.getMeta(trait, entityType)
            .isConserved()) // filter if it is a member of conserved, then continue and instance if it is
        .map(trait -> generator.createInstance(trait, entityType, config)) //
        .collect(Collectors.toList());
  }
}