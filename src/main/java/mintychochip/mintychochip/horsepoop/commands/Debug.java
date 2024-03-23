package mintychochip.mintychochip.horsepoop.commands;

import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.mintychochip.horsepoop.EntityConfig;
import org.bukkit.entity.Player;

public class Debug extends GenericCommandObject implements SubCommand {

    private EntityConfig entityConfig;
    public Debug(String executor, String description, int depth, EntityConfig entityConfig) {
        super(executor, description, depth);
        this.entityConfig = entityConfig;
    }

    @Override
    public boolean execute(String[] strings, Player player) {
        return true;
    }
}
