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
import mintychochip.mintychochip.horsepoop.metas.Polygenic;
import mintychochip.mintychochip.horsepoop.metas.PolygenicMendelianMeta;
import mintychochip.mintychochip.horsepoop.util.string.StringManipulation;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public class PolygenicHoverDisplay<U extends TraitEnum> implements HoverDisplay<U> {
  private final List<BaseTrait<U>> traits;
  private final Fetcher<U> fetcher = new ValueFetcher<>();
  public PolygenicHoverDisplay(List<BaseTrait<U>> traits) {
    this.traits = traits;
  }
  @Override
  public Component getBody(BaseTrait<U> trait, int padding) {
    Meta<U> meta = trait.getMeta();
    Component component = Component.empty();
    if(meta instanceof PolygenicMendelianMeta<U> polygenic) {
      component = component.append(Component.text("Required traits:")).append(Component.newline());
      Map<U, MendelianGene> states = polygenic.getStates();
      for(U traitKey : states.keySet()) {
        component = component.append(this.buildLine(traitKey,states.get(traitKey)));
      }
    }
    return component;
  }
  @Override
  public Component getHeader(BaseTrait<U> trait) {
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
}

