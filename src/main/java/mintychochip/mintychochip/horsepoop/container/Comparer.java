package mintychochip.mintychochip.horsepoop.container;

import mintychochip.mintychochip.horsepoop.config.TraitMeta;

import java.util.List;
import java.util.Set;

public interface Comparer<T extends TraitMeta> { //compares sets of traits

    <U extends Trait> Set<BaseTrait<T>> uniqueTraits(List<BaseTrait<T>> father, List<BaseTrait<T>> mother);
}
