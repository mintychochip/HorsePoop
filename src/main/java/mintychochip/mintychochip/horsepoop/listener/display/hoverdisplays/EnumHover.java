package mintychochip.mintychochip.horsepoop.listener.display.hoverdisplays;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.listener.display.HoverDisplay;
import mintychochip.mintychochip.horsepoop.metas.EnumMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public final class EnumHover<U extends TraitEnum> implements HoverDisplay {

  private final BaseTrait<U> trait;

  public EnumHover(BaseTrait<U> trait) {
    this.trait = trait;
  }
    private TextColor getTextColorFromEnum() {
        Meta<U> meta = this.trait.getMeta();
        if (meta instanceof EnumMeta<U> em) {
            try {
                Class<?> aClass = Class.forName(em.getEnumClass());
                if (Colorful.class.isAssignableFrom(aClass)) {
                    Colorful colorful = (Colorful) Enum.valueOf((Class<? extends Enum>) aClass, this.trait.getValue());
                    return colorful.getTextColor();
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public Component getBody(int padding) {
      Meta<U> meta = this.trait.getMeta();
      Component component = Component.empty();
      if(meta instanceof EnumMeta<U> em) {
        component = component.append(Component.text(this.trait.getValue()));
      }
      return component;
    }

    @Override
    public Component getHeader() {
        return Component.text("Enum:");
    }
}
