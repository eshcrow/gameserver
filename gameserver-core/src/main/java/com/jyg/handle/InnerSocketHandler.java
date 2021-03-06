package com.jyg.handle;

import com.jyg.bean.LogicEvent;
import com.jyg.enums.EventType;
import com.jyg.util.GlobalQueue;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * created by jiayaoguang at 2018年4月9日
 */
public class InnerSocketHandler extends ChannelInboundHandlerAdapter {

	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
		Channel incoming = ctx.channel();
		System.out.println("Client:" + incoming.remoteAddress() + "在线");
		
		GlobalQueue.publicEvent(EventType.INNER_SOCKET_CONNECT_ACTIVE, null, ctx.channel() );
		
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
		Channel incoming = ctx.channel();
		System.out.println("Client:" + incoming.remoteAddress() + "掉线");
		
		GlobalQueue.publicEvent(EventType.INNER_SOCKET_CONNECT_INACTIVE, null, ctx.channel() );
		
		super.channelInactive(ctx);
	}

	
	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		Channel incoming = ctx.channel();
		System.out.println("Client:" + incoming.remoteAddress() + "异常");
		// 当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}
	
}

