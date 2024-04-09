package mintychochip.mintychochip.horsepoop.listener.display;

import com.google.gson.Gson;
import java.util.List;
import mintychochip.genesis.util.Colorful;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;
import mintychochip.mintychochip.horsepoop.metas.EnumMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import mintychochip.mintychochip.horsepoop.metas.MetaType;
import mintychochip.mintychochip.horsepoop.metas.Units;
import mintychochip.mintychochip.horsepoop.util.Unit;
import mintychochip.mintychochip.horsepoop.util.math.Round;
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
    if (meta instanceof EnumMeta<U> em) {
      TextColor textColorFromEnum = this.getTextColorFromEnum(em, value);
      component = component.color(textColorFromEnum);
    }
    if (meta instanceof Units units) {
      Unit unit = units.getUnit();
      component = this.appendUnit(component, unit);
    }
    component = component.hoverEvent(HoverEvent.showText(this.getHoverText(meta)));
    return component;
  }

  private Component appendUnit(Component component, Unit unit) {
    if (unit == null) {
      return component;
    }
    return component.append(Component.text(" " + unit.getAbbreviation()).hoverEvent(
        HoverEvent.showText(Component.text(
            StringManipulation.capitalizeFirstLetter(unit.toString().toLowerCase())))));
  }

  private TextColor getTextColorFromEnum(EnumMeta<U> meta, String value) {
    try {
      Class<?> aClass = Class.forName(meta.getEnumClass());
      if (Colorful.class.isAssignableFrom(aClass)) {
        Colorful colorful = (Colorful) Enum.valueOf((Class<? extends Enum>) aClass, value);
        return colorful.getTextColor();
      }
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  private String roundStringIfDecimal(String value) {
    try {
      double v = Double.parseDouble(value);
      return String.valueOf(Round.roundDecimal(v, DECIMAL_PLACES));
    } catch (NumberFormatException ex) {
      return value;
    }
  }

  @Override
  public Component getHoverText(Meta<U> meta) {
    Display<U> strategy = switch (meta.getTrait().getMetaType()) {
      case INTEGER, CROSSABLE_INTEGER -> new IntegerStrategy<>();
      case DOUBLE, CROSSABLE_DOUBLE -> new DoubleOrIntegerDisplay<>();
      case ENUM, WEIGHTED_ENUM -> new EnumStrategy<>();
      case POLYGENIC_MENDELIAN -> new PolygenicDisplay<>(traits);
      case MENDELIAN, CROSSABLE_MENDELIAN -> new MendelianDisplay<>();
    };
    return strategy.getHoverText(meta);
  }
}