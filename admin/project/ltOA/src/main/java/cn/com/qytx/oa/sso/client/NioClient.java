package cn.com.qytx.oa.sso.client;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class NioClient {

	private String ip;
	private int port;
	public NioClient(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;
	}
	
	public Object send(Object input){
		Object out = null;
		NioSocketConnector connector = new NioSocketConnector(); 
		connector.getFilterChain().addLast( "codec",new ProtocolCodecFilter(
		        new ObjectSerializationCodecFactory())); //设置编码过滤器 
		SocketSessionConfig cfg = connector.getSessionConfig();
	    cfg.setUseReadOperation(true);
		ConnectFuture cf = connector.connect( 
		new InetSocketAddress(ip, port));//建立连接 
		cf.awaitUninterruptibly();//等待连接创建完成 
		cf.getSession().write(input).awaitUninterruptibly();//发送消息
		
		ReadFuture readFuture = cf.getSession().read();
		if (readFuture.awaitUninterruptibly(30000,TimeUnit.MILLISECONDS)) {
			out =  readFuture.getMessage();
		}else{
		  //超时
		}
		cf.getSession().close(true);
		cf.getSession().getCloseFuture().awaitUninterruptibly();//等待连接断开 
		connector.dispose(); 
		return out; 
	}
	
}
