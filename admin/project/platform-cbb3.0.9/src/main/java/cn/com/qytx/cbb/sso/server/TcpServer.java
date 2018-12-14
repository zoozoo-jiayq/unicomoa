package cn.com.qytx.cbb.sso.server;
/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */


import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import cn.com.qytx.cbb.sso.server.cache.SSOCache;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * An TCP server used for performance tests.
 * 
 * It does nothing fancy, except receiving the messages, and counting the number of
 * received messages.
 * 
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class TcpServer extends IoHandlerAdapter {
	private Log log = LogFactory.getLog(this.getClass());
	//验证token类型
	private final String MESSAGE_TYPE_CHECK = "check";
	//心跳请求类型
	private final String MESSAGE_TYPE_HEART = "heart";

	/**
     * {@inheritDoc}
     */
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
        session.close(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void messageReceived(IoSession session, Object mes) throws Exception {
        Map<String,String> message = (Map<String,String>) mes;
		if(message!=null){
			String token = message.get("sso_token");
			UserInfo u = (UserInfo) SSOCache.getInstance().read(token);
			if(u==null){
				log.info("---------------------鉴权失败......"+token);
			}else{
				if(message.get("type").equals(MESSAGE_TYPE_CHECK)){
					log.info("---------------------接收鉴权请求......"+token);
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("userId", u.getUserId());
					map.put("userName", u.getUserName());
					map.put("phone", u.getPhone());
					map.put("groupId", u.getGroupId());
					map.put("loginName", u.getLoginName());
					map.put("loginPass", u.getOrialPassword());
					map.put("sex", u.getSex());
					map.put("workNo", u.getWorkNo());
					map.put("job", u.getJob());
					map.put("companyId", u.getCompanyId());
					session.write(map);
				}else if(message.get("type").equals(MESSAGE_TYPE_HEART)){
					log.info("---------------------接收心跳请求......"+token);
				}
			}
		}
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sessionClosed(IoSession session) throws Exception {
    	System.out.println("Session Opened...");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sessionCreated(IoSession session) throws Exception {
    	System.out.println("Session Opened...");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
    	System.out.println("Session Opened...");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sessionOpened(IoSession session) throws Exception {
    	System.out.println("Session Opened...");
    }

    /**
     * Create the TCP server
     */
    public TcpServer(int port) throws IOException {
        NioSocketAcceptor acceptor = new NioSocketAcceptor();
        
        acceptor.setHandler(this);
        
        DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
        ProtocolCodecFilter coderFilter = new ProtocolCodecFilter(new ObjectSerializationCodecFactory());
        chain.addLast("objectSeria", coderFilter);
        chain.addLast("exector", new ExecutorFilter(Executors.newCachedThreadPool()));

        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        
        SocketSessionConfig scfg = acceptor.getSessionConfig();

        acceptor.bind(new InetSocketAddress(port));
        System.out.println("SSO Server started...");
    }
}
