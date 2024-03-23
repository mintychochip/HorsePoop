package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.*;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.CowTrait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.GeneticAttribute;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.SheepTrait;

import java.io.IOException;

public class TraitTypeAdapter extends TypeAdapter<Trait> {
    private final String value = "value";
    private final String traitType = "trait-type";

    @Override
    public void write(JsonWriter jsonWriter, Trait trait) throws IOException {
        jsonWriter.beginObject();
        TraitType type;
        if (trait instanceof SheepTrait) {
            type = TraitType.SHEEP;
        } else if (trait instanceof GeneticAttribute) {
            type = TraitType.HORSE;
        } else if (trait instanceof GenericTrait) {
            type = TraitType.GENERIC;
        } else if (trait instanceof CowTrait) {
            type = TraitType.COW;
        } else {
            throw new IOException("Something went wrong...");
        }
        jsonWriter.name(this.traitType).value(type.getKey()).name(value).value(trait.toString());
        jsonWriter.endObject();
    }

    @Override
    public Trait read(JsonReader jsonReader) throws IOException {
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
                        return SheepTrait.valueOf(value);
                    case "horse":
                        return GeneticAttribute.valueOf(value);
                    case "generic":
                        return GenericTrait.valueOf(value);
                    case "cow":
                        return CowTrait.valueOf(value);
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
