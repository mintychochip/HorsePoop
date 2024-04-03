package mintychochip.mintychochip.horsepoop.container;

import mintychochip.mintychochip.horsepoop.metas.Meta;

import java.util.List;
import java.util.Map;

public interface Fetcher<U extends Trait> {
  <Y extends Enum<Y>> Y getEnumValue(List<BaseTrait<U>> traits, U trait, Class<Y> enumClass);
  MendelianGene getMendelian(BaseTrait<U> trait);

   Map<U, BaseTrait<U>> getAttributes(List<BaseTrait<U>> baseTraits);

   BaseTrait<U> getTraitFromList(List<BaseTrait<U>> traits, U trait);
   boolean isTraitInList(List<BaseTrait<U>> traits, U trait);

  String toJson(Trait trait);
}
