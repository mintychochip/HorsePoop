package mintychochip.mintychochip.horsepoop.container;

import java.util.List;
import mintychochip.mintychochip.horsepoop.config.TraitMeta;

public interface Fetcher<T extends TraitMeta> {
  <Y extends Enum<Y>, U extends Trait> Y getEnumValue(List<BaseTrait<T>> traits, U trait, Class<Y> enumClass);

  Trait getTrait(String trait);

  MendelianGene getMendelian(BaseTrait<T> trait);

  <U extends Trait> BaseTrait<T> getTraitFromList(List<BaseTrait<T>> traits, U Trait);

  <U extends Trait> boolean isTraitInList(List<BaseTrait<T>> traits, U trait);

  String toJson(Trait trait);
}
