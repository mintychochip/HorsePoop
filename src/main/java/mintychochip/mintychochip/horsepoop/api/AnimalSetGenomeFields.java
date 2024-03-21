package mintychochip.mintychochip.horsepoop.api;

import mintychochip.genesis.events.AbstractEvent;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;

public class AnimalSetGenomeFields extends AbstractEvent implements Cancellable {

    private boolean cancelled = false;
    private final LivingEntity livingEntity;

    private final AnimalGenome genome;
    public AnimalSetGenomeFields(LivingEntity livingEntity, AnimalGenome genome) {
        this.livingEntity = livingEntity;
        this.genome = genome;
    }
    public AnimalGenome getGenome() {
        return genome;
    }

    public LivingEntity getLivingEntity() {
        return livingEntity;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
