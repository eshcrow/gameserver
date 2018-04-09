package com.jyg.consumers;

import java.util.HashMap;
import java.util.Map;

import com.jyg.bean.LogicEvent;
import com.jyg.enums.EventType;
import com.jyg.net.Processor;
import com.jyg.timer.Timer;
import com.jyg.timer.TimerCallBack;
import com.jyg.net.EventDispatcher;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * created by jiayaoguang at 2017年12月6日
 */
public class EventConsumer implements EventHandler<LogicEvent>, WorkHandler<LogicEvent> {


	private final EventDispatcher dispatcher = EventDispatcher.getInstance();
	

	public EventConsumer() {

	}

	public void onEvent(LogicEvent event, long sequence, boolean endOfBatch) throws Exception {
		this.onEvent(event);
	}
	
	private int eventTimes = 0;
	
	public void onEvent(LogicEvent event) throws Exception {

		// System.out.println(event.getChannel());
		try {
			switch (event.getChannelEventType()) {
				
				case ACTIVE:
					dispatcher.as_on_game_client_come(event);
					break;
				case INACTIVE:
					dispatcher.as_on_game_client_leave(event);
					break;
				case HTTP_MSG_COME:
					dispatcher.httpProcess(event);
					//五秒后关闭
					dispatcher.addTimer(new Timer(1 , 10*1000L, event.getChannel(),new TimerCallBack() {
						public void call(Timer timer) {
							if(timer.getChannel().isOpen()){
								System.out.println("out of time,just close it");
								timer.getChannel().close();
							}
						}
					}));
					break;
				case ON_MESSAGE_COME:
					dispatcher.webSocketProcess(event);
					break;
				case RPC_MSG_COME:
					dispatcher.socketProcess(event);
					break;
				default:
					throw new Exception("unknown channelEventType <"+event.getChannelEventType()+">");
			}
		}finally {
			eventTimes++;
			if (eventTimes == 10000) {
				eventTimes = 0;
				dispatcher.loop();
			}
		}
		
		
	}
	
}
