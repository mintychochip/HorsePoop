package mintychochip.mintychochip.horsepoop.container;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

public class CooldownContainer {

  private final long instanceTime = System.currentTimeMillis();
  public CooldownContainer(NamespacedKey key, double cooldown) {
    this.key = key;
    this.cooldown = cooldown;
  }

  private final NamespacedKey key;
  private final double cooldown;
  public NamespacedKey getKey() {
    return key;
  }

  public double getCooldown() {
    return cooldown;
  }

  public double getRemaining() {
    long l = System.currentTimeMillis() - instanceTime;
    double v = ((double) l / 1000) - cooldown;
    return v < 0 ? v * -1 : 0;
  }
  public boolean cooldownIsOver() {
    long l = System.currentTimeMillis() - instanceTime;
    return (double) l / 1000 >= cooldown;
  }
}
