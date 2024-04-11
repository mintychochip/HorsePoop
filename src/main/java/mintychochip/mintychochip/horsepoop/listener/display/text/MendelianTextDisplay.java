package mintychochip.mintychochip.horsepoop.listener.display.text;

import com.google.gson.Gson;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.MendelianGene;
import net.kyori.adventure.text.Component;

public class MendelianTextDisplay<U extends TraitEnum>  implements TextValueDisplay {
    public MendelianTextDisplay(BaseTrait<U> trait) {
        super(trait);
    }
    @Override
    public Component getTextValue() {
        MendelianGene mendelianGene = new Gson().fromJson(trait.getValue(), MendelianGene.class);
        return Component.text(mendelianGene.toString());
    }
}
