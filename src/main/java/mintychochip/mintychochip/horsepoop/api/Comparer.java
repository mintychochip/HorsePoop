package mintychochip.mintychochip.horsepoop.api;

import mintychochip.mintychochip.horsepoop.container.BaseTrait;

import java.util.List;
import java.util.Set;

public interface Comparer<U extends TraitEnum> { //compares sets of traits

    Set<BaseTrait<U>> uniqueTraits(List<BaseTrait<U>> father, List<BaseTrait<U>> mother);
}
