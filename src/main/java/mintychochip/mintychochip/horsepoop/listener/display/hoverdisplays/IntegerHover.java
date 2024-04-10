package mintychochip.mintychochip.horsepoop.listener.display.hoverdisplays;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.listener.display.HoverDisplay;
import mintychochip.mintychochip.horsepoop.metas.IntegerMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import net.kyori.adventure.text.Component;

public class IntegerHover<U extends TraitEnum> implements HoverDisplay {

  private final BaseTrait<U> trait;

  public IntegerHover(BaseTrait<U> trait) {
    this.trait = trait;
  }

  @Override
  public Component getBody(int padding) {
    Component component = Component.empty();
    Meta<U> meta = this.trait.getMeta();
    if(meta instanceof IntegerMeta<U> im) {
      component = component.append(Component.text("Minimum: " + im.getMin())).append(Component.newline())
              .append(Component.text("Maximum: " + im.getMax()));
    }
    return component;
  }

  @Override
  public Component getHeader() {
    return Component.text("Integer");
  }
}
