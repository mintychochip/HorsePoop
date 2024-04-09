package mintychochip.mintychochip.horsepoop.container;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import mintychochip.mintychochip.horsepoop.api.Comparer;
import mintychochip.mintychochip.horsepoop.api.Fetcher;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;

public class GenomeComparer<U extends TraitEnum> implements Comparer<U> {
    @Override
    public Set<BaseTrait<U>> uniqueTraits(List<BaseTrait<U>> father, List<BaseTrait<U>> mother) {
        Fetcher<U> fetcher = new ValueFetcher<>();

        Map<U, BaseTrait<U>> combinedAttributes = new HashMap<>();
        combinedAttributes.putAll(fetcher.getAttributes(father));
        combinedAttributes.putAll(fetcher.getAttributes(mother));

        return combinedAttributes.entrySet().stream().filter(entry -> {
                    U trait = entry.getKey();

                    BaseTrait<U> fatherTrait = fetcher.getTraitFromList(father, trait);
                    BaseTrait<U> motherTrait = fetcher.getTraitFromList(mother, trait);
                    return (fatherTrait == null && motherTrait != null) || (fatherTrait != null && motherTrait == null);
                })
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());
    }
}
