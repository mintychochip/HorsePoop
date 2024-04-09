package mintychochip.mintychochip.horsepoop.api;

import java.util.List;
import java.util.Set;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;

public interface Comparer<U extends TraitEnum> { //compares sets of traits

    Set<BaseTrait<U>> uniqueTraits(List<BaseTrait<U>> father, List<BaseTrait<U>> mother);
}
