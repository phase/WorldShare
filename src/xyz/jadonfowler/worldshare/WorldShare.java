package xyz.jadonfowler.worldshare;

import java.io.*;
import java.net.*;
import org.bukkit.plugin.java.*;
import com.sun.net.httpserver.*;

public class WorldShare extends JavaPlugin {
    protected HttpServer server;
    public static File index = new File("plugins/WorldShare/index.html");

    @Override public void onEnable() {
        try {
            if (!index.exists()) createIndexFile();
            server = HttpServer.create(new InetSocketAddress(80), 0);
            //server.createContext("/", new WorldLandingHandler());
            server.createContext("/", new WorldDownloadHandler());
            server.setExecutor(null); // creates a default executor
            server.start();
            System.out.println("WorldShare Server started at port 80");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createIndexFile() throws Exception {
        getDataFolder().mkdir();
        index.createNewFile();
        FileOutputStream o = new FileOutputStream(index);
        PrintStream p = new PrintStream(o);
        p.println("<html><body>Go to <code>/world</code> to download the world</body>\n</html>");
        p.close();
        o.close();
    }

    @Override public void onDisable() {
        server.stop(0);
    }

    public static void updateWorldZip() {
        try {
            Zipper.zip("world", "world.zip");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}