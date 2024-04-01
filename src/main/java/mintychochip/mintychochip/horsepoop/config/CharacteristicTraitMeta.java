package mintychochip.mintychochip.horsepoop.config;

import com.google.gson.annotations.SerializedName;

public record CharacteristicTraitMeta(double maximum, double minimum, String type) implements
    TraitMeta {
}
