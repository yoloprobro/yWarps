package me.yonatan.yWarps.cmds;

import me.yonatan.yWarps.yWarps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;

public class PublicWarpCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)){
            commandSender.sendMessage(ChatColor.RED + "Must be a player inorder to use this command.");
            return true;
        }
        Player player = (Player) commandSender;
        if (strings.length == 1){
            String warpName = strings[0];

            if (warpName.equalsIgnoreCase("list")){
                ArrayList<String> list = new ArrayList<>();
                ConfigurationSection inventorySection = yWarps.get().getWarpData().getConfigurationSection("pWarps");
                if (inventorySection == null){
                    player.sendMessage(ChatColor.GRAY + "No warps are set.");
                    return true;
                }
                for (String key : inventorySection.getKeys(false)) {
                    list.add(key);
                }
                player.sendMessage(list.toString());
            }
            else {
                if (yWarps.get().getWarpData().getString("pWarps." + warpName + ".WORLD") == null){
                    player.sendMessage(ChatColor.RED + "Warp not found.");
                    return true;
                }
                String world = yWarps.get().getWarpData().getString("pWarps." + warpName + ".WORLD");
                double x = yWarps.get().getWarpData().getDouble("pWarps." + warpName + ".X");
                double y = yWarps.get().getWarpData().getDouble("pWarps." + warpName + ".Y");
                double z = yWarps.get().getWarpData().getDouble("pWarps." + warpName + ".Z");
                float yaw = (float) yWarps.get().getWarpData().getDouble("pWarps." + warpName + ".YAW");
                float pitch = (float) yWarps.get().getWarpData().getDouble("pWarps." + warpName + ".PITCH");
                Location location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                player.teleport(location);
            }
            return true;
        }
        else if (strings.length == 2){
            if (strings[0].equalsIgnoreCase("set")){
                String warpName = strings[1];
                Location location = player.getLocation();
                yWarps.get().getWarpData().set("pWarps." + warpName + ".WORLD", location.getWorld().getName());
                yWarps.get().getWarpData().set("pWarps." + warpName + ".X", location.getX());
                yWarps.get().getWarpData().set("pWarps." + warpName + ".Y", location.getY());
                yWarps.get().getWarpData().set("pWarps." + warpName + ".Z", location.getZ());
                yWarps.get().getWarpData().set("pWarps." + warpName + ".YAW", location.getYaw());
                yWarps.get().getWarpData().set("pWarps." + warpName + ".PITCH", location.getPitch());
                yWarps.get().getWarpData().set("pWarps." + warpName + ".UUID", player.getUniqueId().toString());
                try {
                    yWarps.get().saveWarps();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.sendMessage(ChatColor.GREEN + "Warp " + warpName + " set.");
                return true;
            }else if (strings[0].equalsIgnoreCase("del") || strings[0].equalsIgnoreCase("delete") ||
                    strings[0].equalsIgnoreCase("remove")) {
                if (yWarps.get().getWarpData().get("pWarps." + strings[1]) == null) {
                    commandSender.sendMessage(ChatColor.RED + "Warp not found.");
                    return true;
                }
                String owner = yWarps.get().getWarpData().getString("pWarps." + strings[1] + ".UUID");
                if (!player.getUniqueId().toString().equals((owner))){
                    commandSender.sendMessage(ChatColor.RED + "This is not your warp.");
                    return true;
                }
                yWarps.get().getWarpData().set("pWarps." + strings[1], null);
                player.sendMessage(ChatColor.GREEN + "Warp removed.");
            }

            else {
                commandSender.sendMessage(ChatColor.RED + "/pwarp <set,delete> <warp name>");
                return true;
            }
        }
        else {
            commandSender.sendMessage(ChatColor.RED + "/pwarp <warp name>");
            return true;
        }
        return true;
    }
}
