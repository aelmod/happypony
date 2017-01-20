package com.github.aelmod.adadm.core;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

class Pagination {
    int getFavouritesLastPage(Document gallery) {
        Elements paginationElements = gallery.select("div.pagination-wrapper.full div.pagination ul.pages li.number a.away");
        if (paginationElements.isEmpty()) return 0;
        return Integer.parseInt(paginationElements.get(2).childNode(0).toString());
    }
}
