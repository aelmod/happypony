package com.github.aelmod.adadm.core;

import com.github.aelmod.adadm.conf.DbConnection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Spider {
    private static java.sql.Connection connection = null;

    public void getPicPages(String url) throws SQLException, IOException {
        connection = DbConnection.getConnection();
        ImgUrlParser imgUrlParser = new ImgUrlParser();

        ExecutorService executorService = Executors.newFixedThreadPool(20);

        org.jsoup.Connection connect = Jsoup.connect(url);
        int lastPage = new Pagination().getFavouritesLastPage(connect.get());

        for (int i = 0; i < lastPage; i++) {
            final int constI = i;
            executorService.submit(() -> imgUrlParser.parse(url + "?offset=" + constI * 24)
                    .forEach(imgUrl -> {
                        try {
                            PreparedStatement ps = connection.prepareStatement("INSERT INTO imgs(img_url, status) VALUES (?, ?)");
                            ps.setString(1, imgUrl);
                            ps.setString(2, "ready_for_download");
                            ps.execute();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }));
        }
        executorService.shutdown();
        ImageWorker.start();
    }
}