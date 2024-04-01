package mintychochip.mintychochip.horsepoop.container.TypeAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import mintychochip.mintychochip.horsepoop.config.CharacteristicTraitMeta;
import mintychochip.mintychochip.horsepoop.config.GeneTraitMeta;
import mintychochip.mintychochip.horsepoop.config.TraitMeta;

public class TraitMetaAdapter extends TypeAdapter<TraitMeta> {

  private Map<String, Class<? extends TraitMeta>> traitMap = new HashMap<>();

  @Override
  public void write(JsonWriter jsonWriter, TraitMeta traitMeta) throws IOException {

  }

  @Override
  public TraitMeta read(JsonReader jsonReader) throws IOException {
    jsonReader.beginObject();

    String type = null;

    boolean conserved = false;
    Boolean crossable = false;

    double chance = 0;
    double max = 0;
    double min = 0;

    while (jsonReader.hasNext()) {
      String name = jsonReader.nextName();
      switch (name) {
        case "chance" -> {
          chance = jsonReader.nextDouble();
        }
        case "conserved" -> {
          conserved = jsonReader.nextBoolean();
        }
        case "crossable" -> {
          crossable = jsonReader.nextBoolean();
        }
        case "max" -> {
          max = jsonReader.nextDouble();
        }
        case "min" -> {
          min = jsonReader.nextDouble();
        }
        case "type" -> {
          type = jsonReader.nextString();
        }
      }
    }
    jsonReader.endObject();

    if (type == null) {
      return null;
    }

    if (type.equalsIgnoreCase("gene")) {
      return new GeneTraitMeta(conserved, crossable, chance, max, min, type);
    }
    return new CharacteristicTraitMeta(max, min, type);
  }
}
