package mintychochip.mintychochip.horsepoop.container.TypeAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianAllele;
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
import mintychochip.mintychochip.horsepoop.metas.Polygenic;
import mintychochip.mintychochip.horsepoop.metas.PolygenicMendelianMeta;
import mintychochip.mintychochip.horsepoop.metas.Units;
import mintychochip.mintychochip.horsepoop.metas.WeightedEnumMeta;
import mintychochip.mintychochip.horsepoop.util.Unit;
import org.bukkit.Bukkit;

public class TraitMetaAdapter<U extends TraitEnum> extends TypeAdapter<Meta<U>> {
  private final TraitConfig<U> config;
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
    if(uMeta instanceof Units units) {
      if(units.getUnit() != null) {
        jsonWriter.name("units").value(units.getUnit().toString());
      }
    }
    if(uMeta instanceof Crossable crossable) {
      jsonWriter.name("crossable").value(crossable.isCrossable());
    }
    if(uMeta instanceof Polygenic<?>) {
      Polygenic<U> poly = (Polygenic<U>) uMeta;
      Map<U, MendelianGene> states = poly.getStates();
      jsonWriter.name("trait-enums").beginArray();
      for (U traitEnum : states.keySet()) {
        jsonWriter.value(traitEnum.getKey());
      }
      jsonWriter.endArray();
      jsonWriter.name("states").beginArray();
      for (MendelianGene value : states.values()) {
        jsonWriter.value(value.getAlleleA().toString() + "/" + value.getAlleleB().toString());
      }
      jsonWriter.endArray();

    }
    switch (uMeta.getTrait().getMetaType()) {
      case MENDELIAN, CROSSABLE_MENDELIAN, POLYGENIC_MENDELIAN: {
        MendelianMeta<U> mendelianMeta = (MendelianMeta<U>) uMeta;
        jsonWriter.name("conserved").value(mendelianMeta.isConserved());
        jsonWriter.name("black-list").beginArray();
        for (String item : mendelianMeta.getBlacklist()) {
          jsonWriter.value(item);
        }
        jsonWriter.endArray();
        break;
      }
      case INTEGER, CROSSABLE_INTEGER: {
        IntegerMeta<U> integerMeta = (IntegerMeta<U>) uMeta;
        jsonWriter.name("max").value(integerMeta.getMax());
        jsonWriter.name("min").value(integerMeta.getMin());
        break;
      }
      case WEIGHTED_ENUM: {
        WeightedEnumMeta<U> weightedEnumMeta = (WeightedEnumMeta<U>) uMeta;
        jsonWriter.name("enum-class").value(weightedEnumMeta.getEnumClass());
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
    Set<U> traitEnums = new HashSet<>();
    Set<MendelianGene> states = new HashSet<>();
    U trait = null;
    Unit unit = null;
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
        case "units":
          unit = Enum.valueOf(Unit.class, jsonReader.nextString().toUpperCase());
          break;
        case "trait-enums":
          jsonReader.beginArray();
          while(jsonReader.hasNext()) {
            U traitFromString = config.getTraitFromString(jsonReader.nextString());
            if(traitFromString != null) {
              traitEnums.add(traitFromString);
            }
          }
          jsonReader.endArray();
          break;
        case "states":
          jsonReader.beginArray();
          while(jsonReader.hasNext()) {
            String[] split = jsonReader.nextString().split("/");
            if(split.length == 2) {
              states.add(new MendelianGene(this.getMendelianFromString(split[0]),
                  this.getMendelianFromString(split[1])));
            }
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
        if(meta instanceof Units) {
          if(unit != null) {
            ((Units) meta).setUnits(unit);
          }
        }
        if(meta instanceof Polygenic<?> poly) {
          Polygenic<U> polygenic = (Polygenic<U>) poly;
          polygenic.setTraitStates(traitEnums,states);
        }
      }
    }
    return meta;
  }
  private MendelianAllele getMendelianFromString(String allele) {
    return allele.equals("DOM") ? MendelianAllele.DOMINANT : MendelianAllele.RECESSIVE;
  }
}

