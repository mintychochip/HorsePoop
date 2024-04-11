package mintychochip.mintychochip.horsepoop.listener.display;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;

public interface Componentify<U extends TraitEnum> {

  void setStrategySelector(DisplayStrategySelector<U> strategySelector);
}
