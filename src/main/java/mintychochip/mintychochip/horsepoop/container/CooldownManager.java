package mintychochip.mintychochip.horsepoop.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mintychochip.mintychochip.horsepoop.api.events.FawnyEvent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;

public class CooldownManager {

  private final Map<LivingEntity, List<CooldownContainer>> cooldownContainerMap = new HashMap<>();
  public void addCooldown(LivingEntity livingEntity, CooldownContainer container) {
    List<CooldownContainer> cooldownContainers = cooldownContainerMap.get(livingEntity);
    if(cooldownContainers == null || cooldownContainers.isEmpty()) {
      cooldownContainers = new ArrayList<>();
    }
    cooldownContainers.add(container);
    cooldownContainerMap.put(livingEntity,cooldownContainers);
  }
  private CooldownContainer findContainer(LivingEntity livingEntity, NamespacedKey namespacedKey) {
    List<CooldownContainer> cooldownContainers = cooldownContainerMap.get(livingEntity);
    return cooldownContainers.stream()
        .filter(container -> container.getKey().equals(namespacedKey)).findFirst().orElse(null);
  }
  public boolean mapContainsCooldown(LivingEntity livingEntity, CooldownContainer container) {
    List<CooldownContainer> cooldownContainers = cooldownContainerMap.get(livingEntity);
    if(cooldownContainers == null) {
      this.addCooldown(livingEntity,container);
      return false;
    }
    CooldownContainer cooldownContainer = cooldownContainers.stream()
        .filter(cc -> cc.getKey().equals(container.getKey())).findFirst().orElse(null);
    return cooldownContainer != null;
  }

  public boolean offCooldown(LivingEntity livingEntity, NamespacedKey namespacedKey) {
    CooldownContainer container = this.findContainer(livingEntity, namespacedKey);
    if(container == null) {
      return false;
    }
    List<CooldownContainer> cooldownContainers = cooldownContainerMap.get(livingEntity);
    if(container.cooldownIsOver()) {
      boolean b = cooldownContainers.remove(container);
      cooldownContainers.add(new CooldownContainer(namespacedKey,container.getCooldown()));
      cooldownContainerMap.put(livingEntity,cooldownContainers);
      return b;
    }
    return false;
  }

  public void trigerEventOffCooldown(LivingEntity livingEntity, CooldownContainer cooldownContainer, FawnyEvent fawnyEvent) {
    if(!this.mapContainsCooldown(livingEntity,cooldownContainer)) {
      Bukkit.getPluginManager().callEvent(fawnyEvent);
    } else {
      if(this.offCooldown(livingEntity,cooldownContainer.getKey())) {
        Bukkit.getPluginManager().callEvent(fawnyEvent);
      }
    }
  }
}
