package mintychochip.mintychochip.horsepoop.util;

import java.util.List;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.Trait;

public class GeneUtil {

  public static boolean isTraitInList(List<Gene> genes, Trait trait) {
    return genes.stream().anyMatch(x -> x.getTrait() == trait);
  }

}
