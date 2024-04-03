package mintychochip.mintychochip.horsepoop.container.meta;

import java.util.List;
import mintychochip.mintychochip.horsepoop.container.Trait;

public class NumericPhenotypicMeta implements Phenotypic {
  private double max;
  private double min;
  @Override
  public <U extends Trait> List<U> getBlackList() {
    return null;
  }
}
