package org.gameserver.auth;

import org.gameserver.auth.module.AuthModule;
import org.gameserver.auth.processor.LoginHttpProcessor;
import org.gameserver.auth.processor.TokenReceiveSuccessProtoProcessor;
import org.gameserver.auth.processor.TokenSendHttpProcessor;
import org.gameserver.auth.useless.LoginHtmlHttpProcessor;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.jyg.enums.ProtoEnum;
import com.jyg.net.HttpService;
import com.jyg.proto.p_auth_sm.p_auth_sm_request_send_token;
import com.jyg.startup.GameServerBootstarp;

/**
 * Hello world!
 *
 */
public class AuthBootstarp
{
    public static void main ( String[] args ) throws Exception 
    {
    	
    	Injector injector = Guice.createInjector(new AuthModule());
    	
    	
    	GameServerBootstarp bootstarp = new GameServerBootstarp();
        
        bootstarp.registerHttpEvent("/index", injector.getInstance(TokenSendHttpProcessor.class));
        
        bootstarp.registerHttpEvent("/login", new LoginHttpProcessor());
        
        bootstarp.registerSendEventIdByProto(ProtoEnum.P_AUTH_SM_REQUEST_SEND_TOKEN.getEventId(), 
        		p_auth_sm_request_send_token.class);
        
        bootstarp.addService(new HttpService(8080));
        
        bootstarp.registerSocketEvent(ProtoEnum.P_SM_AUTH_RESPONSE_RECEIVE_TOKEN.getEventId(), 
        		injector.getInstance(TokenReceiveSuccessProtoProcessor.class));
        
        bootstarp.start();
    }
}
