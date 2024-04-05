package mintychochip.mintychochip.horsepoop.metas;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;

import java.util.HashSet;
import java.util.List;

public class PolygenicMendelianMeta<U extends TraitEnum> extends CrossableMendelianMeta<U> implements Polygenic {
    private List<TraitEnum> requiredTraitEnums;

    public PolygenicMendelianMeta(U trait, double chance, List<String> blacklist) {
        super(trait, chance, blacklist);
    }

    @Override
    public List<TraitEnum> getRequiredTraits() {
        return requiredTraitEnums;
    }

    @Override
    public void setRequiredTraits(List<TraitEnum> traitEnums) {
        this.requiredTraitEnums = traitEnums;
    }

    public boolean isActive(List<TraitEnum> traitEnums) {
        return new HashSet<>(traitEnums).containsAll(requiredTraitEnums);
    }
}
