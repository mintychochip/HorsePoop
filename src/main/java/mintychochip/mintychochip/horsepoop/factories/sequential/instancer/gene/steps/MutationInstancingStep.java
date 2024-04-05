package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.steps;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.Fetcher;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.Generator;
import mintychochip.mintychochip.horsepoop.container.ValueFetcher;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.InstancingStep;
import org.bukkit.entity.EntityType;

public class MutationInstancingStep<U extends Trait> implements InstancingStep<U> {

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
    int mutations = 1;
    List<BaseTrait<U>> traits = new ArrayList<>();

    for(int i = 0; i < mutations; i++) {
      List<U> allTraits = config.getAllTraits(entityType);
      U randomTrait = getRandomTrait(allTraits);

      if(randomTrait != null && !this.traitIsInList(randomTrait,baseTraits,traits)) {
        BaseTrait<U> instance = generator.createInstance(randomTrait, entityType, config);
        if(instance != null) {
          traits.add(instance);
        }
      }
    }
    return traits;
  }
}