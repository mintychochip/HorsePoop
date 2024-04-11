package mintychochip.mintychochip.horsepoop.listener.display.hoverdisplays;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.listener.display.HoverDisplay;
import mintychochip.mintychochip.horsepoop.metas.EnumMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public final class EnumHoverDisplay<U extends TraitEnum> implements HoverDisplay<U> {
    private TextColor getTextColorFromEnum(Meta<U> meta, String value) {
        if (meta instanceof EnumMeta<U> em) {
            try {
                Class<?> aClass = Class.forName(em.getEnumClass());
                if (Colorful.class.isAssignableFrom(aClass)) {
                    Colorful colorful = (Colorful) Enum.valueOf((Class<? extends Enum>) aClass, value);
                    return colorful.getTextColor();
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public Component getBody(BaseTrait<U> trait, int padding) {
      Meta<U> meta = trait.getMeta();
      Component component = Component.empty();
      if(meta instanceof EnumMeta<U> em) {
        component = component.append(Component.text(trait.getValue()));
      }
      return component;
    }

    @Override
    public Component getHeader(BaseTrait<U> trait) {
        return Component.text("Enum:");
    }
}
