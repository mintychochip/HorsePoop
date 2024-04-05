package mintychochip.mintychochip.horsepoop.metas;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;

import java.util.List;

public class WeightedEnumMeta<U extends TraitEnum> extends EnumMeta<U> { //still has enum class, but calcs differently

    protected final List<Double> weights;

    public WeightedEnumMeta(U trait, String enumClass, List<Double> weights) {
        super(trait, enumClass);
        this.weights = weights;
    }
    public List<Double> getWeights() {
        return weights;
    }
}
