package mintychochip.mintychochip.horsepoop.metas;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;
public class DoubleMeta<U extends TraitEnum> extends AbstractMeta<U> { //by default does not cross
    protected double max;

    protected double min;

    public DoubleMeta(U trait, double max, double min) {
        super(trait);
        this.max = max;
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

}
