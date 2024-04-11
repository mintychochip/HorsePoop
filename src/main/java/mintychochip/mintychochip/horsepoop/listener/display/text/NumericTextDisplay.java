package mintychochip.mintychochip.horsepoop.listener.display.text;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.metas.DoubleMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import mintychochip.mintychochip.horsepoop.metas.UnitHolder;
import mintychochip.mintychochip.horsepoop.util.Unit;
import mintychochip.mintychochip.horsepoop.util.math.Round;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;

public class NumericTextDisplay<U extends TraitEnum> implements TextValueDisplay<U> {

  private static int DECIMAL_PLACES = 3;

  @Override
  public Component getTextValue(BaseTrait<U> trait) {
    Meta<U> meta = trait.getMeta();

    String text = trait.getValue();
    if (meta instanceof DoubleMeta<U>) {
      text = this.roundStringIfDecimal(text);
    }
    Component component = Component.empty().append(Component.text(text));
    if (meta instanceof UnitHolder unitHolder) {
        Unit metricUnit = unitHolder.getUnit();
        if(metricUnit != null) {
            component = component.append(Component.text(metricUnit.getAbbreviation()).hoverEvent(
                HoverEvent.showText(Component.text(metricUnit.toString()))));
        }
    }
    return component;
  }

  private String roundStringIfDecimal(String value) {
    try {
      double v = Double.parseDouble(value);
      return String.valueOf(Round.roundDecimal(v, DECIMAL_PLACES));
    } catch (NumberFormatException ex) {
      return value;
    }
  }
}
