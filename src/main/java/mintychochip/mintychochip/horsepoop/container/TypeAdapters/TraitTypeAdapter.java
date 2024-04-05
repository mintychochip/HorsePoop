package mintychochip.mintychochip.horsepoop.container.TypeAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import mintychochip.mintychochip.horsepoop.api.Phenotypic;
import mintychochip.mintychochip.horsepoop.api.Gene;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.*;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.CowGene;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.GeneticAttribute;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.SheepGene;

import java.io.IOException;
import mintychochip.mintychochip.horsepoop.container.enums.characteristics.GenericCharacteristicTraitEnum;

public class TraitTypeAdapter<U extends TraitEnum> extends TypeAdapter<U> {
  private final String value = "value";
  private final String traitType = "trait-type"; //gene or characteristic for now, dependent on the class
  @Override
  public void write(JsonWriter jsonWriter, TraitEnum traitEnum) throws IOException {
    jsonWriter.beginObject();
    String type = null;
    if(traitEnum instanceof Gene gene) {
      if(gene instanceof SheepGene) {
        type = "sheep";
      } else if(gene instanceof CowGene) {
        type = "cow";
      } else if(gene instanceof GeneticAttribute geneticAttribute) {
        type = "horse";
      } else if(gene instanceof GenericGene genericGeneTrait) {
        type = "generic";
      }
    } else if(traitEnum instanceof Phenotypic characteristicTrait) {
      type = "char";
    }
    jsonWriter.name(this.traitType).value(type).name(value).value(traitEnum.toString());
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
            return SheepGene.valueOf(value);
          case "horse":
            return GeneticAttribute.valueOf(value);
          case "generic":
            return GenericGene.valueOf(value);
          case "cow":
            return CowGene.valueOf(value);
          case "char":
            return GenericCharacteristicTraitEnum.valueOf(value);
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
