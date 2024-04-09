package mintychochip.mintychochip.horsepoop.factories;

import java.util.Arrays;
import java.util.List;
import mintychochip.mintychochip.horsepoop.api.MetaValueGeneration;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import mintychochip.mintychochip.horsepoop.metas.WeightedEnumMeta;

public class WeightedEnumGeneration<U extends TraitEnum> implements MetaValueGeneration<U> {

  @Override
  public String generateValue(Meta<U> meta) {

    String value = null;
    if (meta instanceof WeightedEnumMeta<U> wem) {
      List<Double> weights = wem.getWeights();

      Object[] enumClassConstants = this.getEnumClassConstants(wem.getEnumClass());
      if(enumClassConstants == null) {
          return null;
      }
      WeightedRandoms<Object> instance = WeightedRandoms.instance(Arrays.asList(enumClassConstants),
          weights);
      if(instance == null) {
        return null;
      }
      value = String.valueOf(instance.next());
    }
    return value;
  }
  private Object[] getEnumClassConstants(String classString) {
    try {
      Class<?> aClass = Class.forName(classString);
      if (Enum.class.isAssignableFrom(aClass)) {
        return aClass.getEnumConstants();
      }
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    return null;
  }
}
