package mintychochip.mintychochip.horsepoop.listener.display;

import java.util.List;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.metas.MetaType;
import mintychochip.mintychochip.horsepoop.util.string.StringManipulation;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

public class Componentifier<U extends TraitEnum> implements Componentify<U> {
    private final List<BaseTrait<U>> traits;

    private DisplayStrategySelector<U> strategySelector;
    public Componentifier(List<BaseTrait<U>> traits) {
        this.traits = traits;
        this.strategySelector = new DisplayStrategySelectorImpl<>(traits);
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
        MetaType metaType = trait.getMetaType();
        Component textValue = strategySelector.getTextStrategy(metaType).getTextValue(trait);
        return textValue.hoverEvent(HoverEvent.showText(this.getHoverText(trait)));
    }

    private Component getHoverText(BaseTrait<U> trait) {
        HoverDisplay<U> hoverTextStrategy = strategySelector.getHoverTextStrategy(
            trait.getMetaType());
        return hoverTextStrategy.getHeader(trait).append(Component.newline())
            .append(hoverTextStrategy.getBody(trait,3));
    }
    @Override
    public void setStrategySelector(DisplayStrategySelector<U> strategySelector) {
        this.strategySelector = strategySelector;
    }
}