package mintychochip.mintychochip.horsepoop.metas;

import java.util.Map;
import java.util.Set;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;

public interface Polygenic<U extends TraitEnum> {
    Map<U,MendelianGene> getStates();
    boolean isActive(Map<U,MendelianGene> traitStateMap, MendelianGene traitState);
    MendelianGene getState(U traitEnum);
    MendelianGene getThisState();
    void setTraitStates(Set<U> traitEnums, Set<MendelianGene> mendelianGenes);
    void setThisState(MendelianGene mendelianGene);
}
