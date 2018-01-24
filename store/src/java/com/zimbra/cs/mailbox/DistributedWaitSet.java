package com.zimbra.cs.mailbox;

import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;

public class DistributedWaitSet {

	private static RedissonClient redisson = RedissonClientHolder.getInstance().getRedissonClient();
	private DistributedNotification distributedNotification;

	public long publishDistributedNotification(String topic, DistributedNotification distributedNotification) {
		RTopic<DistributedNotification> rTopic = redisson.getTopic(topic);
		return rTopic.publish(distributedNotification);
	}

	@SuppressWarnings("rawtypes")
	public DistributedNotification getDistributedNotification(String topic) {
		RTopic<DistributedNotification> rTopic = redisson.getTopic(topic);
		rTopic.addListener(new MessageListener<DistributedNotification>() {
			@Override
			public void onMessage(String arg0, DistributedNotification arg1) {
				// TODO Auto-generated method stub
				distributedNotification = arg1;
			}
		});
		return distributedNotification;
	}

}
