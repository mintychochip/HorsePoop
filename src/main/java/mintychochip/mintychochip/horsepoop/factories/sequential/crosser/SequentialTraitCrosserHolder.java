package mintychochip.mintychochip.horsepoop.factories.sequential.crosser;

import java.util.ArrayList;
import java.util.List;
import mintychochip.mintychochip.horsepoop.api.Comparer;
import mintychochip.mintychochip.horsepoop.api.Fetcher;
import mintychochip.mintychochip.horsepoop.api.TraitCrosser;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.GeneTraitCrosserImpl;
import mintychochip.mintychochip.horsepoop.container.ValueFetcher;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction.GenomeCrossingStep;
import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction.TraitCrosserHolder;
import org.bukkit.entity.EntityType;

public class SequentialTraitCrosserHolder<U extends TraitEnum> implements
    TraitCrosserHolder<U> {

  private List<GenomeCrossingStep<U>> steps;

  private Fetcher<U> fetcher = new ValueFetcher<>();

  private final TraitConfig<U> config;

  private TraitCrosser<U> crosser = new GeneTraitCrosserImpl<>();

  public SequentialTraitCrosserHolder(List<GenomeCrossingStep<U>> steps, Fetcher<U> fetcher,
      TraitConfig<U> config, TraitCrosser<U> crosser) {
    this.steps = steps;
    this.config = config;
  }

  @Override
  public boolean addCrossingStep(GenomeCrossingStep<U> step) {
    return steps.add(step);
  }

  @Override
  public List<BaseTrait<U>> crossTraits(List<BaseTrait<U>> father, List<BaseTrait<U>> mother, EntityType entityType) {
    List<BaseTrait<U>> traits = new ArrayList<>();
    for(GenomeCrossingStep<U> step : steps) {
      List<BaseTrait<U>> baseTraits = step.crossTraits(father, mother, entityType, traits, config, fetcher, crosser);
      if (baseTraits != null && !baseTraits.isEmpty()) {
        traits.addAll(baseTraits);
      }
    }
    return traits;
  }
  public void setSteps(List<GenomeCrossingStep<U>> steps) {
    this.steps = steps;
  }
  public void setCrosser(TraitCrosser<U> crosser) {
    this.crosser = crosser;
  }

  public void setFetcher(Fetcher<U> fetcher) {
    this.fetcher = fetcher;
  }

  @Override
  public void setComparer(Comparer<U> newComparer) {

  }

  @Override
  public Comparer<U> getComparer() {
    return null;
  }
}
