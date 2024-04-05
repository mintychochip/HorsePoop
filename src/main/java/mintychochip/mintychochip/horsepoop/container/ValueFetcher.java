package mintychochip.mintychochip.horsepoop.container;

import mintychochip.mintychochip.horsepoop.api.Fetcher;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.metas.EnumMeta;
import mintychochip.mintychochip.horsepoop.metas.MetaType;

import java.util.List;
import java.util.Map;

public class ValueFetcher<U extends TraitEnum> implements Fetcher<U> {

    private final TraitFetcher<U> fetcher = new TraitFetcher<>();

    @Override
    public <Y extends Enum<Y>> Y getEnumValue(List<BaseTrait<U>> baseTraits, U trait) {
        if (!(trait.getMetaType() == MetaType.WEIGHTED_ENUM || trait.getMetaType() == MetaType.ENUM)) {
            return null;
        }
        BaseTrait<U> traitFromList = fetcher.getTraitFromList(baseTraits, trait);
        EnumMeta<U> meta = (EnumMeta<U>) traitFromList.getMeta();
        String enumClassString = meta.getEnumClass();
        if (enumClassString == null) {
            return null;
        }
        String value = traitFromList.getValue();
        if (value == null) {
            return null;
        }
        try {
            Class<?> aClass = Class.forName(enumClassString);
            if (!Enum.class.isAssignableFrom(aClass)) {
                Class<Y> enumType = (Class<Y>) aClass;
                return Enum.valueOf(enumType, value);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public MendelianGene getMendelian(BaseTrait<U> trait) {
        return fetcher.getMendelian(trait);
    }

    @Override
    public Map<U, BaseTrait<U>> getAttributes(List<BaseTrait<U>> baseTraits) {
        return fetcher.getAttributes(baseTraits);
    }

    @Override
    public BaseTrait<U> getTraitFromList(List<BaseTrait<U>> baseTraits, U trait) {
        return fetcher.getTraitFromList(baseTraits, trait);
    }

    @Override
    public boolean isTraitInList(List<BaseTrait<U>> baseTraits, U trait) {
        return fetcher.isTraitInList(baseTraits, trait);
    }

    @Override
    public String toJson(TraitEnum traitEnum) {
        return null;
    }


}
