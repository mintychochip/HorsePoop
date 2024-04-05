package mintychochip.mintychochip.horsepoop.factories;

import java.util.Random;
import mintychochip.mintychochip.horsepoop.container.MetaValueGeneration;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import mintychochip.mintychochip.horsepoop.metas.WeightedEnumMeta;

import java.util.List;
import net.bytebuddy.pool.TypePool.Resolution.Illegal;

public class WeightedEnumGeneration<U extends Trait> implements MetaValueGeneration<U> {

  @Override
  public String generateValue(Meta<U> meta) {
    String value = null;
    if (meta instanceof WeightedEnumMeta<U> wem) {
      List<Double> weights = wem.getWeights();
      Object[] enumClassConstants = this.getEnumClassConstants(wem.getEnumClass());
      if(enumClassConstants == null) {
          return null;
      }
      if(weightSizeEqualsConstantSize(weights,enumClassConstants)) {
          throw new IllegalArgumentException("Number of weights doesn't equal the class constants, weights: " + weights.size() + " & class constant count: " + enumClassConstants.length);
      }
      double totalWeight = weights.stream().mapToDouble(Double::doubleValue).sum();
      double randomValue = new Random(System.currentTimeMillis()).nextDouble() * totalWeight;
      double cumulativeWeight = 0;
      for(int i = 0; i < weights.size(); i++) {
          cumulativeWeight += weights.get(i);
          if(randomValue < cumulativeWeight) {
              value = enumClassConstants[i].toString();
          }
      }
    }
    return value;
  }

  private boolean weightSizeEqualsConstantSize(List<Double> weights, Object[] enumConstants) {
    return weights.size() == enumConstants.length;
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
