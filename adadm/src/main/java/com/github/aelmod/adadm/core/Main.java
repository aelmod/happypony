package com.github.aelmod.adadm.core;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by aelmod on 05.01.17.
 */
public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        //getPicPages("http://aelmod.deviantart.com/favourites/");

        new Spider().getPicPages("http://madacon.deviantart.com/gallery/");
    }
}
