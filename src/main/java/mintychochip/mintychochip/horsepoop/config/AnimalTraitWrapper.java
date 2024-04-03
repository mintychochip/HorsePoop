package mintychochip.mintychochip.horsepoop.config;

import com.google.gson.annotations.SerializedName;

public record AnimalTraitWrapper<T>(@SerializedName("trait")
                                 String trait, @SerializedName("meta") T meta){

}
