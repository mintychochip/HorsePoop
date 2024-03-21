package mintychochip.mintychochip.horsepoop.commands;

import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.mintychochip.horsepoop.HorseConfig;
import org.bukkit.entity.Player;

public class Debug extends GenericCommandObject implements SubCommand {

    private HorseConfig horseConfig;
    public Debug(String executor, String description, int depth, HorseConfig horseConfig) {
        super(executor, description, depth);
        this.horseConfig = horseConfig;
    }

    @Override
    public boolean execute(String[] strings, Player player) {
        return true;
    }
}
