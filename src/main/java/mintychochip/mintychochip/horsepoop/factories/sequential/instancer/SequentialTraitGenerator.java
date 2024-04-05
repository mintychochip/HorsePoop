package mintychochip.mintychochip.horsepoop.factories.sequential.instancer;

import java.util.ArrayList;
import java.util.List;

import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.api.Generator;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.InstancingStep;
import mintychochip.mintychochip.horsepoop.factories.sequential.instancer.gene.abstraction.GeneratorHolder;
import org.bukkit.entity.EntityType;

public class SequentialTraitGenerator<U extends TraitEnum> implements
    GeneratorHolder<U> {

    private final List<InstancingStep<U>> steps;

    private final TraitConfig<U> config;

    private final Generator<U> generator;

    public SequentialTraitGenerator(List<InstancingStep<U>> steps, TraitConfig<U> config, Generator<U> generator) {
        this.steps = steps;
        this.config = config;
        this.generator = generator;
    }

    public SequentialTraitGenerator(TraitConfig<U> config, Generator<U> generator) {
        this(new ArrayList<>(), config, generator);
    }

    public List<BaseTrait<U>> instanceTraits(EntityType entityType) {
        if (steps.isEmpty()) {
            return null;
        }
        List<BaseTrait<U>> traits = new ArrayList<>();
        for (InstancingStep<U> step : steps) {
            List<BaseTrait<U>> newTraits = step.instanceTrait(entityType, traits, config, generator);
            if (newTraits != null && !newTraits.isEmpty()) {
                traits.addAll(newTraits);
            }
        }
        return traits;
    }

    @Override
    public boolean addStep(InstancingStep<U> step) {
        return steps.add(step);
    }
}
