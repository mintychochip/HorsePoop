package mintychochip.mintychochip.horsepoop.metas;

import mintychochip.mintychochip.horsepoop.container.Trait;

import java.util.List;

public class CrossableMendelianMeta<U extends Trait> extends MendelianMeta<U> implements Crossable {

    protected boolean crossable = false;
    public CrossableMendelianMeta(U trait, boolean conserved, List<Trait> blacklist) {
        super(trait, conserved, blacklist);
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
