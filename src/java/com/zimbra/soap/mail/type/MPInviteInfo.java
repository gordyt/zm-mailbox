/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011 Zimbra, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * ***** END LICENSE BLOCK *****
 */

package com.zimbra.soap.mail.type;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import com.zimbra.common.soap.MailConstants;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"timezones", "calendarReplies", "inviteComponents"})
public class MPInviteInfo {

    // Valid values - "appt" and "task"
    @XmlAttribute(name=MailConstants.A_CAL_ITEM_TYPE, required=true)
    private final String calItemType;

    @XmlElement(name=MailConstants.E_CAL_TZ, required=false)
    private List<CalTZInfo> timezones = Lists.newArrayList();

    @XmlElementWrapper(name=MailConstants.E_CAL_REPLIES, required=false)
    @XmlElement(name=MailConstants.E_CAL_REPLY, required=false)
    private List<CalendarReply> calendarReplies = Lists.newArrayList();

    @XmlElement(name=MailConstants.E_INVITE_COMPONENT, required=false)
    private List<InviteComponent> inviteComponents = Lists.newArrayList();

    /**
     * no-argument constructor wanted by JAXB
     */
    @SuppressWarnings("unused")
    private MPInviteInfo() {
        this((String) null);
    }

    public MPInviteInfo(String calItemType) {
        this.calItemType = calItemType;
    }

    public void setTimezones(Iterable <CalTZInfo> timezones) {
        this.timezones.clear();
        if (timezones != null) {
            Iterables.addAll(this.timezones,timezones);
        }
    }

    public MPInviteInfo addTimezone(CalTZInfo timezone) {
        this.timezones.add(timezone);
        return this;
    }

    public void setCalendarReplies(Iterable <CalendarReply> calendarReplies) {
        this.calendarReplies.clear();
        if (calendarReplies != null) {
            Iterables.addAll(this.calendarReplies,calendarReplies);
        }
    }

    public MPInviteInfo addCalendarReply(CalendarReply calendarReply) {
        this.calendarReplies.add(calendarReply);
        return this;
    }

    public void setInviteComponents(
                    Iterable <InviteComponent> inviteComponents) {
        this.inviteComponents.clear();
        if (inviteComponents != null) {
            Iterables.addAll(this.inviteComponents,inviteComponents);
        }
    }

    public MPInviteInfo addInviteComponent(InviteComponent inviteComponent) {
        this.inviteComponents.add(inviteComponent);
        return this;
    }

    public String getCalItemType() { return calItemType; }
    public List<CalTZInfo> getTimezones() {
        return Collections.unmodifiableList(timezones);
    }
    public List<CalendarReply> getCalendarReplies() {
        return Collections.unmodifiableList(calendarReplies);
    }
    public List<InviteComponent> getInviteComponents() {
        return Collections.unmodifiableList(inviteComponents);
    }

    public Objects.ToStringHelper addToStringInfo(
                Objects.ToStringHelper helper) {
        return helper
            .add("calItemType", calItemType)
            .add("timezones", timezones)
            .add("calendarReplies", calendarReplies)
            .add("inviteComponents", inviteComponents);
    }

    @Override
    public String toString() {
        return addToStringInfo(Objects.toStringHelper(this))
                .toString();
    }
}
