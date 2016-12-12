package com.buildit;

import com.buildit.web.DocumentFetcher;
import org.jsoup.Jsoup;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SitemapCreatorTest {
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
    public void createSitemapFile() {
        SitemapCreator creator = new SitemapCreator(DOMAIN_URL, mockedFetcher);
        creator.create();
    }
}
