package mintychochip.mintychochip.horsepoop.container;

import mintychochip.mintychochip.horsepoop.metas.MetaType;

public interface Trait {
  String getNamespacedKey();
  String getKey();
  String getShortDescription();
  MetaType getMetaType();
}
