package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mintychochip.mintychochip.horsepoop.config.TraitMeta;
import mintychochip.mintychochip.horsepoop.container.TypeAdapters.TraitTypeAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TraitFetcher<T extends TraitMeta> {

  private final Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Trait.class,new TraitTypeAdapter()).create();

  public Trait getTrait(String trait) {
    return gson.fromJson(trait,Trait.class);
  }
  public MendelianGene getMendelian(BaseTrait<T> trait) {
    return this.getTrait(trait.getTrait()).getValueType() == ValueType.MENDELIAN
        ? gson.fromJson(trait.getValue(), MendelianGene.class) : null;
  }

  public String toJson(Trait trait) {
    return gson.toJson(trait);
  }

  public boolean traitInList(List<BaseTrait<T>> traits, Trait trait) {
    return traits.stream().anyMatch(x-> this.getTrait(x.getTrait()) == trait);
  }

}
