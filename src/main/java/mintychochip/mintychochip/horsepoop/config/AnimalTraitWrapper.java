package mintychochip.mintychochip.horsepoop.config;

import com.google.gson.annotations.SerializedName;

public record AnimalTraitWrapper(@SerializedName("trait")
                                 String trait, @SerializedName("meta") TraitMeta meta){

}
