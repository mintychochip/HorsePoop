package mintychochip.mintychochip.horsepoop.metas;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;

import java.util.List;

public class MendelianMeta<U extends TraitEnum> extends AbstractMeta<U>  { //uses 'mendelian'

    protected final double chance;
    protected final List<String> blacklist;
    public MendelianMeta(U trait, double chance, List<String> blacklist) {
        super(trait);
        this.blacklist = blacklist;
        this.chance = chance;
    }

    public double getChance() {
        return chance;
    }

    public List<String> getBlacklist() {
        return blacklist;
    }
}
