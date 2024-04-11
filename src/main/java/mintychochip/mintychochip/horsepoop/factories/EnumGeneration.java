package mintychochip.mintychochip.horsepoop.factories;

import java.util.Random;
import mintychochip.mintychochip.horsepoop.api.MetaValueGeneration;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.metas.EnumMeta;
import mintychochip.mintychochip.horsepoop.metas.Meta;

public final class EnumGeneration<U extends TraitEnum> implements MetaValueGeneration<U> {
  @Override
  public String generateValue(Meta<U> meta) {
    String value = null;
    if(meta instanceof EnumMeta<U> em) {
      Object[] enumClassConstants = this.getEnumClassConstants(em.getEnumClass());
      if(enumClassConstants == null) {
        return null;
      }
      value = enumClassConstants[new Random().nextInt(enumClassConstants.length)].toString();
    }
    return value;
  }
  private Object[] getEnumClassConstants(String classString) {
    try {
      Class<?> aClass = Class.forName(classString);
      if (Enum.class.isAssignableFrom(aClass)) {
        return aClass.getEnumConstants();
      }
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    return null;
  }
}

