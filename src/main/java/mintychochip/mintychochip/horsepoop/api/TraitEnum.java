package mintychochip.mintychochip.horsepoop.api;

import mintychochip.mintychochip.horsepoop.metas.MetaType;

public interface TraitEnum {

  String getNamespacedKey();
  /**
   *
   * @return Returns a String trait key.
   */
  String getKey();

  /**
   *
   * @return Returns a String with short description on the trait
   */
  String getDescription();

  /**
   *
   * @return Returns the MetaType on the TraitEnum object
   */
  MetaType getMetaType();
}
