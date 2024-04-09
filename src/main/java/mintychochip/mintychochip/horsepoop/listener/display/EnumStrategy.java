package mintychochip.mintychochip.horsepoop.listener.display;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.metas.EnumMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import net.kyori.adventure.text.Component;

public class EnumStrategy<U extends TraitEnum> implements Display<U> {

  @Override
  public Component getHoverText(Meta<U> meta) {
    Component component = Component.empty();
    if(meta instanceof EnumMeta<U> em) {

    }
    return component;
  }
}
