package mintychochip.mintychochip.horsepoop.listener.display;

import java.util.List;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import mintychochip.mintychochip.horsepoop.metas.MetaType;
import net.kyori.adventure.text.Component;

public interface Componentify<U extends TraitEnum> {

  void setStrategySelector(DisplayStrategySelector<U> strategySelector);
}
