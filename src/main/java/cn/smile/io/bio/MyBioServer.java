package cn.smile.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MyBioServer {
    public static void main(String [] args){

        Socket socket = null;

        try {
            //服务端监听5555端口
            ServerSocket serverSocket = new ServerSocket(5555);
            System.out.println("服务端已启动~");

            System.out.println("开始等待客户端连接~");
            socket = serverSocket.accept();
            System.out.println("获取客户端连接~");

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String lineStr;

            System.out.println("准备处理客户端数据~");
            while(!(lineStr = input.readLine()).equals("exit")){
                System.out.println(lineStr);
            }


            System.out.println("服务端已关闭~");


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
