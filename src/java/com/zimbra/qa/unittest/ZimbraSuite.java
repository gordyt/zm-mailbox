/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014 Zimbra, Inc.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software Foundation,
 * version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 * ***** END LICENSE BLOCK *****
 */

package com.zimbra.qa.unittest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.runner.JUnitCore;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.extension.ExtensionUtil;
import com.zimbra.qa.unittest.server.TestCalDavImportServer;
import com.zimbra.qa.unittest.server.TestDataSourceServer;
import com.zimbra.qa.unittest.server.TestDocumentServer;
import com.zimbra.qa.unittest.server.TestNotificationServer;
import com.zimbra.qa.unittest.server.TestPop3ImportServer;

/**
 * Complete unit test suite for the Zimbra code base.
 *
 * @author bburtin
 *
 */
public class ZimbraSuite extends TestSuite
{
    private static final List<Class<? extends TestCase>> sClasses = new ArrayList<Class<? extends TestCase>>();

    static {
        sClasses.add(TestWaitSet.class);
        sClasses.add(TestWaitSetRequest.class);
        sClasses.add(TestUtilCode.class);
        sClasses.add(TestEmailUtil.class);
        sClasses.add(TestOutOfOffice.class);
        sClasses.add(TestDbUtil.class);
        sClasses.add(TestUnread.class);
        sClasses.add(TestTags.class);
        sClasses.add(TestItemCache.class);
        sClasses.add(TestFolders.class);
        sClasses.add(TestFolderACLCache.class);
        sClasses.add(TestSpellCheck.class);
        sClasses.add(TestAuthentication.class);
        sClasses.add(TestAccount.class);
        sClasses.add(TestConversion.class);
        sClasses.add(TestMailItem.class);
        sClasses.add(TestConcurrency.class);
        sClasses.add(TestFolderFilterRules.class);
        sClasses.add(TestTagFilterRules.class);
        sClasses.add(TestPop3Import.class);
        sClasses.add(TestPop3ImportServer.class);
        sClasses.add(TestFilter.class);
        sClasses.add(TestPop3ImapAuth.class);
        sClasses.add(TestContacts.class);
        sClasses.add(TestTaskScheduler.class);
        sClasses.add(TestSendAndReceive.class);
        sClasses.add(TestConnectionPool.class);
        sClasses.add(TestLmtp.class);
        sClasses.add(TestSmtpClient.class);
        sClasses.add(TestScheduledTaskManager.class);
        sClasses.add(TestDataSource.class);
        sClasses.add(TestDataSourceServer.class);
        sClasses.add(TestPurge.class);
        sClasses.add(TestImap.class);
        sClasses.add(TestImapImport.class);
        sClasses.add(TestImapOneWayImport.class);
        sClasses.add(TestNotification.class);
        sClasses.add(TestNotificationServer.class);
        sClasses.add(TestMaxMessageSize.class);
        sClasses.add(TestMetadata.class);
        sClasses.add(TestSoap.class);
        sClasses.add(TestBlobInputStream.class);
        sClasses.add(TestRedoLog.class);
        sClasses.add(TestFileUtil.class);
        sClasses.add(TestIndex.class);
        sClasses.add(TestParsedMessage.class);
        sClasses.add(TestUserServlet.class);
        sClasses.add(TestWsdlServlet.class);
        sClasses.add(TestMimeDetect.class);
        sClasses.add(TestDocument.class);
        sClasses.add(TestDocumentServer.class);
        sClasses.add(TestFileUpload.class);
        sClasses.add(TestFilterExisting.class);
        sClasses.add(TestSpam.class);
        sClasses.add(TestMailSender.class);
        sClasses.add(TestGetMsg.class);
        sClasses.add(TestMountpoint.class);
        sClasses.add(TestZClient.class);
        sClasses.add(TestLog.class);
        sClasses.add(TestSaveDraft.class);
        sClasses.add(TestServerStats.class);
        sClasses.add(TestJaxbProvisioning.class);
        sClasses.add(TestAclPush.class);
        sClasses.add(TestCalDav.class);
        sClasses.add(TestCalDavImportServer.class);
        sClasses.add(TestContactCSV.class);
        sClasses.add(TestStoreManager.class);
        sClasses.add(TestSoapHarvest.class);
        sClasses.add(TestBlobDeduper.class);
        sClasses.add(TestDistListACL.class);
        sClasses.add(TestCookieReuse.class);
        sClasses.add(TestCountObjects.class);
        sClasses.add(TestDomain.class);
        sClasses.add(TestMinusOperator.class);
        sClasses.add(TestInvite.class);
        sClasses.add(TestSearchJunkTrash.class);
        sClasses.add(TestCommunityIntegration.class);
        sClasses.add(TestJaxb.class);
        sClasses.add(TestCollectConfigServletsAccess.class);
        //sClasses.add(TestDLMembership.class);
        sClasses.add(TestTwoFactorAuth.class);
        sClasses.add(TestTwoFactorAuthAdmin.class);
        sClasses.add(TestAppSpecificPasswords.class);
        sClasses.add(TestAddZmgDevice.class);
        sClasses.add(TestGetGcmSenderId.class);
        sClasses.add(TestTrustedToken.class);
        sClasses.add(TestShareNotifications.class);
    }

    /**
     * Used by extensions to add additional tests to the main test suite.
     */
    public static void addTest(Class<? extends TestCase> clazz) {
        sClasses.add(clazz);
    }

    public static void removeTest(Class<? extends TestCase> clazz) {
        sClasses.remove(clazz);
    }

    public static TestResults runUserTests(List<String> testNames) throws ServiceException {
        List<Class<? extends TestCase>> tests = new ArrayList<Class<? extends TestCase>>();

        for (String testName : testNames) {
            if (testName.indexOf('.') < 0) {
                // short name...check the suite
                boolean found = false;
                for (Class<? extends TestCase> c : ZimbraSuite.sClasses) {
                    if (testName.equals(c.getSimpleName())) {
                        tests.add(c);
                        found = true;
                    }
                }
                if (!found) {
                    ZimbraLog.test.warn("Could not find test %s.  Make sure it's registered with %s.",
                        testName, ZimbraSuite.class.getName());
                }
            } else {
                Class<? extends TestCase> testClass;
                try {
                    testClass = Class.forName(testName).asSubclass(TestCase.class);
                } catch (ClassNotFoundException e) {
                    try {
                        testClass = ExtensionUtil.findClass(testName).asSubclass(TestCase.class);
                    } catch (ClassNotFoundException e2) {
                        throw ServiceException.FAILURE("Error instantiating test " + testName, e2);
                    }
                }
                tests.add(testClass);
            }
        }

        return runTestsInternal(tests);
    }

    /**
     * Runs the entire test suite and writes the output to the specified
     * <code>OutputStream</code>.
     */
    public static TestResults runTestSuite() {
        return runTestsInternal(sClasses);
    }

    private static TestResults runTestsInternal(Collection<Class<? extends TestCase>> testClasses) {
        JUnitCore junit = new JUnitCore();
        junit.addListener(new TestLogger());
        TestResults results = new TestResults();
        junit.addListener(results);

        Class<?>[] classArray = new Class<?>[testClasses.size()];
        testClasses.toArray(classArray);
        junit.run(classArray);
        return results;
    }
}
