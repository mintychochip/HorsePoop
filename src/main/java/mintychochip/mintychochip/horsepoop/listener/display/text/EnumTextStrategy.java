package mintychochip.mintychochip.horsepoop.listener.display.text;

import mintychochip.genesis.util.Colorful;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.metas.EnumMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import mintychochip.mintychochip.horsepoop.util.string.StringManipulation;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class EnumTextStrategy<U extends TraitEnum> implements TextValueDisplay<U> {

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
  public Component getTextValue(BaseTrait<U> trait) {
    String value = trait.getValue();
    Meta<U> meta = trait.getMeta();
    return Component.text(StringManipulation.capitalizeFirstLetter(value.toLowerCase())).color(this.getTextColorFromEnum(meta,value));
  }
}
