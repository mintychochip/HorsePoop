package mintychochip.mintychochip.horsepoop.container.meta;

import java.util.List;
import mintychochip.mintychochip.horsepoop.container.Trait;

public class PolygenicMeta implements Genomic {
  private List<String> required;
  @Override
  public boolean getConserved() {
    return false;
  }
  @Override
  public <U extends Trait> List<U> getBlackList() {
    return null;
  }

}
