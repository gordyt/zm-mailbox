/*
 * ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1
 * 
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 ("License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.zimbra.com/license
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and limitations
 * under the License.
 * 
 * The Original Code is: Zimbra Collaboration Suite Server.
 * 
 * The Initial Developer of the Original Code is Zimbra, Inc.
 * Portions created by Zimbra are Copyright (C) 2006 Zimbra, Inc.
 * All Rights Reserved.
 * 
 * Contributor(s): 
 * 
 * ***** END LICENSE BLOCK *****
 */
package com.zimbra.cs.dav;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.zimbra.cs.account.Account;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.mailbox.Mailbox;
import com.zimbra.cs.mailbox.Mailbox.OperationContext;
import com.zimbra.soap.Element;

public class DavContext {
	private HttpServletRequest  mReq;
	private HttpServletResponse mResp;
	private OperationContext mOpCtxt;
	private Account mAuthAccount;
	private String mUri;
	private String mUser;
	private int mStatus;
	
	public DavContext(HttpServletRequest req, HttpServletResponse resp) {
		mReq = req;  mResp = resp;
		mUri = req.getPathInfo().toLowerCase();
		mStatus = HttpServletResponse.SC_OK;
		/*
		java.util.Enumeration headers = req.getHeaderNames();
		while (headers.hasMoreElements()) {
			String hdr = (String)headers.nextElement();
			java.util.Enumeration val = req.getHeaders(hdr);
			while (val.hasMoreElements())
				ZimbraLog.dav.debug(hdr+": "+(String)val.nextElement());
		}
		*/
	}
	
	public HttpServletRequest getRequest() {
		return mReq;
	}
	
	public HttpServletResponse getResponse() {
		return mResp;
	}

	public void setOperationContext(Account authUser) {
		mAuthAccount = authUser;
		mOpCtxt = new Mailbox.OperationContext(authUser);
	}
	
	public OperationContext getOperationContext() {
		return mOpCtxt;
	}

	public Account getAuthAccount() {
		return mAuthAccount;
	}
	
	public String getUri() {
		return mUri;
	}

	public void setUser(String user) {
		mUser = user;
	}
	
	public String getUser() {
		return mUser;
	}

	public int getStatus() {
		return mStatus;
	}
	
	public void setStatus(int s) {
		mStatus = s;
	}
	
	public enum Depth {
		ZERO, ONE, INFINITY
	}
	
	public Depth getDepth() {
		String hdr = mReq.getHeader(DavProtocol.HEADER_DEPTH);
		if (hdr == null)
			return Depth.ZERO;
		if (hdr.equals("0"))
			return Depth.ZERO;
		if (hdr.equals("1"))
			return Depth.ONE;
		if (hdr.equalsIgnoreCase("infinity"))
			return Depth.INFINITY;
		
		ZimbraLog.dav.info("invalid depth: "+hdr);
		return Depth.ZERO;
	}
	
	public boolean hasRequestMessage() {
		String hdr = mReq.getHeader(DavProtocol.HEADER_CONTENT_LENGTH);
		return (hdr != null && Integer.parseInt(hdr) > 0);
	}
	
	public Element getRequestMessage() throws DavException {
		try {
			if (hasRequestMessage()) {
				Element ret = Element.parseXML(mReq.getInputStream());
				//ZimbraLog.dav.debug(mReq.getMethod() + " " + mUri + "\n" + ret.toString());
				return ret;
			}
		} catch (DocumentException e) {
			throw new DavException("unable to parse request message", HttpServletResponse.SC_BAD_REQUEST, e);
		} catch (IOException e) {
			throw new DavException("unable to read input", HttpServletResponse.SC_BAD_REQUEST, e);
		}
		return null;
	}
}
