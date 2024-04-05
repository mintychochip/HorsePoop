package mintychochip.mintychochip.horsepoop.metas;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;

public class CrossableDoubleMeta<U extends TraitEnum> extends DoubleMeta<U> implements Crossable {

    protected boolean crossable = false;
    public CrossableDoubleMeta(U trait, double max, double min) {
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
