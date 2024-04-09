package mintychochip.mintychochip.horsepoop.listener.display;

import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.config.configs.EntityConfig;
import mintychochip.mintychochip.horsepoop.config.configs.SettingsConfig;
import mintychochip.mintychochip.horsepoop.metas.MendelianMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;
import net.kyori.adventure.text.Component;

public class MendelianDisplay<U extends TraitEnum> implements Display<U> {
  @Override
  public Component getHoverText(Meta<U> meta) {
    Component component = Component.empty();
    if(meta instanceof MendelianMeta<U> mm) {
      component = component.append(Component.text("Dominant allele chance: " + mm.getChance()));
    }
    return component;
  }
}
