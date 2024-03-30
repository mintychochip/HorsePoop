package mintychochip.mintychochip.horsepoop.container;

import mintychochip.mintychochip.horsepoop.container.Gene.GeneType;

public interface Trait {

    String getNamespacedKey();
    String getKey();
    String getShortDescription();
    GeneType getGeneType(); //geneTypes are final now

}
