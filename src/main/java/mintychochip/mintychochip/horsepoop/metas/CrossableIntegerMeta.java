package mintychochip.mintychochip.horsepoop.metas;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;

public class CrossableIntegerMeta<U extends TraitEnum> extends IntegerMeta<U> implements Crossable {

    protected boolean crossable = false;

    public CrossableIntegerMeta(U trait, double max, double min) {
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
