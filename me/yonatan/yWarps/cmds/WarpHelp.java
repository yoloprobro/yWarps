package me.yonatan.yWarps.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class WarpHelp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage(ChatColor.GRAY + "*** " +ChatColor.GREEN + "WARP" + ChatColor.GRAY + " Help ***");
        commandSender.sendMessage(ChatColor.GRAY + "/warp <name> - Warps you to your privately set warp.");
        commandSender.sendMessage(ChatColor.GRAY + "/warp set <name> - Creates your own private warp");
        commandSender.sendMessage(ChatColor.GRAY + "/warp delete <name> - Removes your own private warp.");
        commandSender.sendMessage(ChatColor.GRAY + "/warp list - Lists all of your private warps.");

        commandSender.sendMessage(ChatColor.GRAY + "*** " +ChatColor.GOLD + "PUBLIC WARP" + ChatColor.GRAY + " Help ***");
        commandSender.sendMessage(ChatColor.GRAY + "/pwarp <name> - Warps you to a publicly set warp");
        commandSender.sendMessage(ChatColor.GRAY + "/pwarp set <name> - Creates a public warp.");
        commandSender.sendMessage(ChatColor.GRAY + "/pwarp delete <name> - Removes a public warp owned ONLY by you.");
        commandSender.sendMessage(ChatColor.GRAY + "/pwarp list - Lists all of the public warps available.");
        return true;
    }
}
