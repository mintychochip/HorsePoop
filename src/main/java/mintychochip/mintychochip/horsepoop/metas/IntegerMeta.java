package mintychochip.mintychochip.horsepoop.metas;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.util.Unit;

public class IntegerMeta<U extends TraitEnum> extends AbstractMeta<U> implements Units, Numeric {
    protected int max;
    protected int min;

    protected Unit unit;

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

    @Override
    public Unit getUnit() {
        return unit;
    }

    @Override
    public void setUnits(Unit unit) {
        this.unit = unit;
    }
}
