package mintychochip.mintychochip.horsepoop.metas;

import mintychochip.mintychochip.horsepoop.container.Trait;

import java.util.List;

public class MendelianMeta<U extends Trait> extends AbstractMeta<U>  { //uses 'mendelian'
    protected final boolean conserved;
    protected final List<Trait> blacklist;
    public MendelianMeta(U trait, boolean conserved, List<Trait> blacklist) {
        super(trait);
        this.conserved = conserved;
        this.blacklist = blacklist;
    }
    public List<Trait> getBlacklist() {
        return blacklist;
    }
    public boolean isConserved() {
        return conserved;
    }
}
