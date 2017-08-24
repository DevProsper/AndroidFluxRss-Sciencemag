package com.venteaucongo.devprosper.fluxrss;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by DevProsper on 23/08/2017.
 */

public class ReadRss extends AsyncTask<Void, Void, Void>{

    Context context;
    String adresse = "http://www.sciencemag.org/rss/news_current.xml";
    ProgressDialog progressDialog;
    URL url;
    RecyclerView recy;
    ArrayList<FeedItem> list;

    public ReadRss(Context context, RecyclerView recy) {
        this.context = context;
        this.recy = recy;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Chargement...");
    }

    @Override
    protected void onPreExecute(){
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void avoid){
        super.onPostExecute(avoid);
        progressDialog.dismiss();
        MyAdapter adapter = new MyAdapter(list, context);
        recy.setLayoutManager(new LinearLayoutManager(context));
        recy.addItemDecoration(new VerticalSpace(50));
        recy.setAdapter(adapter);
    }

    @Override
    protected Void doInBackground(Void... params) {
        processXml(getDocument());
        return null;
    }

    private void processXml(Document data) {
        if (data != null){
            list = new ArrayList<>();
            Element root = data.getDocumentElement();
            Node chanel = root.getChildNodes().item(1);
            NodeList items = chanel.getChildNodes();
            for (int i = 0; i < items.getLength(); i++){
                Node currentChild = items.item(i);
                if (currentChild.getNodeName().equalsIgnoreCase("item")){
                    FeedItem feedItem = new FeedItem();
                    NodeList itemsChild = currentChild.getChildNodes();
                    for (int j=0; j<itemsChild.getLength(); j++){
                        Node current = itemsChild.item(j);
                        if (current.getNodeName().equalsIgnoreCase("title")){
                            feedItem.setTitle(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("description")){
                            feedItem.setDescription(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("pubDate")){
                            feedItem.setPubDate(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("link")){
                            feedItem.setLink(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("media:thumbnail")){
                            String url = current.getAttributes().item(0).getTextContent();
                            feedItem.setThumbnailUrl(url);
                        }
                    }
                    list.add(feedItem);
                }

            }

        }
    }

    public Document getDocument(){
        try {
            url = new URL(adresse);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream inputStream = urlConnection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

}
