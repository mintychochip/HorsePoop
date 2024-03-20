package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import mintychochip.mintychochip.horsepoop.container.attributes.GeneticAttribute;
import mintychochip.mintychochip.horsepoop.container.attributes.SheepAttribute;
import org.bukkit.Bukkit;

import java.io.IOException;

public class TraitTypeAdapter extends TypeAdapter<Trait> {
    @Override
    public void write(JsonWriter jsonWriter, Trait trait) throws IOException {
        jsonWriter.beginObject();
        if (trait instanceof SheepAttribute sheepAttribute) {
            jsonWriter.name("trait-type").value("sheep").name("value").value(sheepAttribute.toString());
        } else if (trait instanceof GeneticAttribute geneticAttribute) {
            jsonWriter.name("trait-type").value("genetic").name("value").value(geneticAttribute.toString());
        }
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
                        return SheepAttribute.valueOf(value);
                    case "genetic":
                        return GeneticAttribute.valueOf(value);
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
