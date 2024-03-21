package mintychochip.mintychochip.horsepoop.commands;

import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.mintychochip.horsepoop.HorseConfig;
import org.bukkit.entity.Player;

public class EnabledEntitiesCommand extends GenericCommandObject implements SubCommand {

    private final HorseConfig horseConfig;
    public EnabledEntitiesCommand(String executor, String description, HorseConfig horseConfig) {
        super(executor, description);
        this.horseConfig = horseConfig;
    }

    @Override
    public boolean execute(String[] strings, Player player) {
        player.sendMessage(horseConfig.getEnabledEntityTypes().toString());
        return true;
    }
}
