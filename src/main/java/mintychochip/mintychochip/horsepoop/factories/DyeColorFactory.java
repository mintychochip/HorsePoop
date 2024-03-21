package mintychochip.mintychochip.horsepoop.factories;

import com.google.gson.Gson;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.attributes.SheepAttribute;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianType;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class DyeColorFactory {

    private enum SheepColor { //temporary
        PINK("red0",DyeColor.PINK),
        RED("red1",DyeColor.RED),
        GREEN("green1",DyeColor.GREEN),
        BLUE("blue1",DyeColor.BLUE),
        LIGHT_BLUE("blue0",DyeColor.LIGHT_BLUE),
        LIME("green0",DyeColor.LIME),
        ORANGE("red1green0",DyeColor.ORANGE),
        CYAN("blue1green1",DyeColor.CYAN),
        CYAN2("blue1green0",DyeColor.CYAN),
        PURPLE("red0blue0",DyeColor.PURPLE),
        PURPLE2("red0blue1",DyeColor.PURPLE),
        MAGENTA("red1blue1",DyeColor.MAGENTA),
        MAGENTA2("red1blue0",DyeColor.MAGENTA);

        private final String key;

        private final DyeColor dyeColor;
        SheepColor(String key, DyeColor dyeColor) {
            this.key = key;
            this.dyeColor = dyeColor;
        }

        public String getKey() {
            return key;
        }

        public DyeColor getDyeColor() {
            return dyeColor;
        }
    }

    public static DyeColor calculateDyeColor(AnimalGenome genome, LivingEntity livingEntity) {
        if (livingEntity.getType() != EntityType.SHEEP) {
            return null;
        }
        Gson gson = new Gson();
        Gene red = genome.getGeneFromTrait(SheepAttribute.RED);
        Gene blue = genome.getGeneFromTrait(SheepAttribute.BLUE);
        Gene green = genome.getGeneFromTrait(SheepAttribute.GREEN);
        Gene brightness = genome.getGeneFromTrait(SheepAttribute.BRIGHTNESS);

        Gene override = genome.getGeneFromTrait(SheepAttribute.WHITE_OVERRIDE);
        if(override == null) {
            return DyeColor.WHITE;
        }
        if (override.getPhenotype() == MendelianType.MENDELIAN_DOMINANT) {
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
        String s = formKey(red, blue, green, brightnessValue);
        for (SheepColor value : SheepColor.values()) {
            if(value.getKey().equalsIgnoreCase(s)) {
                return value.getDyeColor();
            }
        }

        return DyeColor.BROWN;
    }

    private static String formKey(Gene red, Gene blue, Gene green, int brightnessValue) {
        StringBuilder stringBuilder = new StringBuilder();
        if (red != null) {
            stringBuilder.append("red").append(red.getPhenotype().getCode());
        }
        if (blue != null) {
            stringBuilder.append("blue").append(blue.getPhenotype().getCode());
        }
        if (green != null) {
            stringBuilder.append("green").append(green.getPhenotype().getCode());
        }
        return stringBuilder.toString();
    }
}
