package com.github.aelmod.adadm.core;

import org.jsoup.nodes.Document;

public interface DownloadStrategy {
    String getImageUrl(Document previewPage);
}
