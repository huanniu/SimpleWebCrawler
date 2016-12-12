package com.buildit.web;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class PageParser {
    private String domainURL;
    private Document document;

    public PageParser(String domainURL, Document document) {
        this.domainURL = domainURL;
        this.document = document;
    }

    public Page parse() {
        Page page = new Page();

        parseLinks(page);
        parseImages(page);

        return page;
    }

    private void parseLinks(Page page) {
        for (Element link: document.select("a")) {
            String href = link.absUrl("href");
            if (href.startsWith(domainURL)) {
                page.addInternalLink(href);
            } else {
                page.addExternalLink(href);
            }
        }
    }

    private void parseImages(Page page) {
        for (Element img: document.select("img")) {
            page.addImage(img.attr("src"));
        }
    }
}
