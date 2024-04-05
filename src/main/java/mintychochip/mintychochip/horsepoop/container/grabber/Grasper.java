package mintychochip.mintychochip.horsepoop.container.grabber;

import org.bukkit.entity.LivingEntity;

public interface Grasper<T> { //move to genesis

    T grab(LivingEntity livingEntity, ItemData itemData);

    void toss(LivingEntity livingEntity);
}
