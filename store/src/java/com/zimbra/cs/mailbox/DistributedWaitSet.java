package com.zimbra.cs.mailbox;

import java.util.List;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;

import com.google.gson.Gson;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.mailbox.MailboxListener.ChangeNotification;
import com.zimbra.cs.session.PendingModifications;
import com.zimbra.cs.session.Session;

public class DistributedWaitSet {

	private static RedissonClient redisson = RedissonClientHolder.getInstance().getRedissonClient();
	private static Gson gson = new Gson();
	private static ChangeNotification changeNotification;
	
	public static long publishChangeNotification(String topic, ChangeNotification notification) {
		//needs to get scencial values to avoid circular references, is that is possible gson api not needed
		RTopic<String> rTopic = redisson.getTopic(topic);
		String json = "";
		 json = gson.toJson(notification);
		return rTopic.publish(json);
	}
	
	public static ChangeNotification getChangeNotificationMessage(String topic) {
		RTopic<String> rTopic = redisson.getTopic(topic);
		rTopic.addListener(new MessageListener<String>() {
			
			@Override
			public void onMessage(String arg0, String arg1) {
				// TODO Auto-generated method stub
				ZimbraLog.mailbox.info("DistributedWaitSet.getChangeNotificationMessage arg0::"+arg0 +" arg1::"+arg1);
				changeNotification = gson.fromJson(arg1, ChangeNotification.class);
				ZimbraLog.mailbox.info("changeNotification::"+changeNotification );
			}
		});
		return changeNotification;
	}

	//to test
	public static void main(String[] args) {
		getChangeNotificationMessage("test");
		
		
		publishChangeNotification("test", new ChangeNotification(null,null,null,0,null,0));
	}
}
