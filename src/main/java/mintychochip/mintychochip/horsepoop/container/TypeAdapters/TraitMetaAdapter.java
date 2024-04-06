package mintychochip.mintychochip.horsepoop.container.TypeAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
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
import org.bukkit.Bukkit;

public class TraitMetaAdapter<U extends TraitEnum> extends TypeAdapter<Meta<U>> {


  private final TraitConfig<U> config;

  @SuppressWarnings("unchecked")
  public TraitMetaAdapter(TraitConfig<U> config) {
    this.config = config;
  }

  @Override
  public void write(JsonWriter jsonWriter, Meta<U> uMeta) throws IOException {
    jsonWriter.beginObject();

    if (uMeta == null) {
      jsonWriter.endObject();
      return;
    }

    jsonWriter.name("trait").value(uMeta.getTrait().getKey());

    switch (uMeta.getTrait().getMetaType()) {
      case MENDELIAN: {
        MendelianMeta<U> mendelianMeta = (MendelianMeta<U>) uMeta;
        jsonWriter.name("conserved").value(mendelianMeta.isConserved());
        jsonWriter.name("black-list").beginArray();
        for (String item : mendelianMeta.getBlacklist()) {
          jsonWriter.value(item);
        }
        jsonWriter.endArray();
        break;
      }
      case INTEGER: {
        IntegerMeta<U> integerMeta = (IntegerMeta<U>) uMeta;
        jsonWriter.name("max").value(integerMeta.getMax());
        jsonWriter.name("min").value(integerMeta.getMin());
        break;
      }
      case WEIGHTED_ENUM: {
        WeightedEnumMeta<U> weightedEnumMeta = (WeightedEnumMeta<U>) uMeta;
        // Write logic for weighted enum meta
        break;
      }
      case DOUBLE, CROSSABLE_DOUBLE: {
        DoubleMeta<U> doubleMeta = (DoubleMeta<U>) uMeta;
        jsonWriter.name("max").value(doubleMeta.getMax());
        jsonWriter.name("min").value(doubleMeta.getMin());
        break;
      }
      case ENUM: {
        EnumMeta<U> enumMeta = (EnumMeta<U>) uMeta;
        jsonWriter.name("enum-class").value(enumMeta.getEnumClass());
        break;
      }
      case CROSSABLE_INTEGER: {
        CrossableIntegerMeta<U> crossableIntegerMeta = (CrossableIntegerMeta<U>) uMeta;
        // Write logic for crossable integer meta
        break;
      }
      case CROSSABLE_MENDELIAN: {
        CrossableMendelianMeta<U> crossableMendelianMeta = (CrossableMendelianMeta<U>) uMeta;
        // Write logic for crossable mendelian meta
        break;
      }
      case POLYGENIC_MENDELIAN: {
        PolygenicMendelianMeta<U> polygenicMendelianMeta = (PolygenicMendelianMeta<U>) uMeta;
        // Write logic for polygenic mendelian meta
        break;
      }
    }

    jsonWriter.endObject(); // End main object
  }

  @Override
  public Meta<U> read(JsonReader jsonReader) throws IOException {
    jsonReader.beginObject();

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
      String metaName = jsonReader.nextName();
      switch (metaName) {
        case "trait":
          trait = config.getTraitFromString(jsonReader.nextString());
          break;
        case "chance":
          chance = jsonReader.nextDouble();
          break;
        case "conserved":
          conserved = jsonReader.nextBoolean();
          break;
        case "crossable":
          crossable = jsonReader.nextBoolean();
          break;
        case "max":
          max = jsonReader.nextDouble();
          break;
        case "min":
          min = jsonReader.nextDouble();
          break;
        case "enum-class":
          enumClass = jsonReader.nextString();
          break;
        case "black-list":
          jsonReader.beginArray();
          while (jsonReader.hasNext()) {
            String value = jsonReader.nextString();
            blacklist.add(value);
          }
          jsonReader.endArray();
          break;
        case "weights":
          jsonReader.beginArray();
          while (jsonReader.hasNext()) {
            double value = jsonReader.nextDouble();
            weights.add(value);
          }
          jsonReader.endArray();
          break;
      }
    }

    jsonReader.endObject();

    Meta<U> meta = null;
    if (trait != null) {
      MetaType metaType = trait.getMetaType();
      switch (metaType) {
        case MENDELIAN:
          meta = new MendelianMeta<>(trait, chance, blacklist);
          break;
        case INTEGER:
          meta = new IntegerMeta<>(trait, (int) max, (int) min);
          break;
        case WEIGHTED_ENUM:
          meta = new WeightedEnumMeta<>(trait, enumClass, weights);
          break;
        case DOUBLE:
          meta = new DoubleMeta<>(trait, max, min);
          break;
        case ENUM:
          meta = new EnumMeta<>(trait, enumClass);
          break;
        case CROSSABLE_DOUBLE:
          meta = new CrossableDoubleMeta<>(trait, max, min);
          break;
        case CROSSABLE_INTEGER:
          meta = new CrossableIntegerMeta<>(trait, (int) max, (int) min);
          break;
        case CROSSABLE_MENDELIAN:
          meta = new CrossableMendelianMeta<>(trait, chance, blacklist);
          break;
        case POLYGENIC_MENDELIAN:
          meta = new PolygenicMendelianMeta<>(trait, chance, blacklist);
          break;
      }
      if (meta != null) {
        meta.setConserved(conserved);
        if (meta instanceof Crossable) {
          ((Crossable) meta).setCrossable(crossable);
        }
      }
    }
    return meta;
  }
}

