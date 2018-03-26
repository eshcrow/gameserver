package com.jyg.net;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.jyg.bean.LogicEvent;

/**
 * created by jiayaoguang at 2017年12月16日
 */
public abstract class ProtoProcessor<T extends GeneratedMessageV3> implements Processor<T> {

	private final Parser<? extends GeneratedMessageV3> parser;
	private final Class clazz;

	public ProtoProcessor(Class<GeneratedMessageV3> protoClazz) throws InstantiationException, IllegalAccessException {
		parser = protoClazz.newInstance().getParserForType();
		clazz = protoClazz;
	}

	public ProtoProcessor(GeneratedMessageV3 messageLite) {
		parser = messageLite.getParserForType();
		clazz = messageLite.getClass();
	}
	
	public String getProtoClassName() {
		return clazz.getName();
	}

	public final Parser<? extends MessageLite> getProtoParser() {
		return parser;
	}
	
	

	public void process(LogicEvent<T> event) {
//		System.out.println("eventid : "+event.getEventId());
		ProtoResponse response = new ProtoResponse(event.getChannel());
		processProtoMessage(event.getData(),response);
	}
	
	public abstract void processProtoMessage(T msg ,ProtoResponse response);
	

}
