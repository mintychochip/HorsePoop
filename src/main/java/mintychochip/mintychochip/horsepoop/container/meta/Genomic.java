package mintychochip.mintychochip.horsepoop.container.meta;

import java.util.List;
import mintychochip.mintychochip.horsepoop.config.Characteristic;
import mintychochip.mintychochip.horsepoop.container.GeneTrait;

public interface Genomic extends Characteristic {
  boolean getConserved();
  List<GeneTrait> getBlacklist();

}
