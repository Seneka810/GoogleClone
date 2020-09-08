package com.company.service;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ServiceURL {

    public static boolean isUrlValid(String url) {
        String[] schemes = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        return urlValidator.isValid(url);
    }

    public static String parseUrl(String url) throws IOException {
        String html;
        Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
        Whitelist whitelist=new Whitelist();
        Cleaner cleaner = new Cleaner(whitelist);
        whitelist.addTags("a");
        whitelist.addAttributes("a","href");
        cleaner.clean(doc);
        html = doc.html();
        String htmlClean = Jsoup.clean(html, whitelist);
        return htmlClean;
    }


}
