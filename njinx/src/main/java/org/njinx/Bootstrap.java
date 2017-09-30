package org.njinx;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author WallaceTang
 */
public class Bootstrap {
    public static final String NJINX_ROOT = "C:\\Users\\wallacetang\\tmp";
    public static final int port = 8060;
    public static boolean shutdown = false;

    public static void main(String[] args) {

        System.out.println("started njinx");

        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (!shutdown) {

                Socket socket = serverSocket.accept();
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();

                Request request = new Request(in);
                request.parse();

                Response response = new Response(out);
                response.setRequest(request);
                response.sendStaticResource();

                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
