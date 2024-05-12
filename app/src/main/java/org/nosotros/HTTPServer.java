package org.nosotros;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import org.nosotros.http.util.Request;
import org.nosotros.http.util.Response;
import org.nosotros.http.util.UrlLink;

public class HTTPServer implements Runnable {

    static ExecutorService executor = java.util.concurrent.Executors.newFixedThreadPool(10);
    private static final int PORT = 80;

    @Override
    public void run() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(PORT);

            while (true) {
                final Socket client = server.accept();
                client.setSoTimeout(0);

                executor.execute(() -> {
                    try {
                        final InputStream inputStream = client.getInputStream();
                        final OutputStream outputStream = client.getOutputStream();

                        Request request = new Request(inputStream);

                        Response response = UrlLink.handle(request);

                        outputStream.write(response.getBytes());
                        outputStream.close();
                        client.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                executor.shutdown();
                server.close();
            } catch (IOException e) {
            }
        }
    }
}
