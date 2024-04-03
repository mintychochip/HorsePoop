package mintychochip.mintychochip.horsepoop.container;

import java.util.List;
import java.util.Map;

public class ValueFetcher<U extends Trait, T extends Characteristic> implements Fetcher<U, T> {

    private final TraitFetcher<U, T> fetcher = new TraitFetcher<>();

    @Override
    public <Y extends Enum<Y>> Y getEnumValue(List<BaseTrait<T>> traits, U trait, Class<Y> enumClass) {
        if (trait.getValueType() != ValueType.ENUM) {
            return null;
        }
        BaseTrait<T> traitFromList = fetcher.getTraitFromList(traits, trait);
        String value = traitFromList.getValue();
        return Enum.valueOf(enumClass, value);
    }

    @Override
    public U getTrait(String trait) {
        return fetcher.getTrait(trait);
    }

    @Override
    public MendelianGene getMendelian(BaseTrait<T> trait) {
        return fetcher.getMendelian(trait);
    }

    @Override
    public Map<U, BaseTrait<T>> getAttributes(List<BaseTrait<T>> baseTraits) {
        return fetcher.getAttributes(baseTraits);
    }

    @Override
    public BaseTrait<T> getTraitFromList(List<BaseTrait<T>> baseTraits, U trait) {
        return fetcher.getTraitFromList(baseTraits, trait);
    }

    @Override
    public BaseTrait<T> getTraitFromList(List<BaseTrait<T>> baseTraits, String traitString) {
        return fetcher.getTraitFromList(baseTraits,fetcher.getTrait(traitString));
    }

    @Override
    public boolean isTraitInList(List<BaseTrait<T>> baseTraits, U trait) {
        return fetcher.isTraitInList(baseTraits, trait);
    }

    @Override
    public String toJson(Trait trait) {
        return fetcher.toJson(trait);
    }

    public TraitFetcher<U, T> getFetcher() {
        return fetcher;
    }
}
