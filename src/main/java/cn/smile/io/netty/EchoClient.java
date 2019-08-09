package cn.smile.io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;


/**
 * 为进行事件处理分配了一个NioEventLoopGroup 实例，其中事件处理包括创建新的
 * 连接以及处理入站和出站数据；
 * 为服务器连接创建了一个InetSocketAddress 实例；
 * 当连接被建立时，一个EchoClientHandler 实例会被安装到（该Channel 的）
 * ChannelPipeline 中；
 * 在一切都设置完成后，调用Bootstrap.connect()方法连接到远程节点；
 *
 * */
public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host,int port){
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture f =  b.connect().sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String [] args) throws Exception{
//        if (args.length != 2) {
//            System.err.println(
//                    "Usage: " + EchoClient.class.getSimpleName() +
//                            " <host> <port>");
//            return;
//        }

//        String host= args[0];
//        int port = Integer.parseInt(args[1]);

        String host= "localhost";
        int port = 8888;

        new EchoClient(host,port).start();
    }
}
