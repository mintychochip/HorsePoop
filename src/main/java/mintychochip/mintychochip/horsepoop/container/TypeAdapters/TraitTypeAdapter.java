package mintychochip.mintychochip.horsepoop.container.TypeAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import mintychochip.mintychochip.horsepoop.container.CharacteristicTrait;
import mintychochip.mintychochip.horsepoop.container.GeneTrait;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.*;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.CowGeneTrait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.GeneticAttribute;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.SheepGeneTrait;

import java.io.IOException;
import mintychochip.mintychochip.horsepoop.container.enums.characteristics.GenericCharacteristicTrait;
import org.bukkit.Bukkit;
import org.bukkit.entity.Sheep;

public class TraitTypeAdapter<U extends Trait> extends TypeAdapter<U> {
  private final String value = "value";
  private final String traitType = "trait-type"; //gene or characteristic for now, dependent on the class
  @Override
  public void write(JsonWriter jsonWriter, Trait trait) throws IOException {
    jsonWriter.beginObject();
    String type = null;
    if(trait instanceof GeneTrait geneTrait) {
      if(geneTrait instanceof SheepGeneTrait) {
        type = "sheep";
      } else if(geneTrait instanceof CowGeneTrait) {
        type = "cow";
      } else if(geneTrait instanceof GeneticAttribute geneticAttribute) {
        type = "horse";
      } else if(geneTrait instanceof GenericGeneTrait genericGeneTrait) {
        type = "generic";
      }
    } else if(trait instanceof CharacteristicTrait characteristicTrait) {
      type = "char";
    }
    jsonWriter.name(this.traitType).value(type).name(value).value(trait.toString());
    jsonWriter.endObject();
  }
  @Override
  public U read(JsonReader jsonReader) throws IOException { //fix this later
    jsonReader.beginObject();
    String traitType = null;
    String value = null;
    while (jsonReader.hasNext()) {
      String name = jsonReader.nextName();
      if (name.equals("trait-type")) {
        traitType = jsonReader.nextString();
      } else if (name.equals("value")) {
        value = jsonReader.nextString();
      } else {
        // Skip unexpected properties
        jsonReader.skipValue();
      }
    }
    jsonReader.endObject();

    if (traitType != null && value != null) {
      try {
        switch (traitType) {
          case "sheep":
            return SheepGeneTrait.valueOf(value);
          case "horse":
            return GeneticAttribute.valueOf(value);
          case "generic":
            return GenericGeneTrait.valueOf(value);
          case "cow":
            return CowGeneTrait.valueOf(value);
          case "char":
            return GenericCharacteristicTrait.valueOf(value);
          default:
            // Handle unknown trait types
            throw new IllegalArgumentException("Unknown trait type: " + traitType);
        }
      } catch (IllegalArgumentException e) {
        // Handle invalid enum values
        throw new IOException("Invalid enum value for " + traitType + ": " + value, e);
      }
    } else {
      // Handle missing or incomplete data
      throw new IOException("Missing trait type or value in JSON");
    }
  }


}
