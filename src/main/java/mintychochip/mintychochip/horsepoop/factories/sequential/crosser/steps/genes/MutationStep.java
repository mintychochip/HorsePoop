package mintychochip.mintychochip.horsepoop.factories.sequential.crosser.steps.genes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import mintychochip.mintychochip.horsepoop.config.GeneTraitMeta;
import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.Comparer;
import mintychochip.mintychochip.horsepoop.container.Fetcher;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.Crosser;
import mintychochip.mintychochip.horsepoop.container.TraitGenerator;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction.GenomeCrossingStep;
import org.bukkit.entity.EntityType;

public class MutationStep<T extends Characteristic> implements GenomeCrossingStep<T> {

  private final double recombinanceChance;

  private final int maxCount;

  private final Comparer<T> comparer;

  private final TraitGenerator<T> generator;
  Predicate<BaseTrait<T>> CROSSABLE = baseTrait -> baseTrait.getMeta() instanceof GeneTraitMeta gtm && gtm.crossable();

  public MutationStep(double recombinanceChance, int maxCount, Comparer<T> comparer, TraitGenerator<T> generator) {
    this.recombinanceChance = recombinanceChance;
    this.maxCount = maxCount;
    this.comparer = comparer;
    this.generator = generator;
  }
  @Override
  public <U extends Trait> List<BaseTrait<T>> crossTraits(List<BaseTrait<T>> father,
      List<BaseTrait<T>> mother, EntityType entityType, List<BaseTrait<T>> baseTraits,
      TraitConfig<U, T> config, Fetcher<U, T> fetcher, Crosser<T> crosser) {

    Random random = new Random();
    List<BaseTrait<T>> traits = new ArrayList<>();

    int count = 0;
    for (BaseTrait<T> trait : comparer.uniqueTraits(father, mother)) {
      if(CROSSABLE.test(trait) && random.nextDouble() <= recombinanceChance && count <= maxCount) {
        BaseTrait<T> instance = generator.createInstance(fetcher.getTrait(trait.getTrait()),
            entityType, config);
        if(instance != null) {
          BaseTrait<T> child = crosser.crossTraits(trait, instance);
          if(child != null) {
            traits.add(child);
            count++;
          }
        }
      }
    }
    return traits;
  }
}