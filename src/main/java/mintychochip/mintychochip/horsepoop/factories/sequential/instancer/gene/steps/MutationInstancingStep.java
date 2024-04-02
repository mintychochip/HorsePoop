package mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.steps;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mintychochip.mintychochip.horsepoop.config.TraitMeta;
import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.TraitFetcher;
import mintychochip.mintychochip.horsepoop.container.TraitGenerator;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.InstancingStep;
import org.bukkit.entity.EntityType;

public class MutationInstancingStep<T extends TraitMeta> implements InstancingStep<T> {

  private final Random random = new Random(System.currentTimeMillis());
  public <U extends Trait> List<BaseTrait<T>> instanceTrait(EntityType entityType, List<BaseTrait<T>> baseTraits, TraitConfig<U, T> config, TraitGenerator<T> generator) {
    int mutations = 1;
    List<BaseTrait<T>> traits = new ArrayList<>();

    for (int i = 0; i < mutations; i++) {
      List<U> allTraits = config.getAllTraits(entityType);
      U trait = getRandomTrait(allTraits);

      if (trait != null && !traitAlreadyExists(trait, baseTraits, traits)) {
        BaseTrait<T> instance = generator.createInstance(trait, entityType, config);
        if (instance != null) {
          traits.add(instance);
        }
      }
    }
    return traits;
  }

  private <U extends Trait> U getRandomTrait(List<U> allTraits) {
    int index = random.nextInt(allTraits.size());
    return allTraits.get(index);
  }

  private <U extends Trait> boolean traitAlreadyExists(U trait, List<BaseTrait<T>> baseTraits, List<BaseTrait<T>> traits) {
    TraitFetcher<T> fetcher = new TraitFetcher<>();
    return fetcher.isTraitInList(baseTraits, trait) || fetcher.isTraitInList(traits, trait);
  }
}