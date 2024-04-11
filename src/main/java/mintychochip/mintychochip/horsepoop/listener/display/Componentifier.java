package mintychochip.mintychochip.horsepoop.listener.display;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mintychochip.genesis.util.Colorful;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;
import mintychochip.mintychochip.horsepoop.listener.display.hoverdisplays.*;
import mintychochip.mintychochip.horsepoop.listener.display.text.TextValueDisplay;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import mintychochip.mintychochip.horsepoop.metas.MetaType;
import mintychochip.mintychochip.horsepoop.metas.Units;
import mintychochip.mintychochip.horsepoop.util.Unit;
import mintychochip.mintychochip.horsepoop.util.string.StringManipulation;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

public class Componentifier<U extends TraitEnum> implements Componentify<U> {
    private static final int DECIMAL_PLACES = 3;
    private final List<BaseTrait<U>> traits;

    public Componentifier(List<BaseTrait<U>> traits) {
        this.traits = traits;
    }

    public Component getComponent() {
        Component component = Component.text("");
        for (BaseTrait<U> trait : traits) {
            component = component.append(this.getIndividualComponent(trait)).append(Component.newline());
        }
        return component;
    }

    private Component getIndividualComponent(BaseTrait<U> trait) {
        return Component.empty().append(this.createIndividualKeyComponent(trait))
                .append(Component.text(": ").append(this.createIndividualValueComponent(trait)));
    }

    private Component createIndividualKeyComponent(@NotNull BaseTrait<U> trait) {
        U traitEnum = trait.getMeta().getTrait();
        TextColor color = NamedTextColor.GOLD; //magic value
        return Component.text(StringManipulation.capitalizeFirstLetter(traitEnum.getKey())).color(color)
                .hoverEvent(HoverEvent.showText(Component.text(traitEnum.getDescription())));
    }

    private Component createIndividualValueComponent(BaseTrait<U> trait) {
        Meta<U> meta = trait.getMeta();
        MetaType metaType = meta.getTrait().getMetaType();
        String value = trait.getValue();
        String text = switch (metaType) {
            case DOUBLE, CROSSABLE_DOUBLE -> this.roundStringIfDecimal(value);
            case MENDELIAN, CROSSABLE_MENDELIAN, POLYGENIC_MENDELIAN -> new Gson().fromJson(value,
                    MendelianGene.class).toString();
            case ENUM, WEIGHTED_ENUM -> StringManipulation.capitalizeFirstLetter(value.toLowerCase());
            default -> value;
        };
        Component component = Component.text(text);
        if (meta instanceof Units units) {
            Unit unit = units.getUnit();
            component = this.appendUnit(component, unit);
        }
        HoverDisplay<U> hoverDisplay = this.getHoverDisplayStrategy(trait);

        component = component.hoverEvent(HoverEvent.showText(this.getHoverText(hoverDisplay, 3)));
        return component;
    }

    private Component getHoverText(HoverDisplay<U> hoverDisplay, int padding) {
        return hoverDisplay.getHeader().append(Component.newline()).append(hoverDisplay.getBody(padding));
    }

    public TextValueDisplay
    public HoverDisplay<U> getHoverDisplayStrategy(BaseTrait<U> trait) {
        MetaType metaType = trait.getMeta().getTrait().getMetaType();
        return switch (metaType) {
            case ENUM, WEIGHTED_ENUM -> new EnumHoverDisplay<>();
            case DOUBLE, CROSSABLE_DOUBLE, INTEGER, CROSSABLE_INTEGER -> new NumericHoverDisplay<>(trait);
            case POLYGENIC_MENDELIAN -> new PolygenicHoverDisplay<>(trait, this.traits);
            case MENDELIAN, CROSSABLE_MENDELIAN -> new MendelianHoverDisplay<>();
        };
    }

}