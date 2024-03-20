package mintychochip.mintychochip.horsepoop.api;

import mintychochip.genesis.events.AbstractEvent;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class AnimalCreationEvent extends AbstractEvent implements Cancellable {

    private boolean cancelled = false;
    private final AbstractHorse abstractHorse;

    private final AnimalGenome genome;
    public AnimalCreationEvent(AbstractHorse abstractHorse, AnimalGenome genome) {
        this.abstractHorse = abstractHorse;
        this.genome = genome;
    }
    public AnimalGenome getGenome() {
        return genome;
    }
    public AbstractHorse getAbstractHorse() {
        return abstractHorse;
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
