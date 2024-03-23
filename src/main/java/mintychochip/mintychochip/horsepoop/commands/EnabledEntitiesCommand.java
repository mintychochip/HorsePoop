package mintychochip.mintychochip.horsepoop.commands;

import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.mintychochip.horsepoop.EntityConfig;
import org.bukkit.entity.Player;

public class EnabledEntitiesCommand extends GenericCommandObject implements SubCommand {

    private final EntityConfig entityConfig;
    public EnabledEntitiesCommand(String executor, String description, EntityConfig entityConfig) {
        super(executor, description);
        this.entityConfig = entityConfig;
    }

    @Override
    public boolean execute(String[] strings, Player player) {
        player.sendMessage(entityConfig.getEnabledEntityTypes().toString());
        return true;
    }
}
