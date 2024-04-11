package mintychochip.mintychochip.horsepoop.listener.display;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.listener.display.text.TextValueDisplay;
import mintychochip.mintychochip.horsepoop.metas.MetaType;

public interface DisplayStrategySelector<U extends TraitEnum> {
  TextValueDisplay<U> getTextStrategy(MetaType metaType);
  HoverDisplay<U> getHoverTextStrategy(MetaType metaType);

}
