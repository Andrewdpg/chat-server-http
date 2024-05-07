
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

import config.Urls;
import util.Request;
import util.Response;

public class SimpleHTTPServer {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        ServerSocket server = new ServerSocket(80);

        while (true) {
            Socket client = server.accept();

            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            Request request = new Request(readBlock(in));
            if (request.isPost()) {
                request.setBody(readBlock(in));
            }

            Response response = Urls.handle(request);

            OutputStream out = client.getOutputStream();
            out.write(response.getBytes("UTF-8"));
            out.close();
            client.close();
        }

    }

    public static String readBlock(BufferedReader in) throws IOException{
        StringBuilder requestData = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            requestData.append(line).append("\r\n");
            if (line.isEmpty()) {
                // Se encontró el delimitador, salir del bucle
                break;
            }
        }
        return requestData.toString();
    }
}

