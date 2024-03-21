package mintychochip.mintychochip.horsepoop.commands;

import mintychochip.genesis.commands.abstraction.GenericCommand;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.genesis.util.EnumUtil;
import mintychochip.mintychochip.horsepoop.HorseConfig;
import mintychochip.mintychochip.horsepoop.container.Trait;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Set;

public class EntityTraitCommand extends GenericCommand implements SubCommand {

    private final HorseConfig horseConfig;

    public EntityTraitCommand(String executor, String description, Set<String> strings, HorseConfig horseConfig) {
        super(executor, description, strings);
        this.horseConfig = horseConfig;
    }
    @Override
    public boolean execute(String[] strings, Player player) {
        if (strings.length < depth) {
            return false;
        }
        String executor = strings[depth - 1];
        if (!EnumUtil.isInEnum(EntityType.class, executor)) {
            return false;
        }
        for (Trait trait : horseConfig.getEntityTypeTraitMap().get(EntityType.valueOf(executor))) {
            player.sendMessage(trait.getNamespacedKey());
        }
        return true;
    }
}
