package mintychochip.mintychochip.horsepoop.factories;

import com.google.gson.Gson;
import java.util.List;

import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.api.Fetcher;
import mintychochip.mintychochip.horsepoop.api.Gene;
import mintychochip.mintychochip.horsepoop.container.ValueFetcher;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianType;
import mintychochip.mintychochip.horsepoop.container.enums.traits.SheepGene;
import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class DyeSelector {

  private final Fetcher<Gene> fetcher = new ValueFetcher<>();
  public DyeColor calculateDyeColor(AnimalGenome genome, LivingEntity livingEntity) {
    if (livingEntity.getType() != EntityType.SHEEP) {
      return null;
    }
    List<BaseTrait<Gene>> genes = genome.getGenes();
    Gson gson = new Gson();

    BaseTrait<Gene> red = fetcher.getTraitFromList(genes, SheepGene.RED);
    BaseTrait<Gene> blue = fetcher.getTraitFromList(genes, SheepGene.BLUE);
    BaseTrait<Gene> green = fetcher.getTraitFromList(genes, SheepGene.GREEN);
    BaseTrait<Gene> brightness = fetcher.getTraitFromList(genes, SheepGene.BRIGHTNESS);
    BaseTrait<Gene> override = fetcher.getTraitFromList(genes, SheepGene.WHITE_OVERRIDE);

    if (override == null) {
      return DyeColor.WHITE;
    }
    if (fetcher.getMendelian(override).getPhenotype() == MendelianType.MENDELIAN_DOMINANT) {
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

  private String formKey(BaseTrait<Gene> red, BaseTrait<Gene> blue, BaseTrait<Gene> green) {
    StringBuilder stringBuilder = new StringBuilder();
    if (red != null) {
      stringBuilder.append("red").append(fetcher.getMendelian(red).getPhenotype().getCode());
    }
    if (blue != null) {
      stringBuilder.append("blue").append(fetcher.getMendelian(blue).getPhenotype().getCode());
    }
    if (green != null) {
      stringBuilder.append("green").append(fetcher.getMendelian(green).getPhenotype().getCode());
    }
    return stringBuilder.toString();
  }

}
