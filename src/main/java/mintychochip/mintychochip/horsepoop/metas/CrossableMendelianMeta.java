package mintychochip.mintychochip.horsepoop.metas;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;

import java.util.List;

public class CrossableMendelianMeta<U extends TraitEnum> extends MendelianMeta<U> implements Crossable {

  protected boolean crossable = false;

  public CrossableMendelianMeta(U trait, double chance, List<String> blacklist) {
    super(trait, chance, blacklist);
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
