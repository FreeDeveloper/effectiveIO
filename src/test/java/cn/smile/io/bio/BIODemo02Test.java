package cn.smile.io.bio;

import org.junit.Test;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 客户端只是保持连接，不进行读操作。那么可以看到服务器端write()方法写入次数为65513时就产生阻塞，
 * 因为write()方法将数据首先写入网卡缓冲区，（不同电脑的网卡缓冲区容量不同），
 * 当写满时就会产生阻塞。每次写入"HelloWorld"，10个字节，写入了65513次，总共655130字节，
 * 这表明该电脑网卡缓冲区容量约为655130字节。

 * */
public class BIODemo02Test {
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
        String msg = "HelloWorld";
        for (int i = 1; i <= 1000000; i++) {
            out.write(msg.getBytes());
            System.out.println("写入次数：" + i + "，字节总数：" + (msg.length() * i));
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

        // 保持连接
        while (true);
    }
}
