package com.qoqoa.tagyu4j.model;

import junit.framework.TestCase;

public class TagTest extends TestCase {

    public void testTagKeepsMemberFields() {

        Tag tag = new Tag("basketball", "rel", "http://someservice.com/basketball");
        assertEquals("basketball", tag.getValue());
        assertEquals("rel", tag.getRelation());
        assertEquals("http://someservice.com/basketball", tag.getHref());
    }
}
