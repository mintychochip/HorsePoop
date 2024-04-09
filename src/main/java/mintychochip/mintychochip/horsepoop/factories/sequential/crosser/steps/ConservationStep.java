package mintychochip.mintychochip.horsepoop.factories.sequential.crosser.steps;

import java.util.List;
import java.util.stream.Collectors;
import mintychochip.mintychochip.horsepoop.api.Fetcher;
import mintychochip.mintychochip.horsepoop.api.TraitCrosser;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction.GenomeCrossingStep;
import org.bukkit.entity.EntityType;

public class ConservationStep<U extends TraitEnum> implements GenomeCrossingStep<U> {
    @Override
    public List<BaseTrait<U>> crossTraits(List<BaseTrait<U>> father, List<BaseTrait<U>> mother, EntityType entityType, List<BaseTrait<U>> baseTraits, TraitConfig<U> config, Fetcher<U> fetcher, TraitCrosser<U> traitCrosser) {
        return father.stream().filter(trait -> trait.getMeta().isConserved())
                .filter(trait -> fetcher.isTraitInList(mother, trait.getMeta().getTrait()))
                .map(trait -> traitCrosser.crossTraits(trait, fetcher.getTraitFromList(mother, trait.getMeta().getTrait())))
                .collect(Collectors.toList());
    }
}