package cn.smile.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

//标记一个channelhandler可以被多个channel安全的共享
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf in = (ByteBuf)msg;

        System.out.println("服务端接收到消息："+ in.toString(CharsetUtil.UTF_8));

        //接接收到的消息写给发送者
        ctx.write(in);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        //将未决消息flush到远程节点并关闭该channel
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){

        //打印堆栈异常
        cause.printStackTrace();

        //关闭该channel
        ctx.close();
    }
}
