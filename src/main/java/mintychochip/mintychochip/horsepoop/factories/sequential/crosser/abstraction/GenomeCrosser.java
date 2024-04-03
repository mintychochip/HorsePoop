package mintychochip.mintychochip.horsepoop.factories.sequential.crosser.abstraction;

import java.util.List;

import mintychochip.mintychochip.horsepoop.config.TraitMeta;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.Comparer;
import org.bukkit.entity.EntityType;

public interface GenomeCrosser<T extends TraitMeta> {

    /*
    Crosses two sets of traits and generates a new one
     */
    List<BaseTrait<T>> crossTraits(List<BaseTrait<T>> father, List<BaseTrait<T>> mother, EntityType entityType);

    /*
    Adds a 'GenomeCrossingStep<T>' to a list in a 'GenomeCrosser'
     */
    boolean addCrossingStep(GenomeCrossingStep<T> step);
    /*
    Sets a new 'Comparer<T>' to determine unique traits between lists of BaseTrait<T>
     */

    void setComparer(Comparer<T> newComparer);
    
    /*
    Returns the current 'Comparer<T>'
     */

    Comparer<T> getComparer();

}
