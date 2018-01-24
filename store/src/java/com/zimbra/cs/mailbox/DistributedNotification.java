package com.zimbra.cs.mailbox;

import com.zimbra.cs.session.PendingModifications;


public class DistributedNotification {
	@SuppressWarnings("rawtypes")
	private PendingModifications pendingModifications;
	private int lastChangeId;


	@SuppressWarnings("rawtypes")
	public DistributedNotification(PendingModifications pendingModifications, int lastChangeId) {
		super();
		this.pendingModifications = pendingModifications;
		this.lastChangeId = lastChangeId;
	}

	public DistributedNotification() {
		super();
	}

	@SuppressWarnings("rawtypes")
	public PendingModifications getPendingModifications() {
		return pendingModifications;
	}

	public int getLastChangeId() {
		return lastChangeId;
	}
}
