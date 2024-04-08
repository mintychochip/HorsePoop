package mintychochip.mintychochip.horsepoop.metas;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;

import java.util.List;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;

public class PolygenicMendelianMeta<U extends TraitEnum> extends
    CrossableMendelianMeta<U> implements Polygenic<U> {

  /**
   * Holds a traitEnum and MendelianGene in the correct state for the polygenic trait to be active
   * When queried using a traitEnum, returns a MendelianGene in the correct state.
   */
  private Map<U, MendelianGene> states;
  private MendelianGene state = null;

  public PolygenicMendelianMeta(U trait, double chance, List<String> blacklist) {
    super(trait, chance, blacklist);
  }
  @Override
  public Map<U, MendelianGene> getStates() {
    return states;
  }
  @Override
  public boolean isActive(Map<U, MendelianGene> traitStateMap, MendelianGene traitState) {
    if (state == null || traitState.equals(state)) {
      return false;
    }
    for (U traitEnum : traitStateMap.keySet()) {
      MendelianGene mendelianGene = traitStateMap.get(traitEnum);
      if (mendelianGene == null) {
        return false;
      }
      if (!this.states.get(traitEnum).equals(mendelianGene)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public MendelianGene getState(U traitEnum) {
    return states.get(traitEnum);
  }

  @Override
  public MendelianGene getThisState() {
    return state;
  }
  @Override
  public void setTraitStates(Set<U> traitEnums, Set<MendelianGene> mendelianGenes) {
    if (traitEnums == null || mendelianGenes == null) {
      return;
    }
    if (traitEnums.size() == mendelianGenes.size()) {
      Map<U, MendelianGene> states = new HashMap<>();
      Iterator<U> iterator = traitEnums.iterator();
      Iterator<MendelianGene> geneIterator = mendelianGenes.iterator();
      while (iterator.hasNext() && geneIterator.hasNext()) {
        states.put(iterator.next(), geneIterator.next());
      }
      this.states = states;
    }
  }

  @Override
  public void setThisState(MendelianGene state) {
    this.state = state;
  }
}
