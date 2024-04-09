package mintychochip.mintychochip.horsepoop.listener.display;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import net.kyori.adventure.text.Component;

public interface Display<U extends TraitEnum> {
  Component getHoverText(Meta<U> meta);

}
