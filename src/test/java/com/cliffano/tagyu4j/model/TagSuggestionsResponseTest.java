package com.cliffano.tagyu4j.model;

import java.util.ArrayList;
import java.util.List;

import com.cliffano.tagyu4j.model.Tag;
import com.cliffano.tagyu4j.model.TagSuggestionsResponse;

import junit.framework.TestCase;

public class TagSuggestionsResponseTest extends TestCase {

    public void testTagSuggestionsResponseKeepsMemberFields() {

        List tags = new ArrayList();
        tags.add(new Tag("fencing", "rel", "http://someservice.com/fencing"));
        TagSuggestionsResponse response = new TagSuggestionsResponse(tags, "sports");
        assertEquals("sports", response.getCategory());
        assertEquals(tags, response.getTags());
        Tag tag = (Tag) response.getTags().get(0);
        assertEquals("fencing", tag.getValue());
        assertEquals("rel", tag.getRelation());
        assertEquals("http://someservice.com/fencing", tag.getHref());
    }
}
