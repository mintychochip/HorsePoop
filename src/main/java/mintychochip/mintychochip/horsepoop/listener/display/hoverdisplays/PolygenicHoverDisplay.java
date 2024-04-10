package mintychochip.mintychochip.horsepoop.listener.display.hoverdisplays;

import java.util.List;
import java.util.Map;
import mintychochip.mintychochip.horsepoop.api.Fetcher;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;
import mintychochip.mintychochip.horsepoop.container.ValueFetcher;
import mintychochip.mintychochip.horsepoop.listener.display.HoverDisplay;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import mintychochip.mintychochip.horsepoop.metas.PolygenicMendelianMeta;
import mintychochip.mintychochip.horsepoop.util.string.StringManipulation;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public class PolygenicHoverDisplay<U extends TraitEnum> implements HoverDisplay {

  private final BaseTrait<U> trait;
  private final List<BaseTrait<U>> traits;
  private final Fetcher<U> fetcher = new ValueFetcher<>();
  public PolygenicHoverDisplay(BaseTrait<U> trait, List<BaseTrait<U>> traits) {
    this.trait = trait;
    this.traits = traits;
  }

  @Override
  public Component getBody(Meta<U> meta) {
    if (meta instanceof PolygenicMendelianMeta<U> polygenic) {
      Component component = Component.text("Required traits:").append(Component.newline());
      Map<U, MendelianGene> states = polygenic.getStates();
      for (U trait : states.keySet()) {
        component = component.append(this.buildLine(trait, states.get(trait)));
      }
      return component;
    }
    return Component.empty();
  }

  @Override
  public Component getHeader(Meta<U> meta) {
    return Component.text("Polygenic Mendelian");
  }

  private TextColor getColor(U trait, MendelianGene state) {
    BaseTrait<U> traitFromList = fetcher.getTraitFromList(traits, trait);
    if (traitFromList == null) {
      return NamedTextColor.RED;
    }
    MendelianGene mendelian = fetcher.getMendelian(traitFromList);
    return mendelian.equals(state) ? NamedTextColor.GREEN : NamedTextColor.RED;
  }

  private Component buildLine(U trait, MendelianGene state) {
    return Component.empty().append(Component.text('█').color(this.getColor(trait, state)))
        .append(Component.text(
            StringManipulation.capitalizeFirstLetter(trait.getKey().toLowerCase()) + " " + state));
  }

  @Override
  public Component getBody(int padding) {
    return null;
  }

  @Override
  public Component getHeader() {
    return null;
  }
}

