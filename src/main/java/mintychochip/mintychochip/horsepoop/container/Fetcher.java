package mintychochip.mintychochip.horsepoop.container;

import java.util.List;
import java.util.Map;

import mintychochip.mintychochip.horsepoop.config.TraitMeta;

public interface Fetcher<U extends Trait,T extends TraitMeta> {
  <Y extends Enum<Y>> Y getEnumValue(List<BaseTrait<T>> traits, U trait, Class<Y> enumClass);

  U getTrait(String trait);

  MendelianGene getMendelian(BaseTrait<T> trait);

   Map<U, BaseTrait<T>> getAttributes(List<BaseTrait<T>> baseTraits);

   BaseTrait<T> getTraitFromList(List<BaseTrait<T>> traits, U trait);

   boolean isTraitInList(List<BaseTrait<T>> traits, U trait);

  String toJson(Trait trait);
}
