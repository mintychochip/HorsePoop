package mintychochip.mintychochip.horsepoop.container;

import mintychochip.mintychochip.horsepoop.config.TraitMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GenomeComparer<T extends TraitMeta> implements Comparer<T> {
    @Override
    public <U extends Trait> Set<BaseTrait<T>> uniqueTraits(List<BaseTrait<T>> father, List<BaseTrait<T>> mother) {
        Fetcher<U, T> fetcher = new ValueFetcher<>();

        Map<U, BaseTrait<T>> combinedAttributes = new HashMap<>();
        combinedAttributes.putAll(fetcher.getAttributes(father));
        combinedAttributes.putAll(fetcher.getAttributes(mother));

        return combinedAttributes.entrySet().stream().filter(entry -> {
                    U trait = entry.getKey();
                    BaseTrait<T> fatherTrait = fetcher.getTraitFromList(father, trait);
                    BaseTrait<T> motherTrait = fetcher.getTraitFromList(mother, trait);
                    return (fatherTrait == null && motherTrait != null) || (fatherTrait != null && motherTrait == null);
                })
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());
    }
}
