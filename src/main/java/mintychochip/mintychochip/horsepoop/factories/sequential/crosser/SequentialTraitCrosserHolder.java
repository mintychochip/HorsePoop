//package mintychochip.mintychochip.horsepoop.factories.sequential.crosser;
//
//import java.util.ArrayList;
//import java.util.List;
//import mintychochip.mintychochip.horsepoop.config.configs.TraitConfig;
//import mintychochip.mintychochip.horsepoop.container.BaseTrait;
//import mintychochip.mintychochip.horsepoop.container.Comparer;
//import mintychochip.mintychochip.horsepoop.container.Fetcher;
//import mintychochip.mintychochip.horsepoop.container.GeneCrosserImpl;
//import mintychochip.mintychochip.horsepoop.container.Trait;
//import mintychochip.mintychochip.horsepoop.container.Crosser;
//import mintychochip.mintychochip.horsepoop.container.ValueFetcher;
//import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction.TraitCrosserHolder;
//import mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction.GenomeCrossingStep;
//import org.bukkit.entity.EntityType;
//
//public class SequentialTraitCrosserHolder<U extends Trait, T extends Characteristic> implements
//    TraitCrosserHolder<T> {
//
//  private List<GenomeCrossingStep<T>> steps;
//
//  private Fetcher<U, T> fetcher = new ValueFetcher<>();
//
//  private final TraitConfig<U, T> config;
//
//  private Crosser<T> crosser = new GeneCrosserImpl<U, T>();
//
//  public SequentialTraitCrosserHolder(List<GenomeCrossingStep<T>> steps, Fetcher<U, T> fetcher,
//      TraitConfig<U, T> config, Crosser<T> crosser) {
//    this.steps = steps;
//    this.config = config;
//  }
//
//  @Override
//  public boolean addCrossingStep(GenomeCrossingStep<T> step) {
//    return steps.add(step);
//  }
//
//  @Override
//  public List<BaseTrait<T>> crossTraits(List<BaseTrait<T>> father, List<BaseTrait<T>> mother,
//      EntityType entityType) {
//    List<BaseTrait<T>> traits = new ArrayList<>();
//    for (GenomeCrossingStep<T> step : steps) {
//      List<BaseTrait<T>> baseTraits = step.crossTraits(father, mother, entityType, traits, config,
//          fetcher, crosser);
//      if(baseTraits != null && !baseTraits.isEmpty()) {
//        traits.addAll(baseTraits);
//      }
//    }
//    return traits;
//  }
//
//  public void setSteps(List<GenomeCrossingStep<T>> steps) {
//    this.steps = steps;
//  }
//
//  public void setFetcher(Fetcher<U, T> fetcher) {
//    this.fetcher = fetcher;
//  }
//
//  public void setCrosser(Crosser<T> crosser) {
//    this.crosser = crosser;
//  }
//
//  @Override
//  public void setComparer(Comparer<T> newComparer) {
//
//  }
//
//  @Override
//  public Comparer<T> getComparer() {
//    return null;
//  }
//}
