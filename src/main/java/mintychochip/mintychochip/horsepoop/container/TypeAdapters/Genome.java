package mintychochip.mintychochip.horsepoop.container.TypeAdapters;

import java.util.List;

import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.api.Phenotypic;
import mintychochip.mintychochip.horsepoop.api.Gene;

public interface Genome {

  long getBirthTime();

  List<BaseTrait<Gene>> getGenes();

  List<BaseTrait<Phenotypic>> getChars();

}
