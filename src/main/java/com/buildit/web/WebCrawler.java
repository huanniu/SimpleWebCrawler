package com.buildit.web;

import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WebCrawler {
    private String domainURL;
    private PageParser parser;
    private DocumentFetcher fetcher;
    private Set<String> visitedLinks;
    private Map<String, Page> knownPages;

    public WebCrawler(String domainURL, PageParser parser, DocumentFetcher fetcher) {
        this.domainURL = domainURL;
        this.parser = parser;
        this.fetcher = fetcher;
        this.visitedLinks = new HashSet<String>();
        this.knownPages = new HashMap<String, Page>();
    }

    public Page crawl() {
        Document home = this.fetcher.fetcher(domainURL);
        visitedLinks.add(domainURL);
        Page homePage = parser.parse(home);
        knownPages.put(domainURL, homePage);
        populateChildren(homePage);

        return homePage;
    }

    private void populateChildren(Page homePage) {
        for (String link: homePage.getInternalLinks()) {
            if (!visitedLinks.contains(link)) {
                visitedLinks.add(link);

                Document child = this.fetcher.fetcher(link);
                Page childPage = parser.parse(child);
                knownPages.put(link, childPage);
                homePage.addChild(childPage);

                populateChildren(childPage);
            } else {
                if (knownPages.get(link) != null) {
                    homePage.addChild(knownPages.get(link));
                }
            }
        }
    }
}
