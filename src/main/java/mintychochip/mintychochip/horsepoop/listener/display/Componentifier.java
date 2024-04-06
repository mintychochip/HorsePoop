package mintychochip.mintychochip.horsepoop.listener.display;

import com.google.gson.Gson;
import java.util.List;
import mintychochip.genesis.util.Colorful;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import mintychochip.mintychochip.horsepoop.metas.MetaType;
import mintychochip.mintychochip.horsepoop.util.math.Round;
import mintychochip.mintychochip.horsepoop.util.string.StringManipulation;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEvent.Action;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class Componentifier<U extends TraitEnum> {

  private static final int DECIMAL_PLACES = 3;

  public Component getComponent(List<BaseTrait<U>> traits) {
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
    value = switch (metaType) {
      case DOUBLE, CROSSABLE_DOUBLE -> this.roundStringIfDecimal(value);
      case MENDELIAN, CROSSABLE_MENDELIAN, POLYGENIC_MENDELIAN -> new Gson().fromJson(value,
          MendelianGene.class).toString();
      default -> trait.getValue();
    };

    TextComponent text = Component.text(value);
//    if (meta.getTrait() instanceof Colorful colorful) {
//      TextColor textColor = colorful.getTextColor();
//      return text.color(textColor);
//    }
    return text;
  }

  private String roundStringIfDecimal(String value) {
    try {
      double v = Double.parseDouble(value);
      return String.valueOf(Round.roundDecimal(v, DECIMAL_PLACES));
    } catch (NumberFormatException ex) {
      return value;
    }
  }
}