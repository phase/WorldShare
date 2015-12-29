package xyz.jadonfowler.worldshare;

import java.io.*;
import com.sun.net.httpserver.*;

public class WorldDownloadHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        // zip header
        Headers h = t.getResponseHeaders();
        //h.add("Content-Type", "application/zip");
        h.add("Content-Disposition", "filename=world.zip");
        // update the world
        WorldShare.updateWorldZip();
        // put file into bytes
        File file = new File("world.zip");
        byte[] bytearray = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(bytearray, 0, bytearray.length);
        // send the response.
        t.sendResponseHeaders(200, file.length());
        OutputStream os = t.getResponseBody();
        os.write(bytearray, 0, bytearray.length);
        os.close();
        bis.close();
    }
}