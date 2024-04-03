package mintychochip.mintychochip.horsepoop.metas;

import mintychochip.mintychochip.horsepoop.container.Trait;

import java.util.HashSet;
import java.util.List;

public final class PolygenicMendelianMeta<U extends Trait> extends CrossableMendelianMeta<U> implements Polygenic {
    private List<Trait> requiredTraits;

    public PolygenicMendelianMeta(U trait, boolean conserved, List<Trait> blacklist) {
        super(trait, conserved, blacklist);
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
