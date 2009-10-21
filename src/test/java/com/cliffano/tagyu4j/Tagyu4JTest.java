package com.cliffano.tagyu4j;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.easymock.classextension.EasyMock;

import com.cliffano.tagyu4j.Tagyu4J;
import com.cliffano.tagyu4j.model.RelatedTagsResponse;
import com.cliffano.tagyu4j.model.TagSuggestionsResponse;
import com.cliffano.tagyu4j.util.ResponseParser;
import com.cliffano.tagyu4j.util.ServiceManager;

public class Tagyu4JTest extends TestCase {

    private Tagyu4J tagyu4J;
    private ServiceManager serviceManager;
    private ResponseParser responseParser;

    protected void setUp() {
        serviceManager = (ServiceManager) EasyMock.createStrictMock(ServiceManager.class);
        responseParser = (ResponseParser) EasyMock.createStrictMock(ResponseParser.class);
    }

// Tests via live service is commented out while Tagyu is down.
// http://kalsey.com/2006/08/and_thanks_for_all_the_fish/
//    public void testGetTagSuggestionsWithTextViaLiveService() {
//        mTagyu4J = new Tagyu4J();
//        mTagyu4J.setAuthentication(DataFixture.TAGYU_USERNAME, DataFixture.TAGYU_PASSWORD);
//        TagSuggestionsResponse response = mTagyu4J.getTagSuggestions(DataFixture.REQUEST_TAG_TEXT);
//        assertTagSuggestionsResponse(response);
//    }
//
//    public void testGetTagSuggestionsWithUrlViaLiveService() {
//        mTagyu4J = new Tagyu4J();
//        mTagyu4J.setAuthentication(DataFixture.TAGYU_USERNAME, DataFixture.TAGYU_PASSWORD);
//        TagSuggestionsResponse response = mTagyu4J.getTagSuggestions(DataFixture.REQUEST_TAG_URL);
//        assertTagSuggestionsResponse(response);
//    }
//
//    public void testGetRelatedTagsViaLiveService() {
//        mTagyu4J = new Tagyu4J();
//        mTagyu4J.setAuthentication(DataFixture.TAGYU_USERNAME, DataFixture.TAGYU_PASSWORD);
//        RelatedTagsResponse response = mTagyu4J.getRelatedTags("music");
//        assertRelatedTagsResponse(response);
//    }

    public void testGetTagSuggestionsViaMockService() {

        tagyu4J = new Tagyu4J(serviceManager, responseParser);
        ArrayList tags = new ArrayList();

        EasyMock.expect(serviceManager.execute("http://www.tagyu.com/api/suggest/", "css")).andReturn("some response");
        EasyMock.expect(responseParser.parseTagSuggestions("some response")).andReturn(new TagSuggestionsResponse(tags, "web design"));

        EasyMock.replay(new Object[]{serviceManager, responseParser});

        TagSuggestionsResponse response = tagyu4J.getTagSuggestions("css");
        assertEquals("web design", response.getCategory());
        assertEquals(tags, response.getSuggestedTags());

        EasyMock.verify(new Object[]{serviceManager, responseParser});
    }

    public void testGetRelatedTagsViaMockService() {

        tagyu4J = new Tagyu4J(serviceManager, responseParser);
        ArrayList tags = new ArrayList();

        EasyMock.expect(serviceManager.execute("http://www.tagyu.com/api/tag/", "css")).andReturn("some response");
        EasyMock.expect(responseParser.parseRelatedTags("some response")).andReturn(new RelatedTagsResponse(tags, "css"));

        EasyMock.replay(new Object[]{serviceManager, responseParser});

        RelatedTagsResponse response = tagyu4J.getRelatedTags("css");
        assertEquals("css", response.getRequestTag());
        assertEquals(tags, response.getRelatedTags());

        EasyMock.verify(new Object[]{serviceManager, responseParser});
    }

    public void testGetRelatedTagsWithProxyViaMockService() {

        tagyu4J = new Tagyu4J(serviceManager, responseParser);
        ArrayList tags = new ArrayList();

        serviceManager.setProxy("http://someproxy.com", 8080);
        EasyMock.expect(serviceManager.execute("http://www.tagyu.com/api/tag/", "css")).andReturn("some response");
        EasyMock.expect(responseParser.parseRelatedTags("some response")).andReturn(new RelatedTagsResponse(tags, "css"));

        EasyMock.replay(new Object[]{serviceManager, responseParser});

        tagyu4J.setProxy("http://someproxy.com", 8080);
        RelatedTagsResponse response = tagyu4J.getRelatedTags("css");
        assertEquals("css", response.getRequestTag());
        assertEquals(tags, response.getRelatedTags());

        EasyMock.verify(new Object[]{serviceManager, responseParser});
    }

    public void testGetRelatedTagsWithAuthenticatedProxyViaMockService() {

        tagyu4J = new Tagyu4J(serviceManager, responseParser);
        ArrayList tags = new ArrayList();

        serviceManager.setProxy("http://someproxy.com", 8080, "someusername", "somepassword");
        EasyMock.expect(serviceManager.execute("http://www.tagyu.com/api/tag/", "css")).andReturn("some response");
        EasyMock.expect(responseParser.parseRelatedTags("some response")).andReturn(new RelatedTagsResponse(tags, "css"));

        EasyMock.replay(new Object[]{serviceManager, responseParser});

        tagyu4J.setProxy("http://someproxy.com", 8080, "someusername", "somepassword");
        RelatedTagsResponse response = tagyu4J.getRelatedTags("css");
        assertEquals("css", response.getRequestTag());
        assertEquals(tags, response.getRelatedTags());

        EasyMock.verify(new Object[]{serviceManager, responseParser});
    }
}
