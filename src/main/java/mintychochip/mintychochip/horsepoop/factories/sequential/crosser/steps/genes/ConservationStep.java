package mintychochip.mintychochip.horsepoop.factories.sequential.crosser.steps.genes;

import java.util.List;
import java.util.function.Predicate;

import mintychochip.mintychochip.horsepoop.config.GeneTraitMeta;
import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.Fetcher;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.Crosser;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction.GenomeCrossingStep;
import org.bukkit.entity.EntityType;

public class ConservationStep<T extends Characteristic> implements GenomeCrossingStep<T> {

  Predicate<BaseTrait<T>> IS_GENE = baseTrait -> baseTrait.getMeta() instanceof GeneTraitMeta;
  @Override
  public <U extends Trait> List<BaseTrait<T>> crossTraits(List<BaseTrait<T>> father,
      List<BaseTrait<T>> mother, EntityType entityType, List<BaseTrait<T>> baseTraits,
      TraitConfig<U, T> config,
      Fetcher<U, T> fetcher, Crosser<T> crosser) {

    List<BaseTrait<T>> traits = father.stream().map(trait -> crosser.crossTraits(trait,
        fetcher.getTraitFromList(mother, trait.getTrait()))).toList();

  }
}