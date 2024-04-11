package mintychochip.mintychochip.horsepoop.listener.display.text;


import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import net.kyori.adventure.text.Component;

public interface TextValueDisplay<U extends TraitEnum> {
    Component getTextValue(BaseTrait<U> trait);
}
