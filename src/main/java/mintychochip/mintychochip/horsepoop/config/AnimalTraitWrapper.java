package mintychochip.mintychochip.horsepoop.config;

import com.google.gson.annotations.SerializedName;

public record AnimalTraitWrapper<T extends Characteristic>(@SerializedName("trait")
                                 String trait, @SerializedName("meta") T meta){

}
