package mintychochip.mintychochip.horsepoop.container;

import java.util.List;
import java.util.Map;
import mintychochip.mintychochip.horsepoop.api.Fetcher;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.metas.EnumMeta;
import mintychochip.mintychochip.horsepoop.metas.MetaType;
import mintychochip.mintychochip.horsepoop.metas.Numeric;

public class ValueFetcher<U extends TraitEnum> implements Fetcher<U> {

    private final TraitFetcher<U> fetcher = new TraitFetcher<>();

    @Override
    public <Y extends Enum<Y>> Y getEnumValue(List<BaseTrait<U>> baseTraits, U trait, Class<Y> enumClass) {
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
        return Enum.valueOf(enumClass,value);
    }

    @Override
    public double getNumeric(List<BaseTrait<U>> baseTraits, U trait) {
        BaseTrait<U> traitFromList = this.getTraitFromList(baseTraits, trait);
        if(traitFromList == null || !(traitFromList.getMeta() instanceof Numeric)) {
            return 0;
        }
        String value = traitFromList.getValue();
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
          return 0;
        }
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
