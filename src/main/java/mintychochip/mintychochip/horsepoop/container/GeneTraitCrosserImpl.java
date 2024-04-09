package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import mintychochip.mintychochip.horsepoop.api.Fetcher;
import mintychochip.mintychochip.horsepoop.api.TraitCrosser;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.metas.Crossable;
import mintychochip.mintychochip.horsepoop.metas.DoubleMeta;
import mintychochip.mintychochip.horsepoop.metas.IntegerMeta;
import mintychochip.mintychochip.horsepoop.metas.MendelianMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;

public class GeneTraitCrosserImpl<U extends TraitEnum> implements TraitCrosser<U> {

  private final Random random = new Random(System.currentTimeMillis());
  private final Fetcher<U> fetcher = new ValueFetcher<>();


  private String crossTraitForValue(BaseTrait<U> father, BaseTrait<U> mother) {
    U fatherTrait = father.getMeta().getTrait();
    U motherTrait = mother.getMeta().getTrait();

    if (fatherTrait != motherTrait || fatherTrait.getMetaType() != motherTrait.getMetaType()) {
      return null;
    }
    Meta<U> meta = father.getMeta(); //metas should be the same
    if(father.getMeta() instanceof Crossable fc && mother.getMeta() instanceof Crossable mc) {
      if(!fc.isCrossable() && !mc.isCrossable()) {
        return null;
      }
      if(meta instanceof MendelianMeta<U>) {
        MendelianGene mendelianGene = fetcher.getMendelian(father).cross(fetcher.getMendelian(mother));
        return new Gson().toJson(mendelianGene);
      }
      if(meta instanceof DoubleMeta<U>) {
        double fatherDouble = Double.parseDouble(father.getValue());
        double motherDouble = Double.parseDouble(mother.getValue());
        return String.valueOf(this.numericCrossingCase(fatherDouble,motherDouble));
      }
      if(meta instanceof IntegerMeta<U>) {
        List<Integer> minMax = findMinMax(Integer.parseInt(father.getValue()), Integer.parseInt(mother.getValue()));
        return String.valueOf((int) numericCrossingCase(minMax.get(0),minMax.get(1)));
      }
    }
    return null;
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
  @Override
  public BaseTrait<U> crossTraits(BaseTrait<U> father, BaseTrait<U> mother) {
    String value = crossTraitForValue(father, mother);
    if(value == null) {
      return null;
    }
    return BaseTrait.create(value,father.getMeta(),this);
  }
}
