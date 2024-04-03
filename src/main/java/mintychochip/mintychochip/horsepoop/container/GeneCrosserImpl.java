package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.Gson;
import mintychochip.mintychochip.horsepoop.config.GeneTraitMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GeneCrosserImpl<U extends Trait, T extends Characteristic> implements Crosser<T> {

  private final Random random = new Random(System.currentTimeMillis());
  private final Fetcher<U, T> fetcher = new ValueFetcher<>();
  @Override
  public BaseTrait<T> crossTraits(BaseTrait<T> father, BaseTrait<T> mother) {
    String value = crossTraitForValue(father, mother);
    if (value == null) {
      return null;
    }
    if (!father.getTrait().equals(mother.getTrait())) {
      return null;
    }
    if (!father.getMeta().equals(mother.getMeta())) {
      return null;
    }
    return BaseTrait.createCrossedInstance(value, father.getTrait(), father.getMeta(), this);
  }

  private String crossTraitForValue(BaseTrait<T> father, BaseTrait<T> mother) {
    U fatherTrait = fetcher.getTrait(father.trait);
    U motherTrait = fetcher.getTrait(mother.trait);

    if (fatherTrait != motherTrait || fatherTrait.getValueType() != motherTrait.getValueType()) {
      return null;
    }
    if (father.getMeta() instanceof GeneTraitMeta gtmOne
        && mother.getMeta() instanceof GeneTraitMeta gtmTwo) {
      if (!gtmOne.crossable() && gtmTwo.crossable()) {
        return null;
      }
    }
    String fv = father.getValue();
    String mv = mother.getValue();
    return switch (fatherTrait.getValueType()) {
      case NUMERIC ->
          String.valueOf(numericCrossingCase(Double.parseDouble(fv), Double.parseDouble(mv)));
      case INTEGER ->
          String.valueOf((int) numericCrossingCase(Double.parseDouble(fv), Double.parseDouble(mv)));
      case MENDELIAN -> new Gson().toJson(
          fetcher.getMendelian(father).crossGenes(fetcher.getMendelian(mother)));
      default -> null;
    };
  }

  private double numericCrossingCase(double fv, double mv) {
    List<Double> minMax = findMinMax(fv, mv);
    return minMax.get(0) + random.nextDouble() * (minMax.get(1) - minMax.get(0));
  }

  private <Y extends Comparable<Y>> List<Y> findMinMax(Y a, Y b) {
    Y min = a.compareTo(b) < 0 ? a : b;
    Y max = a.compareTo(b) > 0 ? a : b;
    return new ArrayList<>(Arrays.asList(min, max));
  }
}
