package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.steps;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mintychochip.mintychochip.horsepoop.api.Fetcher;
import mintychochip.mintychochip.horsepoop.api.Generator;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.ValueFetcher;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.InstancingStep;
import org.bukkit.entity.EntityType;

public class MutationInstancingStep<U extends TraitEnum> implements InstancingStep<U> {

  private final Random random = new Random(System.currentTimeMillis());

  private U getRandomTrait(List<U> allTraits) {
    int index = random.nextInt(allTraits.size());
    return allTraits.get(index);
  }

  private boolean traitIsInList(U trait, List<BaseTrait<U>> baseTraits,
      List<BaseTrait<U>> traits) {
    Fetcher<U> fetcher = new ValueFetcher<>();
    return fetcher.isTraitInList(baseTraits, trait) || fetcher.isTraitInList(traits, trait);
  }

  @Override
  public List<BaseTrait<U>> instanceTrait(EntityType entityType, List<BaseTrait<U>> baseTraits,
      TraitConfig<U> config, Generator<U> generator) {
    List<U> allTraits = config.getAllEntityTraits(entityType);
    List<BaseTrait<U>> traits = new ArrayList<>();

    if (!allTraits.isEmpty()) {
      int mutations = 3;

      for (int i = 0; i < mutations; i++) {
        U randomTrait = this.getRandomTrait(allTraits);

        if (randomTrait != null && !this.traitIsInList(randomTrait, baseTraits, traits)) {
          BaseTrait<U> instance = generator.createInstance(randomTrait, entityType, config);
          if (instance != null) {
            traits.add(instance);
          }
        }
      }
    }
    return traits;
  }
}