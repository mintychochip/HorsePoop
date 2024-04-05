//package mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction;
//
//import java.util.List;
//
//import mintychochip.mintychochip.horsepoop.container.BaseTrait;
//import mintychochip.mintychochip.horsepoop.container.Comparer;
//import mintychochip.mintychochip.horsepoop.container.Trait;
//import org.bukkit.entity.EntityType;
//
//public interface TraitCrosserHolder<U extends Trait> {
//
//    /*
//    Crosses two sets of traits and generates a new one
//     */
//    List<BaseTrait<U>> crossTraits(List<BaseTrait<U>> father, List<BaseTrait<U>> mother, EntityType entityType);
//
//    /*
//    Adds a 'GenomeCrossingStep<U>' to a list in a 'TraitCrosserHolder'
//     */
//    boolean addCrossingStep(GenomeCrossingStep<U> step);
//    /*
//    Sets a new 'Comparer<U>' to determine unique traits between lists of BaseTrait<U>
//     */
//
//    void setComparer(Comparer<U> newComparer);
//
//    /*
//    Returns the current 'Comparer<U>'
//     */
//
//    Comparer<U> getComparer();
//
//}
