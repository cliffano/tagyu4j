package com.mbledug.tagyu4j.util;

import com.mbledug.tagyu4j.exception.Tagyu4JException;
import com.mbledug.tagyu4j.model.RelatedTagsResponse;
import com.mbledug.tagyu4j.model.TagSuggestionsResponse;

import junit.framework.TestCase;

public class ResponseParserTest extends TestCase {

    private static final String INVALID_XML = "blahblahblah";
    private static final String ERROR_XML = "<error>You are requesting too often</error>";
    private static final String TAG_SUGGESTIONS_XML_SUCCESS_RESPONSE = "<suggestions><tag rel=\"related\" href=\"http://tagyu.com/api/tag/tagging\">tagging</tag><tag rel=\"related\" href=\"http://tagyu.com/api/tag/tags\">tags</tag><category>Technology</category></suggestions>";
    private static final String RELATED_TAGS_XML_SUCCESS_RESPONSE = "<related for=\"css\"><tag rel=\"related\" href=\"http://tagyu.com/api/tag/webdesign\">webdesign</tag><tag rel=\"related\" href=\"http://tagyu.com/api/tag/design\">design</tag></related>";

    private ResponseParserImpl mParser;

    protected void setUp() {
        mParser = new ResponseParserImpl();
    }

    public void testParseTagSuggestionsFailureInvalidXml() {

        try {
            TagSuggestionsResponse response = mParser.parseTagSuggestions(INVALID_XML);
            fail("Test with invalid xml should have failed at this point. " +
                    "Unexpected response: " + response);
        } catch (Tagyu4JException te) {
            // Tagyu4JException is thrown as expected
        }
    }

    public void testParseTagSuggestionsFailureErrorXml() {

        try {
            TagSuggestionsResponse response = mParser.parseTagSuggestions(ERROR_XML);
            fail("Test with invalid xml should have failed at this point. " +
                    "Unexpected response: " + response);
        } catch (Tagyu4JException te) {
            // Tagyu4JException is thrown as expected
        }
    }

    public void testParseTagSuggestionsSuccessXmlResponse() {

        try {
            TagSuggestionsResponse response = mParser.parseTagSuggestions(TAG_SUGGESTIONS_XML_SUCCESS_RESPONSE);
            assertNotNull(response);
        } catch (Tagyu4JException te) {
            fail("Tagyu4JException should not occur: " + te.getMessage());
        }
    }

    public void testParseRelatedTagsSuccessXmlResponse() {

        try {
            RelatedTagsResponse response = mParser.parseRelatedTags(RELATED_TAGS_XML_SUCCESS_RESPONSE);
            assertNotNull(response);
        } catch (Tagyu4JException te) {
            fail("Tagyu4JException should not occur: " + te.getMessage());
        }
    }
}
