package mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction;

import java.util.List;

import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.Fetcher;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.Crosser;
import org.bukkit.entity.EntityType;

public interface GenomeCrossingStep<T extends Characteristic> {

  <U extends Trait> List<BaseTrait<T>> crossTraits(List<BaseTrait<T>> father,
      List<BaseTrait<T>> mother, EntityType entityType, List<BaseTrait<T>> baseTraits,
      TraitConfig<U, T> config, Fetcher<U, T> fetcher, Crosser<T> crosser);

}
