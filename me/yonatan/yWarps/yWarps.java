package me.yonatan.yWarps;

import me.yonatan.yWarps.cmds.GoCmd;
import me.yonatan.yWarps.cmds.PublicWarpCmd;
import me.yonatan.yWarps.cmds.WarpHelp;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class yWarps extends JavaPlugin {

    private static yWarps yWarps;
    private FileConfiguration warpData;
    private File dataFile;

    @Override
    public void onEnable(){
        getCommand("warp").setExecutor(new GoCmd());
        getCommand("pwarp").setExecutor(new PublicWarpCmd());
        getCommand("warphelp").setExecutor(new WarpHelp());
        yWarps = this;

        dataFile = new File(getDataFolder(), "warps.yml");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        warpData = YamlConfiguration.loadConfiguration(dataFile);

    }

    @Override
    public void onDisable(){
        yWarps = null;
    }

    public static me.yonatan.yWarps.yWarps get() {
        return yWarps;
    }

    public FileConfiguration getWarpData() {
        return warpData;
    }

    public void saveWarps() throws IOException {
        getWarpData().save(dataFile);
    }
}
