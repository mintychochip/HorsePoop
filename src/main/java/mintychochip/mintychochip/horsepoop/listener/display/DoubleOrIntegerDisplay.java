package mintychochip.mintychochip.horsepoop.listener.display;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.metas.DoubleMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import net.kyori.adventure.text.Component;

public class DoubleOrIntegerDisplay<U extends TraitEnum> implements Display<U> {
  @Override
  public Component getHoverText(Meta<U> meta) {
    Component component = Component.empty();
    if(meta instanceof DoubleMeta<U> dm) {
      component = component.append(Component.text("Minimum: " + dm.getMin())).append(Component.newline())
          .append(Component.text("Maximum: " + dm.getMax()));
    }
    return component;
  }
}
