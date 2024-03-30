package mintychochip.mintychochip.horsepoop.config;

import com.google.gson.annotations.SerializedName;
import java.io.Serial;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.container.Trait;

public record AnimalTraitWrapper(@SerializedName("trait")
                                 String trait, @SerializedName("conserved") boolean conserved, @SerializedName("crossable") boolean crossable, @SerializedName("meta") AnimalTraitMeta meta){

}
