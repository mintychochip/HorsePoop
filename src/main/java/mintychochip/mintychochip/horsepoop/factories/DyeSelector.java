package mintychochip.mintychochip.horsepoop.factories;

import com.google.gson.Gson;
import java.util.List;
import mintychochip.mintychochip.horsepoop.config.GeneTraitMeta;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.GeneTrait;
import mintychochip.mintychochip.horsepoop.container.TraitFetcher;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianType;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.SheepGeneTrait;
import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class DyeSelector {

  private final TraitFetcher<GeneTraitMeta> traitFetcher = new TraitFetcher<>();
  public DyeColor calculateDyeColor(AnimalGenome genome, LivingEntity livingEntity) {
    if (livingEntity.getType() != EntityType.SHEEP) {
      return null;
    }
    List<BaseTrait<GeneTraitMeta>> genes = genome.getGenes();
    Gson gson = new Gson();

    BaseTrait<GeneTraitMeta> red = traitFetcher.getTraitFromList(genes, SheepGeneTrait.RED);
    BaseTrait<GeneTraitMeta> blue = traitFetcher.getTraitFromList(genes,SheepGeneTrait.BLUE);
    BaseTrait<GeneTraitMeta> green = traitFetcher.getTraitFromList(genes,SheepGeneTrait.GREEN);
    BaseTrait<GeneTraitMeta> brightness = traitFetcher.getTraitFromList(genes,SheepGeneTrait.BRIGHTNESS);
    BaseTrait<GeneTraitMeta> override = traitFetcher.getTraitFromList(genes,SheepGeneTrait.WHITE_OVERRIDE);

    if (override == null) {
      return DyeColor.WHITE;
    }
    if (traitFetcher.getMendelian(override).getPhenotype() == MendelianType.MENDELIAN_DOMINANT) {
      return DyeColor.WHITE;
    }

    int brightnessValue = gson.fromJson(brightness.getValue(), int.class);
    if (red == null && blue == null && green == null) {
      return switch (brightnessValue) {
        case 1 -> DyeColor.LIGHT_GRAY;
        case 2 -> DyeColor.GRAY;
        case 3 -> DyeColor.BLACK;
        default -> DyeColor.WHITE;
      };
    }
    String s = formKey(red, blue, green);
    for (SheepColor value : SheepColor.values()) {
      if (value.getKey().equalsIgnoreCase(s)) {
        return value.getDyeColor();
      }
    }

    return DyeColor.BROWN;
  }

  private String formKey(BaseTrait<GeneTraitMeta> red, BaseTrait<GeneTraitMeta> blue, BaseTrait<GeneTraitMeta> green) {
    StringBuilder stringBuilder = new StringBuilder();
    if (red != null) {
      stringBuilder.append("red").append(traitFetcher.getMendelian(red).getPhenotype().getCode());
    }
    if (blue != null) {
      stringBuilder.append("blue").append(traitFetcher.getMendelian(blue).getPhenotype().getCode());
    }
    if (green != null) {
      stringBuilder.append("green").append(traitFetcher.getMendelian(green).getPhenotype().getCode());
    }
    return stringBuilder.toString();
  }

}
