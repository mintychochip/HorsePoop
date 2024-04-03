package mintychochip.mintychochip.horsepoop.metas;

import mintychochip.mintychochip.horsepoop.container.Trait;

public class CrossableDoubleMeta<U extends Trait> extends DoubleMeta<U> implements Crossable {

    protected boolean crossable = false;
    protected CrossableDoubleMeta(U trait, double max, double min) {
        super(trait, max, min);
    }

    @Override
    public void setCrossable(boolean crossable) {
        this.crossable = crossable;
    }

    @Override
    public boolean isCrossable() {
        return crossable;
    }
}
