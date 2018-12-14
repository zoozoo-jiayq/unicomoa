package cn.com.qytx.platform.event;

import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;



import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.AbstractApplicationEventMulticaster;

public class AsyncApplicationEventMulticaster extends AbstractApplicationEventMulticaster {
	
	private Executor executor = Executors.newFixedThreadPool(10);

	@SuppressWarnings("unchecked")
	@Override
	public void multicastEvent(final ApplicationEvent event) {
		// TODO Auto-generated method stub
		for(final ApplicationListener listener:getApplicationListeners(event)){
			executor.execute(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					listener.onApplicationEvent(event);
				}
			});
		}
	}

}
