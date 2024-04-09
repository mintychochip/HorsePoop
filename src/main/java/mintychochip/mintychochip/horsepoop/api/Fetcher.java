package mintychochip.mintychochip.horsepoop.api;

import java.util.List;
import java.util.Map;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;


public interface Fetcher<U extends TraitEnum> {

  /**
   *
   * @param traits List of BaseTraits that are a member of U, likely taken from an AnimalGenome instance
   * @param trait Discrete U trait of interest
   * @param enumClass enumClass the string value will be casted to
   * @return value is a member of enumClass
   * @param <Y>
   */
  <Y extends Enum<Y>> Y getEnumValue(List<BaseTrait<U>> traits, U trait, Class<Y> enumClass);

  /**
   *
   * @param trait
   * @return
   */
  MendelianGene getMendelian(BaseTrait<U> trait);

   Map<U, BaseTrait<U>> getAttributes(List<BaseTrait<U>> baseTraits);

   BaseTrait<U> getTraitFromList(List<BaseTrait<U>> traits, U trait);
   boolean isTraitInList(List<BaseTrait<U>> traits, U trait);

  String toJson(TraitEnum traitEnum);
}
