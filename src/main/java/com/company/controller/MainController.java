package com.company.controller;

import com.company.service.ServiceIndexURL;
import com.company.service.ServiceURL;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Map;

@Controller
public class MainController {

    private static String indexUrl;
    private static String htmlData;

    @RequestMapping( value = "/", method = RequestMethod.GET)
    String mainPage() {
        return "main";
    }

    @RequestMapping( value = "/index", method = RequestMethod.GET)
    String index() {
        return "index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    String uriIndexing(@RequestParam String link, Model model) throws IOException {
        indexUrl = link;
        String html = "";
        String[] links = {};
        if(ServiceURL.isUrlValid(link)) {
            html = ServiceURL.parseUrl(link);
            links = html.split("\n");
        } else {
            html = "Invalid link";
        }
        htmlData = html;
        ServiceIndexURL.recursiveIndex(htmlData, 3, indexUrl);
        model.addAttribute("validateUri", ServiceURL.isUrlValid(link) ? link : "Invalid link");
        model.addAttribute("stringsHtml", links);
        return "uriIndexing";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    String viewResult(@RequestParam String keyWord, Model model) throws IOException, ParseException {
        Map<String, String> doc = ServiceIndexURL.searchIndex(htmlData, keyWord, indexUrl);
        model.addAttribute("doc", doc);
        model.addAttribute("url", indexUrl);
        return "viewResult";
    }

}
