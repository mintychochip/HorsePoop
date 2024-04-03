package mintychochip.mintychochip.horsepoop.factories.sequential.instancer;

import java.util.ArrayList;
import java.util.List;

import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.Trait;
import mintychochip.mintychochip.horsepoop.container.Generator;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.InstancingStep;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.GeneratorHolder;
import org.bukkit.entity.EntityType;

public class SequentialTraitGenerator<U extends Trait> implements
    GeneratorHolder<T> {

    private final List<InstancingStep<T>> steps;

    private final TraitConfig<U, T> config;

    private final Generator<T> generator;

    public SequentialTraitGenerator(List<InstancingStep<T>> steps, TraitConfig<U, T> config, Generator<T> generator) {
        this.steps = steps;
        this.config = config;
        this.generator = generator;
    }

    public SequentialTraitGenerator(TraitConfig<U, T> config, Generator<T> generator) {
        this(new ArrayList<>(), config, generator);
    }

    public List<BaseTrait<T>> instanceTraits(EntityType entityType) {
        if (steps.isEmpty()) {
            return null;
        }
        List<BaseTrait<T>> traits = new ArrayList<>();
        for (InstancingStep<T> step : steps) {
            List<BaseTrait<T>> newTraits = step.instanceTrait(entityType, traits, config, generator);
            if (newTraits != null && !newTraits.isEmpty()) {
                traits.addAll(newTraits);
            }
        }
        return traits;
    }

    @Override
    public boolean addStep(InstancingStep<T> step) {
        return steps.add(step);
    }
}
