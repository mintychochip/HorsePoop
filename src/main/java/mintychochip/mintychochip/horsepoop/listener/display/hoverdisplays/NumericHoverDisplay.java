package mintychochip.mintychochip.horsepoop.listener.display.hoverdisplays;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.listener.display.HoverDisplay;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import mintychochip.mintychochip.horsepoop.metas.Numeric;
import net.kyori.adventure.text.Component;

public final class NumericHoverDisplay<U extends TraitEnum> implements HoverDisplay<U> {
  @Override
  public Component getBody(BaseTrait<U> trait, int padding) {
    Component component = Component.empty();
    Meta<U> meta = trait.getMeta();
    if(meta instanceof Numeric numeric) {
      component = component.append(Component.text("Max: " + numeric.getMax())).append(Component.newline())
          .append(Component.text("Min: " + numeric.getMin()));
    }
    return component;
  }

  @Override
  public Component getHeader(BaseTrait<U> trait) {
    return Component.text("Numeric Trait:");
  }
}
