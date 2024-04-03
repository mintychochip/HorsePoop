package mintychochip.mintychochip.horsepoop.container;

import mintychochip.mintychochip.horsepoop.metas.EnumMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import mintychochip.mintychochip.horsepoop.metas.MetaType;

import java.util.List;
import java.util.Map;

public class ValueFetcher<U extends Trait> implements Fetcher<U> {

    private final TraitFetcher<U> fetcher = new TraitFetcher<>();
    @Override
    public <Y extends Enum<Y>> Y getEnumValue(List<BaseTrait<U>> baseTraits, U trait, Class<Y> enumClass) {
        if(trait.getMetaType() != MetaType.ENUM){
            return null;
        }
        BaseTrait<U> traitFromList = fetcher.getTraitFromList(baseTraits, trait);
        EnumMeta<U> meta = (EnumMeta<U>) traitFromList.getMeta();
        String enumClassString = meta.getEnumClass();
        if(enumClassString == null) {
            return null;
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
        return fetcher.getTraitFromList(baseTraits,trait);
    }
    @Override
    public boolean isTraitInList(List<BaseTrait<U>> baseTraits, U trait) {
        return fetcher.isTraitInList(baseTraits,trait);
    }

    @Override
    public String toJson(Trait trait) {
        return null;
    }


}
