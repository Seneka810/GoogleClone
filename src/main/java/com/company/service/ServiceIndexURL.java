package com.company.service;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceIndexURL {

    public static Map<String, String> searchIndex(String htmlData, String queryString, String mainLink) throws IOException, ParseException {
        Directory memoryIndex = new RAMDirectory();
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(memoryIndex, indexWriterConfig);
        Document document = new Document();

        document.add(new TextField("htmlData", htmlData, Field.Store.YES));

        writer.addDocument(document);
        writer.close();

        Query query = new QueryParser("htmlData", analyzer).parse(queryString);

        IndexReader indexReader = DirectoryReader.open(memoryIndex);
        IndexSearcher searcher = new IndexSearcher(indexReader);
        TopDocs topDocs = searcher.search(query, 10);

        List<Document> documents = new ArrayList<>();
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            documents.add(searcher.doc(scoreDoc.doc));
        }

        URL aURL = new URL(mainLink);
        Map<String, String> documentStrings = new HashMap<>();
        String[] docStrings = document.get("htmlData").split("\n");

        for (int i = 0; i < docStrings.length; i++) {
            if(docStrings[i].contains(queryString) && docStrings[i].contains("\"")) {
                String key = docStrings[i].substring(docStrings[i].indexOf(">") + 1, docStrings[i].indexOf("</a>"));
                String value = aURL.getProtocol() + "://" + aURL.getHost()
                        + docStrings[i].substring(docStrings[i].indexOf("\"") + 1, docStrings[i].lastIndexOf("\""));
                documentStrings.put(key, value);
            }
        }

        return documentStrings;
    }

    public static void recursiveIndex(String htmlData, int count, String mainLink) throws IOException {
        int countIn = count;
        URL aURL = new URL(mainLink);
        String[] docStrings = htmlData.split("\n");

        List<String> links = new ArrayList<>();
        for (int i = 0; i < docStrings.length; i++) {
            if(docStrings[i].contains("\"")) {
                links.add(aURL.getProtocol() + "://" + aURL.getHost()
                        + docStrings[i].substring(docStrings[i].indexOf("\"") + 1, docStrings[i].lastIndexOf("\"")));
            }
        }
        String htmlDataLink = "";
        for(String link : links) {
            if(countIn == 0) {
                break;
            }
            if(ServiceURL.isUrlValid(link)) {
                htmlDataLink = ServiceURL.parseUrl(link);
            }

            countIn--;
            recursiveIndex(htmlDataLink, countIn, link);
        }

    }
}
