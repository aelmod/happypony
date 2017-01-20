package com.github.aelmod.adadm.core;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

class ImgUrlParser {
    List<String> parse(String url) {
        try {
            return Jsoup.connect(url)
                   .get()
                   .select("div.tt-a.tt-fh a.t")
                   .stream()
                   .map((element) -> element.attr("href"))
                   .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println(url);
            e.printStackTrace();
            return null;
        }
    }
}
