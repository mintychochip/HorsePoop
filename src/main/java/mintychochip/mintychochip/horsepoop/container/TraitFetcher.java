package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;
import mintychochip.mintychochip.horsepoop.container.TypeAdapters.TraitTypeAdapter;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import mintychochip.mintychochip.horsepoop.metas.MetaType;

public class TraitFetcher<U extends Trait> {

  public Map<U, BaseTrait<U>> getAttributes(List<BaseTrait<U>> baseTraits) {
    Map<U, BaseTrait<U>> attributes = new HashMap<>();
    for (BaseTrait<U> baseTrait : baseTraits) {
      attributes.put(baseTrait.getMeta().getTrait(),baseTrait);
    }
    return attributes;
  }
  private final Gson gson = new GsonBuilder().registerTypeAdapter(Trait.class,
      new TraitTypeAdapter<U>()).create();
  public MendelianGene getMendelian(BaseTrait<U> trait) {
    MetaType metaType = trait.getMeta().getTrait().getMetaType();
    return metaType == MetaType.MENDELIAN || metaType == MetaType.CROSSABLE_MENDELIAN ? gson.fromJson(trait.getValue(), MendelianGene.class) : null;
  }
  public BaseTrait<U> getTraitFromList(List<BaseTrait<U>> traits, U trait) {
    return traits.stream().filter(x -> x.getMeta().getTrait() == trait).findFirst().orElse(null);
  }
  public boolean isTraitInList(List<BaseTrait<U>> traits, U trait) {
    return traits.stream().anyMatch(x -> x.getMeta().getTrait() == trait);
  }
  public String toJson(Trait trait) {
    return gson.toJson(trait);
  }

}
