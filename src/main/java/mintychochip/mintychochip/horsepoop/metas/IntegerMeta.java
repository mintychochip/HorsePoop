package mintychochip.mintychochip.horsepoop.metas;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;
public class IntegerMeta<U extends TraitEnum> extends AbstractMeta<U> {
    protected int max;
    protected int min;

    public IntegerMeta(U trait, int max, int min) {
        super(trait);
        this.max = max;
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }
}
