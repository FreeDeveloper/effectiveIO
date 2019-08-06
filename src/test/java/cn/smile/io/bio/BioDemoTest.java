package cn.smile.io.bio;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 小结：ServerSocket和Socket
 *
 * 四个方法accept()、connect()、read()、write()都是阻塞的。
 *
 * 服务器端：accept()
 * 客户端：connect()
 *
 * 服务器端的InputStream（读） == 客户端的OutputStream（写）
 * 服务器端的OutputStream（写） == 客户端的InputStream（读）
 *
 * */
public class BioDemoTest {
    /**
     * 服务器端
     *
     * @throws Exception
     */
    @Test
    public void testAccept() throws Exception {
        // 创建ServerSocket对象
        ServerSocket ss = new ServerSocket();
        // 绑定端口
        ss.bind(new InetSocketAddress(6666));
        // 提示用户
        System.out.println("等待客户端连接……");
        // 获取连接(accept方法是阻塞方法，直到服务器获取连接)
        Socket sk = ss.accept();
        // 获取输出流
        OutputStream out = sk.getOutputStream();
        // 写入数据
        for (int i = 1; i <= 1000000; i++) {
            out.write("HelloWorld".getBytes());
            System.out.println("写入次数：" + i);
        }
        System.out.println("往客户端写入了内容。");

        // 关闭套接字对象
        sk.close();
        ss.close();
    }

    /**
     * 客户端
     */
    @Test
    public void connect() throws Exception {
        // 创建Socket对象
        Socket sk = new Socket();
        // 请求连接（connect方法是阻塞方法，直到建立连接或抛出异常）
        sk.connect(new InetSocketAddress("127.0.0.1", 6666));
        // 提示用户，连接成功
        System.out.println("连接已经建立。");
        // 获取输入流
        InputStream in = sk.getInputStream();
        // 读取数据
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String nextLine = "";
        StringBuffer buffer = new StringBuffer();
        while ((nextLine = br.readLine()) != null) {
            buffer.append(nextLine);
            System.out.println(nextLine);
        }
        System.out.println("总长度：" + buffer.toString().length() + "B");
    }

}
