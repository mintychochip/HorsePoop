package mintychochip.mintychochip.horsepoop.listener.display;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.metas.DoubleMeta;
import mintychochip.mintychochip.horsepoop.metas.IntegerMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import net.kyori.adventure.text.Component;

public class IntegerStrategy<U extends TraitEnum> implements Display<U> {

  @Override
  public Component getHoverText(Meta<U> meta) {
    Component component = Component.empty();
    if(meta instanceof IntegerMeta<U> im) {
      component = component.append(Component.text("Minimum: " + im.getMin())).append(Component.newline())
          .append(Component.text("Maximum: " + im.getMax()));
    }
    return component;
  }
}
