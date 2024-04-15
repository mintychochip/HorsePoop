package mintychochip.mintychochip.horsepoop.util.math;

import java.util.Random;
import mintychochip.genesis.Genesis;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class RandomYield {

  public static int calculateRandomYield(int max, ItemStack itemStack) {
    Integer fortuneLevel = itemStack.getEnchantments().get(Enchantment.LOOT_BONUS_BLOCKS);
    int min = 1;
    if (fortuneLevel != null) {
      min = calculateMaxYield(fortuneLevel, min);
      max = calculateMaxYield(fortuneLevel, max);
      if (min < 1) {
        min = 1;
      }
    }
    if (min >= max) {
      max = min + 1;
    }
    return new Random().nextInt(min, max);
  }

  private static int calculateMaxYield(int fortuneLevel, int i) {
    return (int) (i * (((double) 1 / (fortuneLevel + 2)) + ((double) (fortuneLevel + 1) / 2)));
  }
}
