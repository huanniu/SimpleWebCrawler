package com.buildit.web;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class PageParser {
    public Page parse(Document document) {
        Page page = new Page();

        parseLinks(page, document);
        parseImages(page, document);

        return page;
    }

    private void parseLinks(Page page, Document document) {
        for (Element link: document.select("a")) {
            String href = link.absUrl("href");
            if (href.startsWith(document.baseUri())) {
                page.addInternalLink(href);
            } else {
                page.addExternalLink(href);
            }
        }
    }

    private void parseImages(Page page, Document document) {
        for (Element img: document.select("img")) {
            page.addImage(img.attr("src"));
        }
    }
}
