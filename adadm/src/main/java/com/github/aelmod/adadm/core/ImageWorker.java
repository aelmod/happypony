package com.github.aelmod.adadm.core;

import com.github.aelmod.adadm.conf.DbConnection;

import java.sql.*;

class ImageWorker {
    static void start() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    while (true) {
                        downloadImage(DbConnection.getConnection());
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static void downloadImage(Connection connection) {
        try {
            connection.setAutoCommit(false);
            connection.createStatement().execute("LOCK TABLES imgs WRITE");
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM imgs WHERE status='ready_for_download' AND retry_count>0 LIMIT 1");
            if (!resultSet.next()) {
                connection.createStatement().execute("UNLOCK TABLES");
                throw new RuntimeException();
            }
            connection.createStatement().execute("UPDATE imgs SET status='downloading' WHERE id=" + resultSet.getInt("id"));
            System.out.println("imgUrl: " + resultSet.getString("img_url"));
            connection.createStatement().execute("UNLOCK TABLES");
            connection.commit();
            try {
                Image downloadedImage = new ImageDownloader().download(resultSet.getString("img_url"));
                ImageWriteUtil.writeImageInFileSystem(downloadedImage);
                System.out.println(downloadedImage.getName() + " saved");
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE imgs SET status='downloaded' WHERE id=?");
                preparedStatement.setInt(1, resultSet.getInt("id"));
                preparedStatement.execute();
            } catch (Exception e) {
                connection.createStatement().execute("UPDATE imgs SET status='ready_for_download', retry_count = retry_count - 1 WHERE id=" + resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
