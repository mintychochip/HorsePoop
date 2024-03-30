package mintychochip.mintychochip.horsepoop.config;

import com.google.gson.annotations.SerializedName;

public class AnimalTraitMeta {
  @SerializedName("chance")
  private double chance = -1;
  @SerializedName("max")
  private double maximum = -1;
  @SerializedName("min")
  private double minimum = -1;

  public double getChance() {
    return chance;
  }

  public double getMaximum() {
    return maximum;
  }

  public double getMinimum() {
    return minimum;
  }
}
