package com.zimbra.soap.mail.message;

import javax.xml.bind.annotation.XmlRootElement;

import com.zimbra.common.soap.MailConstants;

@XmlRootElement(name=MailConstants.E_CLEAR_SEARCH_HISTORY_REQUEST)
public class ClearSearchHistoryRequest {

    public ClearSearchHistoryRequest() {}
}
