package mintychochip.mintychochip.horsepoop.factories;

import com.google.gson.Gson;

import java.util.Random;

import mintychochip.genesis.util.Rarity;
import mintychochip.mintychochip.horsepoop.config.AnimalTraitWrapper;
import mintychochip.mintychochip.horsepoop.config.GeneTraitMeta;
import mintychochip.mintychochip.horsepoop.config.TraitMeta;
import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.*;
import mintychochip.mintychochip.horsepoop.container.enums.FeatherColor;
import mintychochip.mintychochip.horsepoop.container.enums.Gender;
import mintychochip.mintychochip.horsepoop.container.enums.MendelianAllele;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.GenericGeneTrait;
import mintychochip.mintychochip.horsepoop.container.enums.attributes.specific.GeneticAttribute;
import mintychochip.mintychochip.horsepoop.container.enums.characteristics.GenericCharacteristicTrait;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;

public class TraitGeneratorImpl<T extends TraitMeta> implements TraitGenerator<T> { //rename this class

    private Gson gson = new Gson();
    private Random random = new Random(System.currentTimeMillis());
    private TraitFetcher<T> fetcher = new TraitFetcher<>();
    @Override
    public <U extends Trait> BaseTrait<T> createInstance(U trait, EntityType entityType, TraitConfig<U, T> traitConfig) {
        AnimalTraitWrapper<T> traitWrapper = traitConfig.getTraitWrapper(trait, entityType);
        if (traitWrapper == null) {
            return null;
        }
        String traitString = fetcher.toJson(trait);
        return BaseTrait.createInstance(this.generateValue(trait,traitWrapper.meta()),traitString,traitWrapper.meta(),this);
    }

    private String generateValue(Trait trait, TraitMeta traitMeta) {
        ValueType valueType = trait.getValueType();
        double maximum = traitMeta.maximum();
        double minimum = traitMeta.minimum();

        if (valueType == ValueType.MENDELIAN && traitMeta instanceof GeneTraitMeta geneTraitMeta) {
            return mendelianCase(geneTraitMeta);
        }

        return switch (valueType) {
            case NUMERIC -> numericCase(maximum, minimum);
            case INTEGER -> numericCase((int) maximum, (int) minimum);
            case ENUM -> enumCase(trait);
            default -> null;
        };
    }

    private String mendelianCase(GeneTraitMeta meta) {
        return gson.toJson(
                MendelianGene.createInstance(MendelianAllele.createAllele(meta.chance()),
                        MendelianAllele.createAllele(meta.chance())));
    }

    private String numericCase(double maximum, double minimum) {
        double child = random.nextDouble(minimum, maximum);
        if (child > maximum) {
            child = maximum;
        }
        return gson.toJson(child);
    }

    private String enumCase(Trait trait) {
        if (trait == GeneticAttribute.PARTICLE) {
            return randomEnumVal(Particle.values(), random);
        } else if (trait == GenericGeneTrait.FEATHER_COLOR) {
            return randomEnumVal(FeatherColor.values(), random);
        } else if (trait == GenericCharacteristicTrait.RARITY) {
            return randomEnumVal(Rarity.values(), random);
        } else if (trait == GenericCharacteristicTrait.GENDER) {
            return randomEnumVal(Gender.values(), random);
        }
        return null;
    }

    private static <T extends Enum<?>> String randomEnumVal(T[] values, Random random) {
        int i = random.nextInt(values.length);
        return values[i].toString();
    }


}
