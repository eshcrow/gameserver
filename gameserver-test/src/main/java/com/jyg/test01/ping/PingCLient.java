package com.jyg.test01.ping;

import java.io.IOException;

import com.jyg.net.ProtoProcessor;
import com.jyg.net.ProtoResponse;
import com.jyg.proto.p_sm_scene.p_scene_sm_response_pong;
import com.jyg.proto.p_sm_scene.p_sm_scene_request_ping;
import com.jyg.startup.InnerClient;
import com.jyg.timer.Timer;
import com.jyg.timer.TimerCallBack;
import com.jyg.timer.TimerTrigger;

import io.netty.channel.Channel;

/**
 * Hello world!
 *
 */
public class PingCLient {
	public static void main(String[] args) throws Exception {

		ProtoProcessor<p_scene_sm_response_pong> pongProcessor = new ProtoProcessor<p_scene_sm_response_pong>(
				p_scene_sm_response_pong.getDefaultInstance()) {

			@Override
			public void processProtoMessage(p_scene_sm_response_pong msg, ProtoResponse response) {
				System.out.println("receive pong msg");
				// response.writeMsg(p_scene_sm_response_pong.newBuilder());
				
			}

		};

		final InnerClient client = new InnerClient();

		client.registerSendEventIdByProto(1, p_sm_scene_request_ping.class);
		client.registerSocketEvent(2, pongProcessor);

		Channel channel = client.connect("localhost", 8080);

		TimerTrigger tigger = new TimerTrigger();
		tigger.addTimer(new Timer(10, System.currentTimeMillis(), 1000, channel) {

			public void call() {
				this.writeAndFlush(p_sm_scene_request_ping.newBuilder());
			}
		});

		tigger.tickTigger();

		// client.close();
	}
}
