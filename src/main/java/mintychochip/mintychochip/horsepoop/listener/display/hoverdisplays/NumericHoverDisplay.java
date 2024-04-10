package mintychochip.mintychochip.horsepoop.listener.display.hoverdisplays;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.listener.display.HoverDisplay;
import mintychochip.mintychochip.horsepoop.metas.DoubleMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import net.kyori.adventure.text.Component;

public class NumericHoverDisplay<U extends TraitEnum> implements HoverDisplay<U> {
  @Override
  public Component getBody(Meta<U> meta) {
    Component component = Component.empty();
    if(meta instanceof DoubleMeta<U> dm) {
      component = component.append(Component.text("Minimum: " + dm.getMin())).append(Component.newline())
          .append(Component.text("Maximum: " + dm.getMax()));
    }
    return component;
  }

  @Override
  public Component getHeader(Meta<U> meta) {
    return Component.text("Double");
  }
}
