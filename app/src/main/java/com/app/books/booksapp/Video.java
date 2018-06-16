package com.app.books.booksapp;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

public class Video extends Activity {

	VideoView videoView;
	 String videoUrl;
	 
	private class YourAsyncTask extends AsyncTask<String, Void, Void>
    {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Video.this, "", "Loading Video wait...", true);
        }

        @Override
        protected Void doInBackground(String... params)
        {
            try
            {
                //String url = "https://www.youtube.com/watch?v=TNpQPawBTtM";
                videoUrl = getUrlVideoRTSP(params[0]);
                Log.e("Vi>>>>", videoUrl);
            }
            catch (Exception e)
            {
                Log.e("EEn", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            progressDialog.dismiss();
        
            videoView.setVideoURI(Uri.parse(videoUrl));
            MediaController mc = new MediaController(Video.this);
            videoView.setMediaController(mc);
            videoView.requestFocus();
            videoView.start();          
            mc.show();
        }

    }

	public static String getUrlVideoRTSP(String urlYoutube)
    {
        try
        {
            String gdy = "http://gdata.youtube.com/feeds/api/videos/";
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String id = extractYoutubeId(urlYoutube);
            URL url = new URL(id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            Document doc = documentBuilder.parse(connection.getInputStream());
            Element el = doc.getDocumentElement();
            NodeList list = el.getElementsByTagName("media:content");///media:content
            String cursor = urlYoutube;
            for (int i = 0; i < list.getLength(); i++)
            {
                Node node = list.item(i);
                if (node != null)
                {
                    NamedNodeMap nodeMap = node.getAttributes();
                    HashMap<String, String> maps = new HashMap<String, String>();
                    for (int j = 0; j < nodeMap.getLength(); j++)
                    {
                        Attr att = (Attr) nodeMap.item(j);
                        maps.put(att.getName(), att.getValue());
                    }
                    if (maps.containsKey("yt:format"))
                    {
                        String f = maps.get("yt:format");
                        if (maps.containsKey("url"))
                        {
                            cursor = maps.get("url");
                        }
                        if (f.equals("1"))
                            return cursor;
                    }
                }
            }
            return cursor;
        }
        catch (Exception ex)
        {
            Log.e("Get=>>", ex.toString());
        }
        return urlYoutube;

    }

public static String extractYoutubeId(String url) throws MalformedURLException    {
	    String id = null;
        try
        {
            String query = new URL(url).getQuery();
            if (query != null)
            {
                String[] param = query.split("&");
                for (String row : param)
                {
                    String[] param1 = row.split("=");
                    if (param1[0].equals("v"))
                    {
                        id = param1[1];
                    }
                }
            }
            else
            {
                if (url.contains("embed"))
                {
                    id = url.substring(url.lastIndexOf("/") + 1);
                }
            }
        }
        catch (Exception ex)
        {
            Log.e("Exception", ex.toString());
        }
        return id;
    }

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        setContentView(R.layout.profile);
        videoView = (VideoView)findViewById(R.id.videoView1);
        
        Bundle bd = getIntent().getExtras();
	    String vaall = bd.getString("val");
	    
         new YourAsyncTask().execute(vaall);
         
       /* requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);

            setContentView(R.layout.play);

            final ProgressBar Pbar;

            Pbar = (ProgressBar) findViewById(R.id.pB4);
            WebView wv = (WebView) findViewById(R.id.webView1);
            //wv.setWebViewClient(new Callback());
            WebSettings webSettings = wv.getSettings();
            webSettings.setBuiltInZoomControls(true);
            webSettings.setJavaScriptEnabled(true);
            //wv.setBackgroundColor(0x919191);
            final String mimeType = "text/html";
            final String encoding = "UTF-8";
            String html = getHTML();
            wv.loadDataWithBaseURL("", html, mimeType, encoding, "");
            final Activity activity = this;

            wv.setWebChromeClient(new WebChromeClient() {
            	public void onProgressChanged(WebView view, int progress) {
            		 activity.setProgress(progress * 100);
                     {
                         if(progress < 100 && Pbar.getVisibility() == ProgressBar.GONE){
                             Pbar.setVisibility(ProgressBar.VISIBLE);

                         }
                         Pbar.setProgress(progress);
                         if(progress == 100) {
                             Pbar.setVisibility(ProgressBar.GONE);

                         }
                      }
            	}
            	});

            wv.setWebViewClient(new WebViewClient() {
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                  Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_LONG).show();
                }
              });

            wv.setDownloadListener(new DownloadListener()
            {

               public void onDownloadStart(String url, String userAgent,String contentDisposition, String mimetype,long contentLength)
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                    startActivity(intent);
                }


            });*/
    }

	 private String getHTML() {
        // TODO Auto-generated method stub
        String html1 = "<iframe class=\"youtube-player\" style=\"border: 0; width: 100%; height: 95%; padding:0px; margin:0px\" id=\"ytplayer\" type=\"text/html\" src=\"http://www.youtube.com/embed/"
                + "dyauNfiMh7E"
                + "?fs=0\" frameborder=\"0\">\n"
                + "</iframe>\n";
        return html1;
    }
}