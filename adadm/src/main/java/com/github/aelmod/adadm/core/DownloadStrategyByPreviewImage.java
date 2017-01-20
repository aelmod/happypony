package com.github.aelmod.adadm.core;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class DownloadStrategyByPreviewImage implements DownloadStrategy {
    @Override
    public String getImageUrl(Document previewPage) {
        Elements elements = previewPage.select(".dev-content-full");
        if (elements.size()==1) return elements.get(0).attr("src");
        throw new IllegalStateException("We found more than one .dev-content-full element");
    }
}
