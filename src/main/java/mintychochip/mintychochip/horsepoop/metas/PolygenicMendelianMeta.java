package mintychochip.mintychochip.horsepoop.metas;

import mintychochip.mintychochip.horsepoop.container.Trait;

import java.util.HashSet;
import java.util.List;

public class PolygenicMendelianMeta<U extends Trait> extends CrossableMendelianMeta<U> implements Polygenic {
    private List<Trait> requiredTraits;

    public PolygenicMendelianMeta(U trait, double chance, List<String> blacklist) {
        super(trait, chance, blacklist);
    }

    @Override
    public List<Trait> getRequiredTraits() {
        return requiredTraits;
    }

    @Override
    public void setRequiredTraits(List<Trait> traits) {
        this.requiredTraits = traits;
    }

    public boolean isActive(List<Trait> traits) {
        return new HashSet<>(traits).containsAll(requiredTraits);
    }
}
