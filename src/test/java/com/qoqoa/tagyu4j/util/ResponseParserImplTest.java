package com.qoqoa.tagyu4j.util;

import java.util.List;

import junit.framework.TestCase;

import com.qoqoa.tagyu4j.exception.Tagyu4JException;
import com.qoqoa.tagyu4j.model.RelatedTagsResponse;
import com.qoqoa.tagyu4j.model.Tag;
import com.qoqoa.tagyu4j.model.TagSuggestionsResponse;

public class ResponseParserImplTest extends TestCase {

    private static final String TAG_SUGGESTIONS_SUCCESS_RESPONSE =
        "<suggestions>"
        + "<tag rel=\"related\" href=\"http://tagyu.com/api/tag/database\">database</tag>"
        + "<tag rel=\"related\" href=\"http://tagyu.com/api/tag/webserver\">webserver</tag>"
        + "<category>Technology</category></suggestions>";

    private static final String RELATED_TAGS_SUCCESS_RESPONSE =
        "<related for=\"css\">"
        + "<tag rel=\"related\" href=\"http://tagyu.com/api/tag/html\">html</tag>"
        + "<tag rel=\"related\" href=\"http://tagyu.com/api/tag/javascript\">javascript</tag>"
        + "</related>";

    private ResponseParserImpl mParser;

    protected void setUp() {
        mParser = new ResponseParserImpl();
    }

    public void testParseTagSuggestionsWithSuccessResponseXmlString() {
        TagSuggestionsResponse response = mParser.parseTagSuggestions(TAG_SUGGESTIONS_SUCCESS_RESPONSE);

        assertEquals("Technology", response.getCategory());
        List tags = response.getSuggestedTags();

        Tag tag1 = (Tag) tags.get(0);
        assertEquals("database", tag1.getValue());
        assertEquals("related", tag1.getRelation());
        assertEquals("http://tagyu.com/api/tag/database", tag1.getHref());

        Tag tag2 = (Tag) tags.get(1);
        assertEquals("webserver", tag2.getValue());
        assertEquals("related", tag2.getRelation());
        assertEquals("http://tagyu.com/api/tag/webserver", tag2.getHref());
    }

    public void testParseTagSuggestionsWithInvalidResponseXmlStringGivesTagyu4JException() {

        try {
            TagSuggestionsResponse response = mParser.parseTagSuggestions("<foobar>(&#($&(*&#@($&@#</foobar>");
            fail("Test with invalid xml should have failed at this point. " +
                    "Unexpected response: " + response);
        } catch (Tagyu4JException te) {
            assertEquals("Unable to parse response string:\n<foobar>(&#($&(*&#@($&@#</foobar>", te.getMessage());
        }
    }

    public void testParseTagSuggestionsWithErrorResponseXmlStringGivesTagyu4JException() {

        try {
            TagSuggestionsResponse response = mParser.parseTagSuggestions("<error>You are requesting too often</error>");
            fail("Test with invalid xml should have failed at this point. " +
                    "Unexpected response: " + response);
        } catch (Tagyu4JException te) {
            assertEquals("You are requesting too often", te.getMessage());
        }
    }

    public void testParseRelatedTagsWithSuccessResponseXmlString() {
        RelatedTagsResponse response = mParser.parseRelatedTags(RELATED_TAGS_SUCCESS_RESPONSE);

        assertEquals("css", response.getRequestTag());
        List tags = response.getRelatedTags();

        Tag tag1 = (Tag) tags.get(0);
        assertEquals("html", tag1.getValue());
        assertEquals("related", tag1.getRelation());
        assertEquals("http://tagyu.com/api/tag/html", tag1.getHref());

        Tag tag2 = (Tag) tags.get(1);
        assertEquals("javascript", tag2.getValue());
        assertEquals("related", tag2.getRelation());
        assertEquals("http://tagyu.com/api/tag/javascript", tag2.getHref());
    }

    public void testParseRelatedTagsWithInvalidResponseXmlStringGivesTagyu4JException() {

        try {
            RelatedTagsResponse response = mParser.parseRelatedTags("<foobar>(&#($&(*&#@($&@#</foobar>");
            fail("Test with invalid xml should have failed at this point. " +
                    "Unexpected response: " + response);
        } catch (Tagyu4JException te) {
            assertEquals("Unable to parse response string:\n<foobar>(&#($&(*&#@($&@#</foobar>", te.getMessage());
        }
    }

    public void testParseRelatedTagsWithErrorResponseXmlStringGivesTagyu4JException() {

        try {
            RelatedTagsResponse response = mParser.parseRelatedTags("<error>You are requesting too often</error>");
            fail("Test with invalid xml should have failed at this point. " +
                    "Unexpected response: " + response);
        } catch (Tagyu4JException te) {
            assertEquals("You are requesting too often", te.getMessage());
        }
    }
}
