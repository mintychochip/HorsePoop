package mintychochip.mintychochip.horsepoop.container.TypeAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import mintychochip.mintychochip.horsepoop.config.AnimalTraitWrapper;

public class TraitMetaAdapter<T extends Characteristic> extends TypeAdapter<AnimalTraitWrapper<T>> {

  private final Class<T> aClass;

  @SuppressWarnings("unchecked")
  public TraitMetaAdapter(Class<T> aClass) {
    this.aClass = aClass;
  }

  @Override
  public void write(JsonWriter jsonWriter, AnimalTraitWrapper<T> tAnimalTraitWrapper)
      throws IOException {

  }

  @Override
  public AnimalTraitWrapper<T> read(JsonReader jsonReader) throws IOException {
    jsonReader.beginObject();

    String type = null;

    boolean conserved = false;
    Boolean crossable = false;

    double chance = 0;
    double max = 0;
    double min = 0;

    String trait = null;
    while (jsonReader.hasNext()) {
      String name = jsonReader.nextName();
      if (name.equals("trait")) {
        trait = jsonReader.nextString();
      }
      if (name.equals("meta")) {
        jsonReader.beginObject();
        break;
      }
    }
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
    jsonReader.endObject();

    T meta;
    if (type == null) {
      return null;
    }
    if (type.equalsIgnoreCase("gene")) {
      meta = (T) new GeneTraitMeta(conserved, crossable, chance, max, min, type);
    } else {
      meta = (T) new CharacteristicTraitMeta(max, min, type);
    }
    if (aClass.isInstance(meta)) {
      return new AnimalTraitWrapper<>(trait, meta);
    } else {
      return null;
    }
  }
}
