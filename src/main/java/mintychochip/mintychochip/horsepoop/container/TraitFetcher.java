package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;
import mintychochip.mintychochip.horsepoop.container.TypeAdapters.TraitTypeAdapter;
import mintychochip.mintychochip.horsepoop.metas.Meta;

public class TraitFetcher<U extends Trait, T extends Meta> {

  public Map<U, BaseTrait<T>> getAttributes(List<BaseTrait<T>> baseTraits) {
    Map<U, BaseTrait<T>> attributes = new HashMap<>();
    for (BaseTrait<T> baseTrait : baseTraits) {
      attributes.put(this.getTrait(baseTrait.getTrait()),baseTrait);
    }
    return attributes;
  }
  private final Gson gson = new GsonBuilder().registerTypeAdapter(Trait.class,
      new TraitTypeAdapter<U>()).create();

  public U getTrait(String trait) {
    return gson.fromJson(trait, new TypeToken<U>(){}.getType());
  }
  public MendelianGene getMendelian(BaseTrait<T> trait) {
    return this.getTrait(trait.getTrait()).getValueType() == ValueType.MENDELIAN
        ? gson.fromJson(trait.getValue(), MendelianGene.class) : null;
  }
  public BaseTrait<T> getTraitFromList(List<BaseTrait<T>> traits, U trait) {
    return traits.stream().filter(x -> this.getTrait(x.getTrait()) == trait).findFirst()
        .orElse(null);
  }
  public boolean isTraitInList(List<BaseTrait<T>> traits, U trait) {
    return traits.stream().anyMatch(x -> this.getTrait(x.getTrait()) == trait);
  }
  public String toJson(Trait trait) {
    return gson.toJson(trait);
  }

}
