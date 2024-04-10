package mintychochip.mintychochip.horsepoop.listener.display.hoverdisplays;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.listener.display.HoverDisplay;
import mintychochip.mintychochip.horsepoop.metas.MendelianMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import net.kyori.adventure.text.Component;

public class MendelianHoverDisplay<U extends TraitEnum> implements HoverDisplay {
  private final BaseTrait<U> trait;

  public MendelianHoverDisplay(BaseTrait<U> trait) {
    this.trait = trait;
  }
  @Override
  public Component getBody(int padding) {
    Component component = Component.empty();
    Meta<U> meta = this.trait.getMeta();
    if(meta instanceof MendelianMeta<U> mm) {
      component = component.append(Component.text("Allele Chance: " + mm.getChance()));
    }
    return component;
  }

  @Override
  public Component getHeader() {
    return Component.text("Mendelian:");
  }
}
