package xyz.jadonfowler.worldshare;

import java.io.*;
import com.sun.net.httpserver.*;

public class WorldLandingHandler implements HttpHandler {
    @Override public void handle(HttpExchange t) throws IOException {
        Headers h = t.getResponseHeaders();
        h.add("Content-Type", "text/html");
        // put file into bytes
        byte[] bytearray = new byte[(int) WorldShare.index.length()];
        FileInputStream fis = new FileInputStream(WorldShare.index);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(bytearray, 0, bytearray.length);
        // send the response.
        t.sendResponseHeaders(200, WorldShare.index.length());
        OutputStream os = t.getResponseBody();
        os.write(bytearray, 0, bytearray.length);
        os.close();
        bis.close();
    }
}