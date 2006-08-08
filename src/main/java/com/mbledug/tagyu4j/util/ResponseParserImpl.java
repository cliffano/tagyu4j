/**
 * Copyright (c) 2005, Cliffano Subagio
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of Mbledug nor the names of its contributors
 *     may be used to endorse or promote products derived from this software
 *     without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.mbledug.tagyu4j.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.mbledug.tagyu4j.exception.Tagyu4JException;
import com.mbledug.tagyu4j.model.RelatedTagsResponse;
import com.mbledug.tagyu4j.model.Tag;
import com.mbledug.tagyu4j.model.TagSuggestionsResponse;

/**
 * ResponseParser parses the xml response String retrieved from Tagyu service.
 * @author Cliffano Subagio
 */
public class ResponseParserImpl implements ResponseParser {

    /**
     * Create a ResponseParser instance.
     */
    public ResponseParserImpl() {
    }

    /**
     * Parse the xml response String for tag suggestions response.
     * @param xmlString the response String to parse
     * @return response which contains suggested tags
     * @throws Tagyu4JException when there's a failure response from Tagyu
     *             service or there's an error with parsing the xml response
     */
    public final TagSuggestionsResponse parseTagSuggestions(
            final String xmlString) throws Tagyu4JException {

        Document doc = parseDocument(xmlString);
        Element root = doc.getRootElement();
        errorCheck(root);

        String category = null;
        List tagSuggestions = new ArrayList();

        for (Iterator it = root.elementIterator(); it.hasNext();) {
            Element tagElement = (Element) it.next();
            if ("tag".equals(tagElement.getQualifiedName())) {
                Tag tag = new Tag(
                        tagElement.getText(),
                        tagElement.attributeValue("rel"),
                        tagElement.attributeValue("href"));
                tagSuggestions.add(tag);
            } else if ("category".equals(tagElement.getQualifiedName())) {
                category = tagElement.getText();
            }
        }

        return new TagSuggestionsResponse(tagSuggestions, category);
    }


    /**
     * Parse the xml response String for related tags response.
     * @param xmlString the response String to parse
     * @return response which contains related tags
     * @throws Tagyu4JException when there's a failure response from Tagyu
     *             service or there's an error with parsing the xml response
     */
    public final RelatedTagsResponse parseRelatedTags(
            final String xmlString) throws Tagyu4JException {

        Document doc = parseDocument(xmlString);
        Element root = doc.getRootElement();
        errorCheck(root);

        String requestTag = root.attributeValue("for");
        List tagSuggestions = new ArrayList();

        for (Iterator it = root.elementIterator(); it.hasNext();) {
            Element tagElement = (Element) it.next();
            if ("tag".equals(tagElement.getQualifiedName())) {
                Tag tag = new Tag(
                        tagElement.getText(),
                        tagElement.attributeValue("rel"),
                        tagElement.attributeValue("href"));
                tagSuggestions.add(tag);
            }
        }

        return new RelatedTagsResponse(tagSuggestions, requestTag);
    }


    /**
     * Check the root element for error tag. Throws Tagyu4JException
     * if the root element is an error tag.
     * @param root the root element on the xml string
     * @throws Tagyu4JException when the root element is error tag.
     */
    private void errorCheck(final Element root) throws Tagyu4JException {

        // capture error message when there's a failure
        if ("error".equals(root.getQualifiedName())) {
            String errorMessage = root.getText();
            throw new Tagyu4JException(errorMessage);
        }
    }

    /**
     * Parse a Document out of an xml string.
     * @param xmlString the xml string to parse from
     * @return the DOcument from the xml string
     * @throws Tagyu4JException when there's a problem with
     *         parsing xml response string.
     */
    private Document parseDocument(final String xmlString)
            throws Tagyu4JException {

        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xmlString);
        } catch (DocumentException de) {
            throw new Tagyu4JException("Unable to parse response string:\n"
                    + xmlString, de);
        }
        return doc;
    }
}
