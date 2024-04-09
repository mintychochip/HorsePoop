package mintychochip.mintychochip.horsepoop.factories.sequential.crosser.steps;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import mintychochip.mintychochip.horsepoop.api.Comparer;
import mintychochip.mintychochip.horsepoop.api.Fetcher;
import mintychochip.mintychochip.horsepoop.api.Generator;
import mintychochip.mintychochip.horsepoop.api.TraitCrosser;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction.GenomeCrossingStep;
import mintychochip.mintychochip.horsepoop.metas.Crossable;
import org.bukkit.entity.EntityType;

public class MutationStep<U extends TraitEnum> implements GenomeCrossingStep<U> {

    private final double recombinanceChance;
    private final int maxCount;
    private final Comparer<U> comparer;

    private final Generator<U> generator;
    Predicate<BaseTrait<U>> CROSSABLE = baseTrait -> baseTrait.getMeta() instanceof Crossable c && c.isCrossable();

    public MutationStep(double recombinanceChance, int maxCount, Comparer<U> comparer, Generator<U> generator) {
        this.recombinanceChance = recombinanceChance;
        this.maxCount = maxCount;
        this.comparer = comparer;
        this.generator = generator;
    }

    @Override
    public List<BaseTrait<U>> crossTraits(List<BaseTrait<U>> father, List<BaseTrait<U>> mother, EntityType entityType, List<BaseTrait<U>> baseTraits, TraitConfig<U> config, Fetcher<U> fetcher, TraitCrosser<U> traitCrosser) {
        Random random = new Random(System.currentTimeMillis());
        List<BaseTrait<U>> traits = new ArrayList<>();

        int count = 0;
        for (BaseTrait<U> trait : comparer.uniqueTraits(father, mother)) {
            if (CROSSABLE.test(trait) && random.nextDouble() <= recombinanceChance && count <= maxCount) {
                BaseTrait<U> instance = generator.createInstance(trait.getMeta().getTrait(), entityType, config);
                if (instance != null) {
                    BaseTrait<U> child = traitCrosser.crossTraits(trait, instance);
                    if (child != null) {
                        traits.add(child);
                        count++;
                    }
                }
            }
        }
        return traits;
    }
}