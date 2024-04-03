package mintychochip.mintychochip.horsepoop.config;

import com.google.gson.annotations.SerializedName;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.metas.Meta;

public record AnimalTraitWrapper<U extends Trait>(@SerializedName("trait")
                                 String trait, @SerializedName("meta") Meta<U> meta){

}
