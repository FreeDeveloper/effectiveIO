package cn.smile.io.netty;

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PlainOioServer {
    public void serve(){
        try {
            final ServerSocket socket = new ServerSocket(8888);
            for(;;){
                final Socket clentSocket = socket.accept();

                System.out.println("Acceped connection from"+clentSocket);

                new Thread(new Runnable() {
                    public void run() {
                        OutputStream out;
                        try {
                            out = clentSocket.getOutputStream();
                            out.write("hi!".getBytes(CharsetUtil.UTF_8));
                            out.flush();
                            clentSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            try {
                                clentSocket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
