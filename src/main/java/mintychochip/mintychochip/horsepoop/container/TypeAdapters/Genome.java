package mintychochip.mintychochip.horsepoop.container.TypeAdapters;

import java.util.List;

import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.CharacteristicTrait;
import mintychochip.mintychochip.horsepoop.container.GeneTrait;

public interface Genome {

  long getBirthTime();

  List<BaseTrait<GeneTrait>> getGenes();

  List<BaseTrait<CharacteristicTrait>> getChars();

}
