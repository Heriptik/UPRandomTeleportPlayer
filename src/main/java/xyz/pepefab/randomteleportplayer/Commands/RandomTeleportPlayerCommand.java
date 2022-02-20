package xyz.pepefab.randomteleportplayer.Commands;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import xyz.pepefab.randomteleportplayer.Utils.ChatUtils;
import xyz.pepefab.randomteleportplayer.Utils.PermissionsUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomTeleportPlayerCommand extends CommandBase {

    private final List<String> aliases = Lists.newArrayList(new String[]{"rtplayer"});
    boolean listEmpty = false;
    List<String> playersList = null;

    @Override
    public String getName() {
        return "randomteleportplayer";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "&cUsage: /randomteleportplayer";
    }

    @Override
    public List<String> getAliases(){
        return this.aliases;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 1){
            listEmpty = false;
            sender.sendMessage(new TextComponentString(ChatUtils.replaceTextFormating("&aReset des joueurs dans la liste.")));
            return;
        }
        if(server.getPlayerList().getCurrentPlayerCount() == 1){
            sender.sendMessage(new TextComponentString(ChatUtils.replaceTextFormating("&c&lErreur. &cVous êtes seul sur le serveur :(")));
            return;
        }
        if (listEmpty == false) {
            playersList = Lists.newArrayList(server.getOnlinePlayerNames());
            Arrays.toString(new boolean[]{playersList.remove(sender.getName())});
            listEmpty = true;
            sender.sendMessage(new TextComponentString(ChatUtils.replaceTextFormating("&aInitialisation de la liste ...")));
        }
        if(listEmpty == true) {
            Random random = new Random();
            int target = random.nextInt(playersList.size());

            String entityPlayerMP = playersList.get(target);
            Arrays.toString(new boolean[]{playersList.remove(entityPlayerMP)});

            server.getCommandManager().executeCommand(sender, "cmi:cmi tp " + entityPlayerMP);
            sender.sendMessage(new TextComponentString(ChatUtils.replaceTextFormating("&eJoueur dans la liste : &3" + playersList.size())));

            if (playersList.isEmpty()) {
                sender.sendMessage(new TextComponentString(ChatUtils.replaceTextFormating("&aVous avez fait le tour de tous les joueurs de la liste ... Initialisation d'une nouvelle liste.")));
                playersList = Lists.newArrayList(server.getOnlinePlayerNames());
                Arrays.toString(new boolean[]{playersList.remove(sender.getName())});
            }
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> possibleArgs = new ArrayList<>();
        if(args.length == 1){
            possibleArgs.add("reset");
        }
        return getListOfStringsMatchingLastWord(args, possibleArgs);
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return PermissionsUtils.canUse("uprandomteleportplayer.use", sender);
    }

}
