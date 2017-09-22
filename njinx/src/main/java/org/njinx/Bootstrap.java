package org.njinx;


import java.io.*;
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

                InputStream inputStream = socket.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(inputStream);
                byte[] buffer = new byte[1024];

                StringBuilder sb = new StringBuilder();
                while (bis.read(buffer) != -1){
                    sb.append(new String(buffer,"UTF-8"));
                }

                System.out.println(sb.toString());

                dos.writeUTF("njinx");
                dos.flush();
                dos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
