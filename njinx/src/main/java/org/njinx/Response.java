package org.njinx;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author WallaceTang
 */
public class Response {

    private static final int BUFFER_SIZE = 2048;
    Request request;
    OutputStream output;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            //将web文件写入到OutputStream字节流中

//            output.write("HTTP/1.1 200 OK\r\n".getBytes(StandardCharsets.UTF_8));
//            output.write("Content-Type: text/html; charset=utf-8\r\n".getBytes(StandardCharsets.UTF_8));
//            output.write("\r\n".getBytes(StandardCharsets.UTF_8));
//            output.write("hi".getBytes(StandardCharsets.UTF_8));


            DataOutputStream dos = new DataOutputStream(output);

            File file = new File(Bootstrap.NJINX_ROOT, request.getFilePath());
            if (file.exists()) {
                Path path = Paths.get(request.getFilePath());
                String mineType = Files.probeContentType(path);


                dos.writeUTF("HTTP/1.1 200 OK\r\n");
                dos.writeUTF("Content-Type: " + mineType + "\r\n");
                dos.writeUTF("Content-Length: " + file.length() + "\r\n");
                dos.writeUTF("\r\n");


                fis = new FileInputStream(file);
                int ch = fis.read(bytes, 0, BUFFER_SIZE);
                while (ch != -1) {
                    dos.write(bytes, 0, ch);
                    ch = fis.read(bytes, 0, BUFFER_SIZE);
                    dos.flush();
                }
            } else {
                // file not found
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n"
                        + "Content-Length: 23\r\n" + "\r\n" + "<h1>File Not Found</h1>";
                dos.write(errorMessage.getBytes());
            }

            dos.close();
        } catch (Exception e) {
            // thrown if cannot instantiate a File object
            System.out.println(e.toString());
        } finally {
            if (fis != null)
                fis.close();
        }
    }
}
