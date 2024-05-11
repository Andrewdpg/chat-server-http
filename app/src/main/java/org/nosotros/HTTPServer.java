package org.nosotros;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.nosotros.http.util.Request;
import org.nosotros.http.util.Response;
import org.nosotros.http.util.UrlLink;

public class HTTPServer implements Runnable {

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(80);
    
            while (true) {
                try {
                    Socket client = server.accept();
    
                    OutputStream out = client.getOutputStream();
    
                    String block = readBlock(client);
                    if (block == null)
                        continue;
                    Request request = new Request(block);
    
                    if (request.getPath().equals("/favicon.ico"))
                        continue;
                    if (request.getHeaders().containsKey("Content-Length")) {
                        request.setBody(readBlock(client));
                    }
    
                    Response response = UrlLink.handle(request);
    
                    out.write(response.getBytes());
                    out.close();
                    client.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readBlock(Socket client) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String request = "";
        String line;
        while ((line = in.readLine()) != null && !line.isEmpty()) {
            request += line + "\n";
        }

        if (request.isEmpty()) {
            client.close();
            return null;
        }
        return request;
    }
}

