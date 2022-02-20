package xyz.pepefab.randomteleportplayer.Commands;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import xyz.pepefab.randomteleportplayer.Utils.ChatUtils;
import xyz.pepefab.randomteleportplayer.Utils.PermissionsUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTeleportPlayerCommand extends CommandBase {

    private final List<String> aliases = Lists.newArrayList(new String[]{"rtplayer"});

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
        List<EntityPlayerMP> players = server.getServer().getPlayerList().getPlayers();
        players.remove(sender);

        if(players.isEmpty()){
            sender.sendMessage(new TextComponentString(ChatUtils.replaceTextFormating("&cLa liste est vide, vous avez fini le tour des joueurs actuellement connectés.")));
            return;
        }

        Random random = new Random();
        int target = random.nextInt(players.size());

        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) players.get(target);
        players.remove(target);

        sender.sendMessage(new TextComponentString(ChatUtils.replaceTextFormating("&aTéléportation à &f" + entityPlayerMP.getName())));
        sender.sendMessage(new TextComponentString(ChatUtils.replaceTextFormating("&aJoueur dans la liste : &f" + players.size())));
        server.getCommandManager().executeCommand(sender, "cmi:cmi tp " + entityPlayerMP.getName());
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> possibleArgs = new ArrayList<>();
        return getListOfStringsMatchingLastWord(args, possibleArgs);
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return PermissionsUtils.canUse("uprandomteleportplayer.use", sender);
    }

}
