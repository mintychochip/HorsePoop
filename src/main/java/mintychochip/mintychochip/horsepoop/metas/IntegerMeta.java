package mintychochip.mintychochip.horsepoop.metas;

import mintychochip.mintychochip.horsepoop.container.Trait;
public class IntegerMeta<U extends Trait> extends AbstractMeta<U> {
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
