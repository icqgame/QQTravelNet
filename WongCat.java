
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WongCat {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(80);
        while (true) {
            Socket s = ss.accept();
            new Thread(()->{


                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    FileInputStream bis = null;
                    String line = br.readLine();
                    String[] request = line.split(" ");
                    String path = "";
                    path = request[1].substring(1);
                    System.out.println(path);
                    if (path.equals("")) {
                        path = "web/index.html";

                    }

                    if (!path.startsWith("web")) {
                        path = "web/"+path;
                    }
                    bis = new FileInputStream(path);


                    OutputStream os = s.getOutputStream();
                    os.write("HTTP/1.1 200 OK\r\n".getBytes());

                    os.write("Content-Type:text/html\r\n".getBytes());
                   
                    os.write("\r\n".getBytes());
                    int len = -1;
                    byte[] buf = new byte[1024];
                    while ((len = bis.read(buf)) != -1) {
                        os.write(buf, 0, len);
                    }
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        //bis.close();

    }
}
