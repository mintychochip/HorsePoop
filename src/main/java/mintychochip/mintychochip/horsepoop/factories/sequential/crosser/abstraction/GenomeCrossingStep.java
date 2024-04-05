package mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction;

import java.util.List;

import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.api.Fetcher;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.api.TraitCrosser;
import org.bukkit.entity.EntityType;

public interface GenomeCrossingStep<U extends TraitEnum> {

  List<BaseTrait<U>> crossTraits(List<BaseTrait<U>> father,
                                                   List<BaseTrait<U>> mother, EntityType entityType, List<BaseTrait<U>> baseTraits,
                                                   TraitConfig<U> config, Fetcher<U> fetcher, TraitCrosser<U> traitCrosser);

}
