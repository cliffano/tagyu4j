package com.cliffano.tagyu4j.util;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.easymock.classextension.EasyMock;

import com.cliffano.tagyu4j.exception.Tagyu4JException;
import com.cliffano.tagyu4j.util.ServiceManager;
import com.cliffano.tagyu4j.util.ServiceManagerImpl;

public class ServiceManagerImplTest extends TestCase {

    private ServiceManager serviceManager;
    private HttpClient httpClient;
    private HttpMethod httpMethod;

    protected void setUp() {
        httpClient = (HttpClient) EasyMock.createStrictMock(HttpClient.class);
        httpMethod = (HttpMethod) EasyMock.createStrictMock(HttpMethod.class);
        serviceManager = new ServiceManagerImpl(httpClient, httpMethod);
    }

    public void testExecuteSuccess() throws Exception {

        httpMethod.setURI(new URI("http://someserviceurl.com/tags%2Band%2Btagging", true));
        httpMethod.setFollowRedirects(true);
        EasyMock.expect(new Integer(httpClient.executeMethod(httpMethod))).andReturn(new Integer(1));
        EasyMock.expect(httpMethod.getResponseBodyAsString()).andReturn("some response");
        httpMethod.releaseConnection();

        EasyMock.replay(new Object[]{httpClient, httpMethod});

        String response = serviceManager.execute("http://someserviceurl.com/", "tags+and+tagging");
        assertEquals("some response", response);

        EasyMock.verify(new Object[]{httpClient, httpMethod});
    }

    public void testExecuteWithAuthenticationSuccess() throws Exception {

        httpMethod.setURI(new URI("http://someserviceurl.com/tags%2Band%2Btagging", true));
        httpMethod.setFollowRedirects(true);
        httpMethod.setRequestHeader("Authorization", "Basic c29tZXVzZXJuYW1lOnNvbWVwYXNzd29yZA==");
        EasyMock.expect(new Integer(httpClient.executeMethod(httpMethod))).andReturn(new Integer(1));
        EasyMock.expect(httpMethod.getResponseBodyAsString()).andReturn("some response");
        httpMethod.releaseConnection();

        EasyMock.replay(new Object[]{httpClient, httpMethod});

        serviceManager.setAuthentication("someusername", "somepassword");
        String response = serviceManager.execute("http://someserviceurl.com/", "tags+and+tagging");
        assertEquals("some response", response);

        EasyMock.verify(new Object[]{httpClient, httpMethod});
    }

    public void testExecuteWithInvalidServiceUrlGivesTagyu4JException() {

        serviceManager = new ServiceManagerImpl();
        try {
            serviceManager.execute("http://@*&^#!&*^$&$", "css");
            fail("Test with invalid service url should have failed at this point.");
        } catch (Tagyu4JException te) {
            assertEquals("Unable to get xml response string", te.getMessage());
        }
    }

    public void testExecuteFailureWithHttpClientThrowingHttpExceptionGivesTagyu4JException() throws Exception {
        testExecuteFailureWithExecuteMethodThrowingException(new HttpException());
    }

    public void testExecuteFailureWithHttpClientThrowingIoExceptionGivesTagyu4JException() throws Exception {
        testExecuteFailureWithExecuteMethodThrowingException(new IOException());
    }

    private void testExecuteFailureWithExecuteMethodThrowingException(Throwable cause) throws Exception {
        httpMethod.setURI(new URI("http://someserviceurl.com/css", true));
        httpMethod.setFollowRedirects(true);
        EasyMock.expect(new Integer(httpClient.executeMethod(httpMethod))).andThrow(cause);
        httpMethod.releaseConnection();

        EasyMock.replay(new Object[]{httpClient, httpMethod});

        try {
            serviceManager.execute("http://someserviceurl.com/", "css");
            fail("Test should have failed at this point.");
        } catch (Tagyu4JException te) {
            assertEquals("Unable to get xml response string", te.getMessage());
        }

        EasyMock.verify(new Object[]{httpClient, httpMethod});
    }


    public void testExecuteSuccessWithProxy() throws Exception {

        HostConfiguration hostConfiguration = (HostConfiguration) EasyMock.createStrictMock(HostConfiguration.class);

        EasyMock.expect(httpClient.getHostConfiguration()).andReturn(hostConfiguration);
        hostConfiguration.setProxy("http://someproxyurl.com", 8080);
        httpMethod.setURI(new URI("http://someserviceurl.com/tags%2Band%2Btagging", true));
        httpMethod.setFollowRedirects(true);
        EasyMock.expect(new Integer(httpClient.executeMethod(httpMethod))).andReturn(new Integer(1));
        EasyMock.expect(httpMethod.getResponseBodyAsString()).andReturn("some response");
        httpMethod.releaseConnection();

        EasyMock.replay(new Object[]{httpClient, httpMethod});

        serviceManager.setProxy("http://someproxyurl.com", 8080);
        String response = serviceManager.execute("http://someserviceurl.com/", "tags+and+tagging");
        assertEquals("some response", response);

        EasyMock.verify(new Object[]{httpClient, httpMethod});
    }

    public void testExecuteSuccessWithAuthenticatedProxy() throws Exception {

        HostConfiguration hostConfiguration = (HostConfiguration) EasyMock.createStrictMock(HostConfiguration.class);
        HttpState httpState = (HttpState) EasyMock.createStrictMock(HttpState.class);

        EasyMock.expect(httpClient.getHostConfiguration()).andReturn(hostConfiguration);
        hostConfiguration.setProxy("http://someproxyurl.com", 8080);
        EasyMock.expect(httpClient.getState()).andReturn(httpState);
        httpState.setCredentials((AuthScope) EasyMock.isA(AuthScope.class), (UsernamePasswordCredentials) EasyMock.isA(UsernamePasswordCredentials.class));
        httpMethod.setURI(new URI("http://someserviceurl.com/tags%2Band%2Btagging", true));
        httpMethod.setFollowRedirects(true);
        EasyMock.expect(new Integer(httpClient.executeMethod(httpMethod))).andReturn(new Integer(1));
        EasyMock.expect(httpMethod.getResponseBodyAsString()).andReturn("some response");
        httpMethod.releaseConnection();

        EasyMock.replay(new Object[]{httpClient, httpMethod});

        serviceManager.setProxy("http://someproxyurl.com", 8080, "someusername", "somepassword");
        String response = serviceManager.execute("http://someserviceurl.com/", "tags+and+tagging");
        assertEquals("some response", response);

        EasyMock.verify(new Object[]{httpClient, httpMethod});
    }
}
