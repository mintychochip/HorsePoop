package mintychochip.mintychochip.horsepoop.metas;

import mintychochip.mintychochip.horsepoop.container.Trait;

import java.util.List;

public interface Polygenic {

    List<Trait> getRequiredTraits();
    void setRequiredTraits(List<Trait> traits);
    boolean isActive(List<Trait> traits);
}
