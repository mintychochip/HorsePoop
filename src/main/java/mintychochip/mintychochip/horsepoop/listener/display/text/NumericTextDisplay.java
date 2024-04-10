package mintychochip.mintychochip.horsepoop.listener.display.text;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.metas.DoubleMeta;
import mintychochip.mintychochip.horsepoop.metas.IntegerMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import mintychochip.mintychochip.horsepoop.util.Unit;
import mintychochip.mintychochip.horsepoop.util.math.Round;
import mintychochip.mintychochip.horsepoop.util.string.StringManipulation;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
public class NumericTextDisplay<U extends TraitEnum> implements TextValueDisplay {

    private static int DECIMAL_PLACES= 3;
    private final BaseTrait<U> trait;

    public NumericTextDisplay(BaseTrait<U> trait) {
        this.trait = trait;
    }
    @Override
    public Component getTextValue() {
        Meta<U> meta = this.trait.getMeta();
        double max;
        double min;

        String text = this.trait.getValue();
        if(meta instanceof DoubleMeta<U> dm) {
            max = dm.getMax();
            min = dm.getMin();
            text = this.roundStringIfDecimal(text);
        }
        if(meta instanceof IntegerMeta<U> im) {
            max = im.getMax();
            min = im.getMin();
        }

    }
    private String roundStringIfDecimal(String value) {
        try {
            double v = Double.parseDouble(value);
            return String.valueOf(Round.roundDecimal(v, DECIMAL_PLACES));
        } catch (NumberFormatException ex) {
            return value;
        }
    }
    private net.kyori.adventure.text.Component appendUnit(net.kyori.adventure.text.Component component, Unit unit) {
        return unit != null ? component
                .append(net.kyori.adventure.text.Component.text(" " + unit.getAbbreviation())
                        .hoverEvent(HoverEvent.showText(net.kyori.adventure.text.Component.text(StringManipulation.capitalizeFirstLetter(unit.toString().toLowerCase())))))
                : component;
    }

}
