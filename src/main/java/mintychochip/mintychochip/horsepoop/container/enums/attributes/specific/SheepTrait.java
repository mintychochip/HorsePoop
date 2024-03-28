package mintychochip.mintychochip.horsepoop.container.enums.attributes.specific;

import com.google.gson.Gson;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianType;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.TraitType;
import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public enum SheepTrait implements Trait { //traits designed for sheep
    RED("red"),
    BLUE("blue"),
    GREEN("green"),
    WHITE_OVERRIDE("override"), //dominant phenotypes always exhibit white, allows for masking of colors
    BRIGHTNESS("brightness");

    // numeric
    private final String key;

    SheepTrait(String key) {
        this.key = key;
    }
    private enum SheepColor { //temporary
        PINK("red0", DyeColor.PINK),
        RED("red1",DyeColor.RED),
        GREEN("green1",DyeColor.GREEN),
        BLUE("blue1",DyeColor.BLUE),
        LIGHT_BLUE("blue0",DyeColor.LIGHT_BLUE),
        LIME("green0",DyeColor.LIME),
        ORANGE("red1green0",DyeColor.ORANGE),
        ORANGE2("red0green1", DyeColor.ORANGE),
        ORANGE3("red1green1", DyeColor.ORANGE),
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
        Gene red = genome.getGeneFromTrait(SheepTrait.RED);
        Gene blue = genome.getGeneFromTrait(SheepTrait.BLUE);
        Gene green = genome.getGeneFromTrait(SheepTrait.GREEN);
        Gene brightness = genome.getGeneFromTrait(SheepTrait.BRIGHTNESS);

        Gene override = genome.getGeneFromTrait(SheepTrait.WHITE_OVERRIDE);
        if(override == null) {
            return DyeColor.WHITE;
        }

        if (override.getMendelian().getPhenotype() == MendelianType.MENDELIAN_DOMINANT) {
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
            if(value.getKey().equalsIgnoreCase(s)) {
                return value.getDyeColor();
            }
        }

        return DyeColor.BROWN;
    }

    private static String formKey(Gene red, Gene blue, Gene green) {
        StringBuilder stringBuilder = new StringBuilder();
        if (red != null) {
            stringBuilder.append("red").append(red.getMendelian().getPhenotype().getCode());
        }
        if (blue != null) {
            stringBuilder.append("blue").append(blue.getMendelian().getPhenotype().getCode());
        }
        if (green != null) {
            stringBuilder.append("green").append(green.getMendelian().getPhenotype().getCode());
        }
        return stringBuilder.toString();
    }

    @Override
    public String getNamespacedKey() {
        return TraitType.SHEEP.getKey() + ":" + key;
    }

    @Override
    public String getKey() {
        return key;
    }
}
