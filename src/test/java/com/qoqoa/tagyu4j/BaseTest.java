package com.qoqoa.tagyu4j;

import java.util.Iterator;

import junit.framework.TestCase;

import com.qoqoa.tagyu4j.model.RelatedTagsResponse;
import com.qoqoa.tagyu4j.model.Tag;
import com.qoqoa.tagyu4j.model.TagSuggestionsResponse;

public abstract class BaseTest extends TestCase {

    protected final void assertTagSuggestionsResponse(final TagSuggestionsResponse response) {
        assertNotNull(response.getCategory());
        assertNotNull(response.getSuggestedTags());
        for (Iterator it = response.getSuggestedTags().iterator(); it.hasNext();) {
            Tag tag = (Tag)it.next();
            assertNotNull(tag.getValue());
            assertNotNull(tag.getRelation());
            assertNotNull(tag.getHref());
        }
    }

    protected final void assertRelatedTagsResponse(final RelatedTagsResponse response) {
        assertNotNull(response.getRequestTag());
        assertNotNull(response.getRelatedTags());
        for (Iterator it = response.getRelatedTags().iterator(); it.hasNext();) {
            Tag tag = (Tag)it.next();
            assertNotNull(tag.getValue());
            assertNotNull(tag.getRelation());
            assertNotNull(tag.getHref());
        }
    }
}
