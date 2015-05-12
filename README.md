Tagyu4J [![http://travis-ci.org/cliffano/tagyu4j](https://img.shields.io/travis/cliffano/tagyu4j.svg)](http://travis-ci.org/cliffano/tagyu4j)
------

Tagyu4J is a Java library for [Tagyu REST web services](http://tagyu.com/tools/rest/). 

Usage
-----

__getTagSuggestions__

Getting tags for the text "imagine all the people".

	Tagyu4J tagyu4J = new Tagyu4J();
	TagSuggestionsResponse response = tagyu4J.getTagSuggestions("imagine all the people");
	String category = response.getCategory();
	List suggestedTags = response.getSuggestedTags();
	Iterator it = suggestedTags.iterator();
	while (it.hasNext()) {
	    Tag tag = (Tag)it.next();
	    String value = tag.getValue();
	    String relation = tag.getRelation();
	    String href = tag.getHref();
	}

Getting tags for the URL www.nba.com.

	Tagyu4J tagyu4J = new Tagyu4J();
	TagSuggestionsResponse response = tagyu4J.getTagSuggestions("www.nba.com");
	String category = response.getCategory();
	List suggestedTags = response.getSuggestedTags();
	Iterator it = suggestedTags.iterator();
	while (it.hasNext()) {
	    Tag tag = (Tag)it.next();
	    String value = tag.getValue();
	    String relation = tag.getRelation();
	    String href = tag.getHref();
	}

__getRelatedTags__

Getting tags related to "music".

	Tagyu4J tagyu4J = new Tagyu4J();
	RelatedTagsResponse response = tagyu4J.getRelatedTags("music");
	List relatedTags = response.getSuggestedTags();
	Iterator it = relatedTags.iterator();
	while (it.hasNext()) {
	    Tag tag = (Tag)it.next();
	    String value = tag.getValue();
	    String relation = tag.getRelation();
	    String href = tag.getHref();
	}

__authentication__

There's a limit of 1 request / minute for anonymous users. You can register for a soft cap limit of 1000 requests / day. To authenticate with your registered account, set Tagyu4J instance with Tagyu username and password.

	Tagyu4J tagyu4J = new Tagyu4J();
	tagyu4J.setAuthentication("username", "password");

Colophon
--------

Follow [@cliffano](http://twitter.com/cliffano) on Twitter.
