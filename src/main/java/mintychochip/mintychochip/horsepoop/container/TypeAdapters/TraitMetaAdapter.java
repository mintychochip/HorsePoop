package mintychochip.mintychochip.horsepoop.container.TypeAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.metas.Conserved;
import mintychochip.mintychochip.horsepoop.metas.Crossable;
import mintychochip.mintychochip.horsepoop.metas.CrossableDoubleMeta;
import mintychochip.mintychochip.horsepoop.metas.CrossableIntegerMeta;
import mintychochip.mintychochip.horsepoop.metas.CrossableMendelianMeta;
import mintychochip.mintychochip.horsepoop.metas.DoubleMeta;
import mintychochip.mintychochip.horsepoop.metas.EnumMeta;
import mintychochip.mintychochip.horsepoop.metas.IntegerMeta;
import mintychochip.mintychochip.horsepoop.metas.MendelianMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import mintychochip.mintychochip.horsepoop.metas.MetaType;
import mintychochip.mintychochip.horsepoop.metas.PolygenicMendelianMeta;
import mintychochip.mintychochip.horsepoop.metas.WeightedEnumMeta;

public class TraitMetaAdapter<U extends Trait> extends TypeAdapter<Meta<U>> {


  private final TraitConfig<U> config;

  @SuppressWarnings("unchecked")
  public TraitMetaAdapter(TraitConfig<U> config) {
    this.config = config;
  }
  @Override
  public void write(JsonWriter jsonWriter, Meta<U> uMeta) throws IOException {

  }

  @Override
  public Meta<U> read(JsonReader jsonReader) throws IOException {
    jsonReader.beginObject();

    String type = null;
    String enumClass = null;

    boolean conserved = false;
    Boolean crossable = false;

    double chance = 0;
    double max = 0;
    double min = 0;
    List<String> blacklist = new ArrayList<>();
    List<Double> weights = new ArrayList<>();
    U trait = null;
    while (jsonReader.hasNext()) {
      String name = jsonReader.nextName();
      if (name.equals("trait")) {
        trait = config.getTraitFromString(jsonReader.nextString());
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
        case "enum-class" -> {
          enumClass = jsonReader.nextString();
        }
        case "black-list" -> {
          jsonReader.beginArray(); // Begin reading the array
          while (jsonReader.hasNext()) {
            String value = jsonReader.nextString(); // Read each string value
            blacklist.add(value); // Add the string to the list
          }
          jsonReader.endArray(); // End reading the array
        }
        case "weights" -> {
          jsonReader.beginArray();
          while (jsonReader.hasNext()) {
            double value = jsonReader.nextDouble();
            weights.add(value);
          }
          jsonReader.endArray();
        }
      }
    }
    jsonReader.endObject();
    jsonReader.endObject();

    Meta<U> meta;
    if(trait == null) {
      return null;
    }
    MetaType metaType = trait.getMetaType();
    meta = switch (metaType) {
      case MENDELIAN -> new MendelianMeta<>(trait, chance, blacklist);
      case INTEGER -> new IntegerMeta<>(trait, (int) max, (int) min);
      case WEIGHTED_ENUM ->  new WeightedEnumMeta<>(trait, enumClass, weights);
      case DOUBLE -> new DoubleMeta<>(trait,max,min);
      case ENUM -> new EnumMeta<>(trait,enumClass);
      case CROSSABLE_DOUBLE -> new CrossableDoubleMeta<>(trait,max,min);
      case CROSSABLE_INTEGER -> new CrossableIntegerMeta<>(trait,(int) max, (int) min);
      case CROSSABLE_MENDELIAN -> new CrossableMendelianMeta<>(trait,chance,blacklist);
      case POLYGENIC_MENDELIAN -> new PolygenicMendelianMeta<>(trait,chance,blacklist);
    };
    Conserved meta1 = (Conserved) meta;
    meta1.setConserved(conserved);

    if(meta instanceof Crossable c) {
      c.setCrossable(crossable);
    }
    return meta;
  }
}
