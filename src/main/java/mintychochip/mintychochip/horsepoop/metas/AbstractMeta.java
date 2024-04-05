package mintychochip.mintychochip.horsepoop.metas;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;

abstract class AbstractMeta<U extends TraitEnum> implements Meta<U> {

  protected boolean conserved = false;
  protected U trait;

  protected AbstractMeta(U trait) {
    this.trait = trait;
  }

  @Override
  public U getTrait() {
    return trait;
  }

  @Override
  public void setConserved(boolean conserved) {
    this.conserved = conserved;
  }

  @Override
  public boolean isConserved() {
    return this.conserved;
  }
}
