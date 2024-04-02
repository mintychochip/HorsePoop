package mintychochip.mintychochip.horsepoop.container;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import mintychochip.mintychochip.horsepoop.config.TraitMeta;
import mintychochip.mintychochip.horsepoop.container.TypeAdapters.TraitTypeAdapter;

public class TraitFetcher<T extends TraitMeta> {

  private final Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Trait.class,
      new TraitTypeAdapter()).create();

  public Trait getTrait(String trait) {
    return gson.fromJson(trait, Trait.class);
  }
  public MendelianGene getMendelian(BaseTrait<T> trait) {
    return this.getTrait(trait.getTrait()).getValueType() == ValueType.MENDELIAN
        ? gson.fromJson(trait.getValue(), MendelianGene.class) : null;
  }
  public <U extends Trait> BaseTrait<T> getTraitFromList(List<BaseTrait<T>> traits, U trait) {
    return traits.stream().filter(x -> this.getTrait(x.getTrait()) == trait).findFirst()
        .orElse(null);
  }
  public <U extends Trait> boolean isTraitInList(List<BaseTrait<T>> traits, U trait) {
    return traits.stream().anyMatch(x -> this.getTrait(x.getTrait()) == trait);
  }
  public String toJson(Trait trait) {
    return gson.toJson(trait);
  }

}
