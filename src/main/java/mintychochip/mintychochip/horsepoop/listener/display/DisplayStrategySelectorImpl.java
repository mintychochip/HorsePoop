package mintychochip.mintychochip.horsepoop.listener.display;

import java.util.List;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.listener.display.hoverdisplays.EnumHoverDisplay;
import mintychochip.mintychochip.horsepoop.listener.display.hoverdisplays.MendelianHoverDisplay;
import mintychochip.mintychochip.horsepoop.listener.display.hoverdisplays.NumericHoverDisplay;
import mintychochip.mintychochip.horsepoop.listener.display.hoverdisplays.PolygenicHoverDisplay;
import mintychochip.mintychochip.horsepoop.listener.display.text.EnumTextStrategy;
import mintychochip.mintychochip.horsepoop.listener.display.text.MendelianTextDisplay;
import mintychochip.mintychochip.horsepoop.listener.display.text.NumericTextDisplay;
import mintychochip.mintychochip.horsepoop.listener.display.text.TextValueDisplay;
import mintychochip.mintychochip.horsepoop.metas.MetaType;

public final class DisplayStrategySelectorImpl<U extends TraitEnum> implements
    DisplayStrategySelector<U> {

  private final List<BaseTrait<U>> traits;

  public DisplayStrategySelectorImpl(List<BaseTrait<U>> traits) {
    this.traits = traits;
  }

  @Override
  public TextValueDisplay<U> getTextStrategy(MetaType metaType) {
    return switch (metaType) {
      case DOUBLE, CROSSABLE_DOUBLE, INTEGER, CROSSABLE_INTEGER -> new NumericTextDisplay<>();
      case ENUM, WEIGHTED_ENUM -> new EnumTextStrategy<>();
      case MENDELIAN, CROSSABLE_MENDELIAN, POLYGENIC_MENDELIAN -> new MendelianTextDisplay<>();
    };
  }

  @Override
  public HoverDisplay<U> getHoverTextStrategy(MetaType metaType) {
    return switch (metaType) {
      case ENUM, WEIGHTED_ENUM -> new EnumHoverDisplay<>();
      case DOUBLE, CROSSABLE_DOUBLE, INTEGER, CROSSABLE_INTEGER -> new NumericHoverDisplay<>();
      case POLYGENIC_MENDELIAN -> new PolygenicHoverDisplay<>(this.traits);
      case MENDELIAN, CROSSABLE_MENDELIAN -> new MendelianHoverDisplay<>();
    };
  }
}
