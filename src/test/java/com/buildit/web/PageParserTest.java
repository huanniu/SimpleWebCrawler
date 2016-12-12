package com.buildit.web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PageParserTest {
    private final static String DOMAIN_URL = "http://www.test.com";

    @Test
    public void testParseInternalLinks() {
        Document document = Jsoup.parse("<html><a href='" + DOMAIN_URL + "'>test page</a></html>");
        PageParser parser = new PageParser(DOMAIN_URL, document);
        Page page = parser.parse();

        assertEquals(1, page.getInternalLinks().size());
        assertEquals(DOMAIN_URL, page.getInternalLinks().iterator().next());
    }

    @Test
    public void testParseExternalLinks() {
        Document document = Jsoup.parse("<html><a href='http://www.google.com'>Google</a></html>");
        PageParser parser = new PageParser(DOMAIN_URL, document);
        Page page = parser.parse();

        assertEquals(1, page.getExternalLinks().size());
        assertEquals("http://www.google.com", page.getExternalLinks().iterator().next());
    }

    @Test
    public void testParseImages() {
        Document document = Jsoup.parse("<html><img src='smile.png' alt='Smile'></html>");
        PageParser parser = new PageParser(DOMAIN_URL, document);
        Page page = parser.parse();

        assertEquals(1, page.getImages().size());
        assertEquals("smile.png", page.getImages().iterator().next());
    }
}
