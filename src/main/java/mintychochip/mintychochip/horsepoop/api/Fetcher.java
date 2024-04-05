package mintychochip.mintychochip.horsepoop.api;

import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;

import java.util.List;
import java.util.Map;

public interface Fetcher<U extends TraitEnum> {
  <Y extends Enum<Y>> Y getEnumValue(List<BaseTrait<U>> traits, U trait);
  MendelianGene getMendelian(BaseTrait<U> trait);

   Map<U, BaseTrait<U>> getAttributes(List<BaseTrait<U>> baseTraits);

   BaseTrait<U> getTraitFromList(List<BaseTrait<U>> traits, U trait);
   boolean isTraitInList(List<BaseTrait<U>> traits, U trait);

  String toJson(TraitEnum traitEnum);
}
