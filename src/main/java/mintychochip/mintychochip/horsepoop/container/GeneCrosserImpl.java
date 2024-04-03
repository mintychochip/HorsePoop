package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.Gson;
import mintychochip.mintychochip.horsepoop.config.GeneTraitMeta;
import mintychochip.mintychochip.horsepoop.config.TraitMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GeneCrosserImpl<U extends Trait, T extends TraitMeta> implements TraitCrosser<T> {
    private final Random random = new Random(System.currentTimeMillis());
    private final TraitFetcher<U, T> traitFetcher;

    public GeneCrosserImpl(TraitFetcher<U, T> traitFetcher) {
        this.traitFetcher = traitFetcher;
    }

    @Override
    public String crossTraitForValue(BaseTrait<T> father, BaseTrait<T> mother) {
        U fatherTrait = traitFetcher.getTrait(father.trait);
        U motherTrait = traitFetcher.getTrait(mother.trait);

        if (fatherTrait != motherTrait || fatherTrait.getValueType() != motherTrait.getValueType()) {
            return null;
        }
        if (father.getMeta() instanceof GeneTraitMeta gtmOne && mother.getMeta() instanceof GeneTraitMeta gtmTwo) {
            if (!gtmOne.crossable() && gtmTwo.crossable()) {
                return null;
            }
        }
        String fv = father.getValue();
        String mv = mother.getValue();
        return switch (fatherTrait.getValueType()) {
            case NUMERIC -> String.valueOf(numericCrossingCase(Double.parseDouble(fv), Double.parseDouble(mv)));
            case INTEGER -> String.valueOf((int) numericCrossingCase(Double.parseDouble(fv), Double.parseDouble(mv)));
            case MENDELIAN ->
                    new Gson().toJson(traitFetcher.getMendelian(father).crossGenes(traitFetcher.getMendelian(mother)));
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
