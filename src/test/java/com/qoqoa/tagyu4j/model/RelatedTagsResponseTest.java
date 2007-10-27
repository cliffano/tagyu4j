package com.qoqoa.tagyu4j.model;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class RelatedTagsResponseTest extends TestCase {

    public void testRelatedTagsResponseKeepsMemberFields() {

        List tags = new ArrayList();
        tags.add(new Tag("red", "rel", "http://someservice.com/red"));
        tags.add(new Tag("green", "rel", "http://someservice.com/green"));
        RelatedTagsResponse response = new RelatedTagsResponse(tags, "blue");
        assertEquals("blue", response.getRequestTag());
        assertEquals(tags, response.getRelatedTags());
        Tag tag1 = (Tag) response.getRelatedTags().get(0);
        assertEquals("red", tag1.getValue());
        assertEquals("rel", tag1.getRelation());
        assertEquals("http://someservice.com/red", tag1.getHref());
        Tag tag2 = (Tag) response.getRelatedTags().get(1);
        assertEquals("green", tag2.getValue());
        assertEquals("rel", tag2.getRelation());
        assertEquals("http://someservice.com/green", tag2.getHref());
    }
}
