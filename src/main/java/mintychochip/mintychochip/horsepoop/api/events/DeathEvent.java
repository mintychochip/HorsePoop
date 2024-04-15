package mintychochip.mintychochip.horsepoop.api.events;

import java.util.List;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DeathEvent extends EntityDeathEvent {

  public DeathEvent(@NotNull LivingEntity entity,
      @NotNull List<ItemStack> drops) {
    super(entity, drops);
  }
}
