package mintychochip.mintychochip.horsepoop.container.TypeAdapters;

import java.util.List;
import mintychochip.mintychochip.horsepoop.api.markers.Gene;
import mintychochip.mintychochip.horsepoop.api.markers.Phenotypic;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;

public interface Genome {

  long getBirthTime();

  List<BaseTrait<Gene>> getGenes();

  List<BaseTrait<Phenotypic>> getPhenotypics();

}
