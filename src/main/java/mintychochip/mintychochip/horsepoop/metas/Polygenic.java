package mintychochip.mintychochip.horsepoop.metas;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;

import java.util.List;

public interface Polygenic {

    List<TraitEnum> getRequiredTraits();
    void setRequiredTraits(List<TraitEnum> traitEnums);
    boolean isActive(List<TraitEnum> traitEnums);
}
