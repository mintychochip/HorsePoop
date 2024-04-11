package mintychochip.mintychochip.horsepoop.listener.display.hoverdisplays;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.listener.display.HoverDisplay;
import mintychochip.mintychochip.horsepoop.metas.MendelianMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import net.kyori.adventure.text.Component;

public final class MendelianHoverDisplay<U extends TraitEnum> implements HoverDisplay<U> {
  @Override
  public Component getBody(BaseTrait<U> trait, int padding) {
    Component component = Component.empty();
    Meta<U> meta = trait.getMeta();
    if(meta instanceof MendelianMeta<U> mm) {
      component = component.append(Component.text("Allele Chance: " + mm.getChance()));
    }
    return component;
  }

  @Override
  public Component getHeader(BaseTrait<U> trait) {
    return Component.text("Mendelian Trait:");
  }
}
