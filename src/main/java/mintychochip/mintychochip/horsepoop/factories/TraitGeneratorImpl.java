package mintychochip.mintychochip.horsepoop.factories;

import com.google.gson.Gson;

import java.util.*;

import mintychochip.genesis.util.Rarity;
import mintychochip.mintychochip.horsepoop.config.AnimalTraitWrapper;
import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.*;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import mintychochip.mintychochip.horsepoop.metas.MetaType;
import org.bukkit.entity.EntityType;

public class TraitGeneratorImpl<U extends Trait> implements
        Generator<U> { //rename this class

    private Map<MetaType, MetaValueGeneration<U>> typeGenerationMap = new HashMap<>();
    private final Gson gson = new Gson();
    private final Random random = new Random(System.currentTimeMillis());

    @Override
    public <U extends Trait> BaseTrait<T> createInstance(U trait, EntityType entityType,
                                                         TraitConfig<U> traitConfig) {
        TraitFetcher<U> fetcher = new TraitFetcher<>();

        AnimalTraitWrapper<T> traitWrapper = traitConfig.getTraitWrapper(trait, entityType);
        if (traitWrapper == null) {
            return null;
        }
        String traitString = fetcher.toJson(trait);
        return BaseTrait.createInstance(this.generateValue(trait, traitWrapper.meta()), traitString,
                traitWrapper.meta(), this);
    }

    private String generateValue(U trait, EntityType entityType) {
        MetaType metaType = trait.getMetaType();

        if (metaType == MetaType.MENDELIAN && traitMeta instanceof GeneTraitMeta geneTraitMeta) {
            return mendelianCase(geneTraitMeta);
        }

        return switch (metaType) {
            case NUMERIC -> numericCase(maximum, minimum);
            case INTEGER -> numericCase((int) maximum, (int) minimum);
            case ENUM -> enumCase(trait);
            default -> null;
        };
    }


    @SuppressWarnings("unchecked")
//    private String enumCase(EnumMeta<U> meta, String value) {
//        String enumClassString = meta.getEnumClass();
//        try {
//            Class<?> aClass = Class.forName(enumClassString);
//          Class<? extends Enum<?>> enumClass = (Class<? extends Enum<?>>) aClass;
//          Enum<?>[] enumConstants = enumClass.getEnumConstants();
//
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        if (trait == GeneticAttribute.PARTICLE) {
//            return randomEnumVal(Particle.values(), random);
//        } else if (trait == GenericGeneTrait.FEATHER_COLOR) {
//            return randomEnumVal(FeatherColor.values(), random);
//        } else if (trait == GenericCharacteristicTrait.RARITY) {
//            return randomEnumVal(Rarity.values(), random);
//        } else if (trait == GenericCharacteristicTrait.GENDER) {
//            return randomEnumVal(Gender.values(), random);
//        }
//        return null;
//    }

    private static <T extends Enum<?>> String randomEnumVal(T[] values, Random random) {
        int i = random.nextInt(values.length);
        return values[i].toString();
    }


    @Override
    public BaseTrait<U> createInstance(U trait, EntityType entityType, TraitConfig<U> traitConfig) {
        Meta<U> meta = traitConfig.getMeta(trait, entityType);

        String value = this.typeGenerationMap.get(trait.getMetaType()).generateValue(meta);
        if (value == null) {
            return null;
        }
        return BaseTrait.create(value,meta,this);
    }

    @Override
    public void addMetaGenerationType(MetaType metaType, MetaValueGeneration<U> generation) {
        typeGenerationMap.put(metaType, generation);
    }
}
