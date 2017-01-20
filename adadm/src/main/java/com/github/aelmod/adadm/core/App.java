package com.github.aelmod.adadm.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) throws Exception {
        ImgUrlParser imgUrlParser = new ImgUrlParser();
        List<String> imgPreviewUrls = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 50; i++) {
            final int constI = i;
            executorService.submit(() -> {
                imgPreviewUrls.addAll(imgUrlParser.parse("http://aelmod.deviantart.com/favourites/?offset=" + constI*24));
                System.out.println("Page " + constI + " scanned");
            });
        }

        executorService.awaitTermination(30, TimeUnit.SECONDS);
        System.out.println("All page scanned!");


        imgPreviewUrls.forEach(imgPreviewUrl -> executorService.submit(() -> {
            Image downloadedImage = new ImageDownloader().download(imgPreviewUrl);
            ImageWriteUtil.writeImageInFileSystem(downloadedImage);
            System.out.println(downloadedImage.getName() + " saved");
        }));

        executorService.shutdown();
    }

}
