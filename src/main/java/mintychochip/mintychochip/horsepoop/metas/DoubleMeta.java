package mintychochip.mintychochip.horsepoop.metas;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.util.Unit;

public class DoubleMeta<U extends TraitEnum> extends AbstractMeta<U> implements
    Units { //by default does not cross

  protected double max;

  protected double min;

  protected Unit unit;

  public DoubleMeta(U trait, double max, double min) {
    super(trait);
    this.max = max;
    this.min = min;
  }

  public double getMax() {
    return max;
  }

  public double getMin() {
    return min;
  }

  @Override
  public Unit getUnit() {
    return unit;
  }
  @Override
  public void setUnits(Unit unit) {
    this.unit = unit;
  }
}
