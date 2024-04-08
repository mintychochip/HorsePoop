package mintychochip.mintychochip.horsepoop.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;

public final class WeightedRandoms<T> {

  private final List<T> list;
  private final List<Double> weights;
  private final Random random = new Random();

  private WeightedRandoms(List<T> list, List<Double> weights) {
    this.list = list;
    this.weights = weights;
  }
  public T next() {
    if (list.isEmpty()) {
      throw new IllegalStateException("Cannot pick from an empty list.");
    }
    double randomNum = random.nextDouble();
    int index = this.binarySearchBoundary(weights, randomNum);
    return list.get(index);
  }

  private int binarySearchBoundary(List<Double> list, double value) {
    double sum = 0.0;
    for (int i = 0; i < list.size(); i++) {
      sum += list.get(i);
      if (sum >= value) {
        return i;
      }
    }
    return list.size() - 1;
  }

  public static <T> WeightedRandoms<T> instance(List<T> list, List<Double> weights) {
    if (list.isEmpty() || weights.isEmpty()) {
      return null;
    }
    if (list.size() != weights.size()) {
      throw new IllegalArgumentException(
          "Weights and list must equal the same size! List: " + list.size() + "Weights: "
              + weights.size());
    }
    if (1 != weights.stream().mapToDouble(Double::doubleValue).sum()) {
      throw new IllegalArgumentException("Weights must add to one!");
    }
    return new WeightedRandoms<>(list, weights);
  }
}
