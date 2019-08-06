package cn.smile.io.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * 分析该模式的缺点：
 * 缺点1：每有一个用户请求，就会创建一个新的线程为之提供服务。当用户请求量特别巨大，线程数量就会随之增大，继而内存的占用增大，所以不适用于高并发、高访问的场景。
 * 缺点2：线程特别多，不仅占用内存开销，也会占用大量的CPU开销，因为CPU要做线程调度。
 * 缺点3：如果一个用户仅仅是连入操作，并且长时间不做其他操作，会产生大量闲置线程。会使CPU做无意义的空转，降低整体性能。
 * 缺点4：这个模型会导致真正需要被处理的线程（用户请求）不能被及时处理。
 *
 * */
public class MyMutiThreadBioServer {

    public static void main(String [] args){
        ServerSocket socketServer = null;

        {
            try {
                socketServer = new ServerSocket(8888);
                System.out.println("服务器已启动，监听端口："+8888);

                Socket socket = null;
                while(true){
                    socket = socketServer.accept();
                    new Thread(new TimeServerHandler(socket)).start();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(socketServer!=null){
                    System.out.println("the time server close");
                    try {
                        socketServer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
