package com.buildit;

import com.buildit.web.DocumentFetcher;
import com.buildit.web.Page;
import com.buildit.web.PageParser;
import com.buildit.web.WebCrawler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SitemapCreator {
    private String domainURL;
    private DocumentFetcher fetcher;
    private Set<Page> writtenPages;

    public SitemapCreator(String domainURL, DocumentFetcher fetcher) {
        this.domainURL = domainURL;
        this.fetcher = fetcher;
        this.writtenPages = new HashSet<Page>();
    }

    public void create() {
        WebCrawler crawler = new WebCrawler(domainURL, new PageParser(), fetcher);
        Page page = crawler.crawl();

        FileWriter writer = null;
        try {
            writer = new FileWriter("sitemap");
            writePage(writer, page);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void writePage(FileWriter writer, Page page) throws IOException {
        while (!writtenPages.contains(page)) {
            writtenPages.add(page);
            writer.write(page.toString());
            for (Page childPage: page.getChildren()) {
                writePage(writer, childPage);
            }
        }
    }
}
