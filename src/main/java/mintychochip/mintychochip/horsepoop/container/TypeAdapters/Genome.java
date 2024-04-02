package mintychochip.mintychochip.horsepoop.container.TypeAdapters;

import java.util.List;
import mintychochip.mintychochip.horsepoop.config.CharacteristicTraitMeta;
import mintychochip.mintychochip.horsepoop.config.GeneTraitMeta;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;

public interface Genome {

  long getBirthTime();

  List<BaseTrait<GeneTraitMeta>> getGenes();

  List<BaseTrait<CharacteristicTraitMeta>> getChars();

}
