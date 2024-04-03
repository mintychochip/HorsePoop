package mintychochip.mintychochip.horsepoop.factories.sequential.crosser.steps.genes;

import java.util.List;
import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.Fetcher;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.Crosser;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction.GenomeCrossingStep;
import org.bukkit.entity.EntityType;

public class MatchingNonConservedStep<T extends Characteristic> implements GenomeCrossingStep<T> {

  @Override
  public <U extends Trait> List<BaseTrait<T>> crossTraits(List<BaseTrait<T>> father,
      List<BaseTrait<T>> mother, EntityType entityType, List<BaseTrait<T>> baseTraits,
      TraitConfig<U, T> config, Fetcher<U, T> fetcher, Crosser<T> crosser) {
    return this.findNonConserved(father,mother,baseTraits,fetcher)
        .stream().map(trait -> crosser.crossTraits(trait,fetcher.getTraitFromList(mother,trait.getTrait()))).toList();
  }

  private <U extends Trait> List<BaseTrait<T>> findNonConserved(List<BaseTrait<T>> father,
      List<BaseTrait<T>> mother, List<BaseTrait<T>> baseTraits, Fetcher<U, T> fetcher) {
    return father.stream().filter(trait -> !fetcher.isTraitInList(baseTraits,fetcher.getTrait(trait.getTrait())))
        .filter(trait -> fetcher.getTraitFromList(mother,trait.getTrait()) != null).toList();
  }
}
