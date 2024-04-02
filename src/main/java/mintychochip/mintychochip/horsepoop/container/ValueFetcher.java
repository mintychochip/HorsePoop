package mintychochip.mintychochip.horsepoop.container;

import java.util.List;
import mintychochip.mintychochip.horsepoop.config.TraitMeta;
import org.bukkit.Bukkit;

public class ValueFetcher<T extends TraitMeta> implements Fetcher<T> {

  private final TraitFetcher<T> fetcher = new TraitFetcher<>();
  public <Y extends Enum<Y>, U extends Trait> Y getEnumValue(List<BaseTrait<T>> traits, U trait, Class<Y> enumClass) {
    if(trait.getValueType() != ValueType.ENUM) {
      return null;
    }
    BaseTrait<T> traitFromList = fetcher.getTraitFromList(traits, trait);
    String value = traitFromList.getValue();
    return Enum.valueOf(enumClass,value);
  }

  @Override
  public Trait getTrait(String trait) {
    return fetcher.getTrait(trait);
  }

  @Override
  public MendelianGene getMendelian(BaseTrait<T> trait) {
    return fetcher.getMendelian(trait);
  }

  @Override
  public <U extends Trait> BaseTrait<T> getTraitFromList(List<BaseTrait<T>> baseTraits, U trait) {
    return fetcher.getTraitFromList(baseTraits,trait);
  }

  @Override
  public <U extends Trait> boolean isTraitInList(List<BaseTrait<T>> baseTraits, U trait) {
    return fetcher.isTraitInList(baseTraits,trait);
  }

  @Override
  public String toJson(Trait trait) {
    return fetcher.toJson(trait);
  }

  public TraitFetcher<T> getFetcher() {
    return fetcher;
  }
}
