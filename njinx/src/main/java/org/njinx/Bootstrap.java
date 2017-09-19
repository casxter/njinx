package org.njinx;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author WallaceTang
 */
public class Bootstrap {
    public static final String NJINX_PATH = "C:\\Users\\wallacetang\\tmp";
    public static final int port = 8060;
    public static boolean shutdown = false;

    public static void main(String[] args) {
        System.out.println("start njinx");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (!shutdown) {
                System.out.println("accept");
                Socket socket = serverSocket.accept();
                OutputStream out = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(out);
                dos.writeUTF("njinx");
                dos.flush();
                dos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
