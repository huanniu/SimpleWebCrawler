package com.buildit.web;

import org.jsoup.Jsoup;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WebCrawlerTest {
    private final static String DOMAIN_URL = "http://www.test.com/";

    private final static String HOME_HTML =  "<html>" +
                                                "<img src='logo.png'>" +
                                                "<a href='page1'>page 1</a>" +
                                                "<a href='page2'>page 2</a>" +
                                                "<a href='http://www.google.com'>Google</a>" +
                                                "<a href='http://www.linkedIn.com'>LinkedIn</a>" +
                                                "<a href='http://www.facebook.com'>Facebook</a>" +
                                            "</html>";

    private final static String PAGE1_HTML = "<html>" +
                                                "<a href='page3'>page 3</a>" +
                                                "<a href='/'>home</a>" +
                                            "</html>";

    private final static String PAGE2_HTML = "<html>" +
                                                "<a href='http://www.google.com'>Google again</a>" +
                                                "<a href='page3'>page 3</a>" +
                                            "</html>";

    private final static String PAGE3_HTML = "<html>" +
                                                "<a href='page2'>page 2</a>" +
                                            "</html>";

    private DocumentFetcher mockedFetcher;

    @Before
    public void setup() {
        this.mockedFetcher = mock(DocumentFetcher.class);
        when(mockedFetcher.fetcher(DOMAIN_URL)).thenReturn(Jsoup.parse(HOME_HTML, DOMAIN_URL));
        when(mockedFetcher.fetcher(DOMAIN_URL + "page1")).thenReturn(Jsoup.parse(PAGE1_HTML, DOMAIN_URL));
        when(mockedFetcher.fetcher(DOMAIN_URL + "page2")).thenReturn(Jsoup.parse(PAGE2_HTML, DOMAIN_URL));
        when(mockedFetcher.fetcher(DOMAIN_URL + "page3")).thenReturn(Jsoup.parse(PAGE3_HTML, DOMAIN_URL));
    }

    @Test
    public void testCrawlWebsite() {
        WebCrawler crawler = new WebCrawler(DOMAIN_URL, new PageParser(), mockedFetcher);
        Page page = crawler.crawl();

        assertEquals(2, page.getInternalLinks().size());
        assertEquals(3, page.getExternalLinks().size());
        assertEquals(1, page.getImages().size());
        assertEquals(2, page.getChildren().size());

        for (Page childPage: page.getChildren()) {
            if (childPage.getInternalLinks().size() == 2) { // This would be Page 1
                assertEquals(2, childPage.getInternalLinks().size());
            } else { // This would be Page 2
                assertEquals(1, childPage.getInternalLinks().size());
                assertEquals(1, childPage.getExternalLinks().size());

                // Check Page 3
                Page page3 = childPage.getChildren().iterator().next();
                assertEquals(DOMAIN_URL + "page2", page3.getInternalLinks().iterator().next());
            }
        }
    }
}
