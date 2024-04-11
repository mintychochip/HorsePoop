package mintychochip.mintychochip.horsepoop.listener.display.hoverdisplays;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.listener.display.HoverDisplay;
import mintychochip.mintychochip.horsepoop.metas.DoubleMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import mintychochip.mintychochip.horsepoop.metas.Numeric;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

public class NumericHoverDisplay<U extends TraitEnum> implements HoverDisplay<U> {

  private final BaseTrait<U> trait;

  public NumericHoverDisplay(BaseTrait<U> trait) {
    this.trait = trait;
  }
  @Override
  public Component getBody(BaseTrait<U> trait, int padding) {
    Component component = Component.empty();
    Meta<U> meta = trait.getMeta();
    if(meta instanceof Numeric numeric) {
      component = component.append(Component.text("Max: " + numeric.getMax()))
              .append(Component.text("Min: " + numeric.getMin()));
    }
    return component;
  }

  @Override
  public Component getHeader(BaseTrait<U> trait) {
    return Component.text("Numeric: ");
  }
}
