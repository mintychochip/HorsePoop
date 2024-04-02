//package mintychochip.mintychochip.horsepoop.commands;
//
//import mintychochip.genesis.commands.abstraction.GenericCommand;
//import mintychochip.genesis.commands.abstraction.SubCommand;
//import mintychochip.genesis.util.EnumUtil;
//import mintychochip.mintychochip.horsepoop.config.AnimalTraitWrapper;
//import mintychochip.mintychochip.horsepoop.config.configs.EntityConfig;
//import org.bukkit.entity.EntityType;
//import org.bukkit.entity.Player;
//
//import java.util.Set;
//
//public class EntityTraitCommand extends GenericCommand implements SubCommand {
//
//    private final EntityConfig entityConfig;
//
//    public EntityTraitCommand(String executor, String description, Set<String> strings, EntityConfig entityConfig) {
//        super(executor, description, strings);
//        this.entityConfig = entityConfig;
//    }
//    @Override
//    public boolean execute(String[] strings, Player player) {
//        if (strings.length < depth) {
//            return false;
//        }
//        String executor = strings[depth - 1];
//        if (!EnumUtil.isInEnum(EntityType.class, executor)) {
//            return false;
//        }
//        for (AnimalTraitWrapper animalTraitWrapper : entityConfig.getEntityTypeTraitMap().get(EntityType.valueOf(executor))) {
//            player.sendMessage(animalTraitWrapper.trait());
//        }
//        return true;
//    }
//}
