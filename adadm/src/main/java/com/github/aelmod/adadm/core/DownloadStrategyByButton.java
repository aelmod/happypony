package com.github.aelmod.adadm.core;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class DownloadStrategyByButton implements DownloadStrategy {
    @Override
    public String getImageUrl(Document previewPage) {

        Elements pictureElements = previewPage.select("div.dev-meta-actions a.dev-page-button.dev-page-button-with-text.dev-page-download");
        if (pictureElements.isEmpty()) return null;
        return pictureElements.get(0).attr("href");
    }
}
