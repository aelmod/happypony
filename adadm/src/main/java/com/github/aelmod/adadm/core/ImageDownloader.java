package com.github.aelmod.adadm.core;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

class ImageDownloader {
    private List<Class<? extends DownloadStrategy>> downloadStrategies = new ArrayList<>();

    ImageDownloader() {
        downloadStrategies.add(DownloadStrategyByButton.class);
        downloadStrategies.add(DownloadStrategyByPreviewImage.class);
    }

    Image download(String previewPageUrl) {
        try {
            Connection connect = Jsoup.connect(previewPageUrl);
            String imageUrl = getImageUrlUsingStrategies(connect.get());
            String cookie = getCookieForImageDownloading(connect);

            URLConnection conn = prepareRequest(imageUrl, cookie);

            byte[] content = new byte[conn.getContentLength()];
            new DataInputStream(conn.getInputStream()).readFully(content);

            return new Image(getImageName(imageUrl), content);
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage() + " url " + previewPageUrl);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(previewPageUrl);
            return null;
        }
    }

    private String getImageName(String imageUrl) {
        String imgName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        int paramPositionStart = imgName.indexOf("?");
        if (paramPositionStart != -1) {
            imgName = imgName.substring(0, paramPositionStart);
        }
        return imgName;
    }

    private URLConnection prepareRequest(String imageUrl, String cookie) throws IOException {
        URLConnection conn = new URL(imageUrl).openConnection();
        conn.setRequestProperty("Accept-Encoding", "gzip");
        conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.59 Safari/537.36");
        conn.addRequestProperty("Cookie", cookie);
        return conn;
    }

    private String getCookieForImageDownloading(Connection connect) {
        StringBuilder builder = new StringBuilder();
        connect.response().cookies().forEach((s, s2) -> {
            builder.append(s + "=" + s2);
        });
        return builder.toString();
    }

    private String getImageUrlUsingStrategies(Document pictureDocument) {
        for (Class<? extends DownloadStrategy> downloadStrategy : downloadStrategies) {
            try {
                DownloadStrategy strategy = downloadStrategy.newInstance();
                String imageUrl = strategy.getImageUrl(pictureDocument);
                if (imageUrl != null) return imageUrl;

            } catch (IllegalStateException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException("It shouldn't never perform!!!");
            }
        }
        throw new IllegalStateException("Your strategies are shits");
    }
}
