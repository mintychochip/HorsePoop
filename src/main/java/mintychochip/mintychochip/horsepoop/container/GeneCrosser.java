package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GeneCrosser {
  private final Random random;
  private final Gson gson;
  private final TraitFetcher traitFetcher;
  public GeneCrosser(Random random, Gson gson, TraitFetcher traitFetcher) {
    this.random = random;
    this.gson = gson;
    this.traitFetcher = traitFetcher;
  }
  public String crossGeneForValue(Gene father, Gene mother) {
    GeneTrait motherTrait = traitFetcher.getGeneTrait(mother.getTrait());
    GeneTrait fatherTrait = traitFetcher.getGeneTrait(father.getTrait());

    ValueType valueType = motherTrait.getValueType();
    if (fatherTrait != motherTrait || fatherTrait.getValueType() != motherTrait.getValueType() || (
        !father.isCrossable() && mother.isCrossable())) {
      return null;
    }

    String fv = father.getValue();
    String mv = mother.getValue();

    return switch (valueType) {
      case NUMERIC -> String.valueOf(numericCrossingCase(Double.parseDouble(fv), Double.parseDouble(mv)));
      case INTEGER -> String.valueOf((int) numericCrossingCase(Double.parseDouble(fv), Double.parseDouble(mv)));
      case MENDELIAN -> gson.toJson(traitFetcher.getMendelian(father).crossGenes(traitFetcher.getMendelian(mother)));
      default -> null;
    };
  }
  private double numericCrossingCase(double fv, double mv) {
    List<Double> minMax = findMinMax(fv, mv);
    return minMax.get(0) + random.nextDouble() * (minMax.get(1) - minMax.get(0));
  }
  private <T extends Comparable<T>> List<T> findMinMax(T a, T b) {
    T min = a.compareTo(b) < 0 ? a : b;
    T max = a.compareTo(b) > 0 ? a : b;
    return new ArrayList<>(Arrays.asList(min, max));
  }
}
