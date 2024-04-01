package mintychochip.mintychochip.horsepoop.factories;

import com.google.gson.Gson;
import java.util.List;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.TraitFetcher;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianType;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.SheepGeneTrait;
import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class DyeSelector {

  private final TraitFetcher traitFetcher;

  public DyeSelector(TraitFetcher traitFetcher) {
    this.traitFetcher = traitFetcher;
  }
  public DyeColor calculateDyeColor(AnimalGenome genome, LivingEntity livingEntity) {
    if (livingEntity.getType() != EntityType.SHEEP) {
      return null;
    }
    List<Gene> genes = genome.getGenes();
    Gson gson = new Gson();
    Gene red = traitFetcher.getGeneFromGeneList(genes,SheepGeneTrait.RED);
    Gene blue = traitFetcher.getGeneFromGeneList(genes,SheepGeneTrait.BLUE);
    Gene green = traitFetcher.getGeneFromGeneList(genes,SheepGeneTrait.GREEN);
    Gene brightness = traitFetcher.getGeneFromGeneList(genes,SheepGeneTrait.BRIGHTNESS);

    Gene override = traitFetcher.getGeneFromGeneList(genes,SheepGeneTrait.WHITE_OVERRIDE);
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

  private String formKey(Gene red, Gene blue, Gene green) {
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
