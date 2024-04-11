package mintychochip.mintychochip.horsepoop.listener.display.hoverdisplays;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.listener.display.HoverDisplay;
import mintychochip.mintychochip.horsepoop.metas.EnumMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import net.kyori.adventure.text.Component;

public final class EnumHoverDisplay<U extends TraitEnum> implements
    HoverDisplay<U> {

  @Override
  public Component getBody(BaseTrait<U> trait, int padding) {
    Meta<U> meta = trait.getMeta();
    Component component = Component.empty();
    if (meta instanceof EnumMeta<U> em) {
      component = component.append(Component.text(trait.getValue()));
    }
    return component;
  }

  @Override
  public Component getHeader(BaseTrait<U> trait) {
    return Component.text("Enum Trait:");
  }
}
