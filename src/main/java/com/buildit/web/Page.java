package com.buildit.web;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashSet;
import java.util.Set;

public class Page {
    private Set<String> internalLinks;
    private Set<String> externalLinks;
    private Set<String> images;
    private Set<Page> children;

    public Page() {
        internalLinks = new HashSet<String>();
        externalLinks = new HashSet<String>();
        images = new HashSet<String>();
        children = new HashSet<Page>();
    }

    public void addInternalLink(String link) {
        internalLinks.add(link);
    }

    public void addExternalLink(String link) {
        externalLinks.add(link);
    }

    public void addImage(String img) {
        images.add(img);
    }

    public void addChild(Page child) {
        children.add(child);
    }

    public Set<String> getInternalLinks() {
        return internalLinks;
    }

    public Set<String> getExternalLinks() {
        return externalLinks;
    }

    public Set<String> getImages() {
        return images;
    }

    public Set<Page> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Internal links: ").append(System.getProperty("line.separator"));
        for (String internalLink: this.getInternalLinks()) {
            sb.append(internalLink).append(System.getProperty("line.separator"));
        }

        sb.append("External links: ").append(System.getProperty("line.separator"));
        for (String externalLink: this.getExternalLinks()) {
            sb.append(externalLink).append(System.getProperty("line.separator"));
        }

        sb.append("Images: ").append(System.getProperty("line.separator"));
        for (String img: this.getImages()) {
            sb.append(img).append(System.getProperty("line.separator"));
        }

        sb.append("===========================").append(System.getProperty("line.separator"));

        return sb.toString();
    }
}
