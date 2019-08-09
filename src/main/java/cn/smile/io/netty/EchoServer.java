package cn.smile.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * EchoServerHandler 实现了业务逻辑；
 * main()方法引导了服务器；
 * 引导过程中所需要的步骤如下：
 * 创建一个ServerBootstrap 的实例以引导和绑定服务器；
 * 创建并分配一个NioEventLoopGroup 实例以进行事件的处理，如接受新连接以及读/
 * 写数据；
 * 指定服务器绑定的本地的InetSocketAddress；
 * 使用一个EchoServerHandler 的实例初始化每一个新的Channel；
 * 调用ServerBootstrap.bind()方法以绑定服务器。
 *
 * */
public class EchoServer {
    private final int port;

    public EchoServer(int port){
        this.port = port;
    }

    public static void main(String[] args)throws Exception{
//        if(args.length != 1){
//            System.err.println("Usage:"+EchoServer.class.getSimpleName()+"<port>");
//        }


        //设置端口的值
//        int port = Integer.parseInt(args[0]);

        int port = 8888;
        //调用服务器的start方法
        new EchoServer(port).start();
    }

    public void start() throws Exception{
        final EchoServerHandler serverHandler = new EchoServerHandler();
        //创建EventLoopGroup

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            //创建server-bootStrap
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    //指定所使用的NIO传输channel
                    .channel(NioServerSocketChannel.class)
                    //使用指定的端口设置套接字地址
                    .localAddress(new InetSocketAddress(port))
                    //添加一个EchoServerHandler的自channel的channelPipeline
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //EchoServerHandler被标记为@Shareable,所以我们可以总是使用相同的实例
                            socketChannel.pipeline().addLast(serverHandler);
                        }
                    });
            //异步的绑定服务器，调用sync()放入发阻塞等到知道绑定完成
            ChannelFuture f = b.bind().sync();
            //获取channel的CloseFuture，并且阻塞当前线程知道他完成
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            //关闭EventLoopGroup，释放所有资源
            group.shutdownGracefully().sync();
        }
    }

}
