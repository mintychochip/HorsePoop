package mintychochip.mintychochip.horsepoop.metas;

import mintychochip.mintychochip.horsepoop.container.Trait;

public class CrossableIntegerMeta<U extends Trait> extends IntegerMeta<U> implements Crossable {

    protected boolean crossable = false;
    public CrossableIntegerMeta(U trait, int max, int min) {
        super(trait, max, min);
    }

    @Override
    public void setCrossable(boolean crossable) {
        this.crossable = crossable;
    }

    @Override
    public boolean isCrossable() {
        return false;
    }
}
