package com.app.books.booksapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class YouTubePlay extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/*setContentView(R.layout.play);
		WebView wv = (WebView) findViewById(R.id.webView1);
	    String playVideo= "https://www.youtube.com/watch?v=dyauNfiMh7E";
	    wv.getSettings().setPluginsEnabled(true);
	    wv.getSettings().setAllowFileAccess(true);
	    wv.getSettings().setPluginState(PluginState.ON);
	    wv.getSettings().setBuiltInZoomControls(true);
	    wv.getSettings().setJavaScriptEnabled(true);
	    wv.loadData(playVideo, "text/html", "utf-8");
	    wv.setWebChromeClient(new WebChromeClient());*/
	    
	    
	    setContentView(R.layout.play);
	    final WebView video = (WebView) findViewById(R.id.webView1);
	    Bundle bd = getIntent().getExtras();
	    String vaall = bd.getString("val");
	    /*Button btnPlay = (Button) findViewById(R.id.btnPlay);
	    btnPlay.setText("Play Video");

	    btnPlay.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {*/
	    video.getSettings().setJavaScriptEnabled(true);
	    //video.getSettings().setPluginState(WebSettings.PluginState.ON);


		/// /video.getSettings().setUserAgent(0);


		video.setWebChromeClient(new WebChromeClient() {
	    });

	    //youtube video url
	    ////http://www.youtube.com/watch?v=WM5HccvYYQg

		try
		{
			 final String mimeType = "text/html";
			 final String encoding = "UTF-8";
			String htmlNew = Video.extractYoutubeId(vaall);
			String html = getHTML(htmlNew);
			 video.loadDataWithBaseURL("", html, mimeType, encoding, "");
		}
		catch (Exception ex)
		{
			Log.e("Get=>>", ex.toString());
		}
	}
	
	public String getHTML(String videoId) {

		String html =
		"<iframe class=\"youtube-player\" "
		+ "style=\"border: 0; width: 100%; height: 95%;"
		+ "padding:0px; margin:0px\" "
		+ "id=\"ytplayer\" type=\"text/html\" "
		+ "src=\"http://www.youtube.com/embed/" + videoId
		+ "?fs=0\" frameborder=\"0\" " + "allowfullscreen autobuffer "
		+ "controls onclick=\"this.play()\">\n" + "</iframe>\n";

		/**
		 * <iframe id="ytplayer" type="text/html" width="640" height="360"
		 * src="https://www.youtube.com/embed/WM5HccvYYQg" frameborder="0"
		 * allowfullscreen>
		 **/

		return html;
		}
	
	// String SrcPath =
	// "rtsp://v5.cache1.c.<a data-text=+"youtube"+ id="cr_it_item_0" class="cr_it_item
	// tooltipstered">youtube</a>.com/CjYLENy73wIaLQnhycnrJQ8qmRMYESARFEIJbXYtZ29vZ2xlSARSBXdhdGNoYPj_hYjnq6uUTQw=/0/0/0/<a data-text="video" id="cr_it_item_1" class="cr_it_item
	// tooltipstered">video</a>.3gp";
	/*
	 * String SrcPath =
	 * "rtsp://v3.cache7.c.youtube.com/CiILENy73wIaGQl-Z2VgdekdJxMYDSANFEgGUgZ2aWRlb3MM/0/0/0/video.3gp."
	 * ;
	 *//** Called when the activity is first created. */
	/*
	 * @Override public void onCreate(Bundle savedInstanceState) {
	 * super.onCreate(savedInstanceState); setContentView(R.layout.play);
	 * VideoView myVideoView = (VideoView)findViewById(R.id.videoView1);
	 * myVideoView.setVideoURI(Uri.parse(SrcPath));
	 * myVideoView.setMediaController(new MediaController(this));
	 * myVideoView.requestFocus(); myVideoView.start(); }
	 */

	/*
	 * private int REQ_PLAYER_CODE = 1; private static String YT_KEY =
	 * "YOUR-YOUTUBE-KEY-HERE"; private static String VIDEO_ID = "sjOfxnlGAF4";
	 * // Your video id here
	 * 
	 * @Override protected void onCreate(Bundle savedInstanceState) {
	 * super.onCreate(savedInstanceState); setContentView(R.layout.play);
	 * 
	 * Intent videoIntent = YouTubeStandalonePlayer.createVideoIntent(this,
	 * YT_KEY, VIDEO_ID, 0, true, false);
	 * 
	 * startActivityForResult(videoIntent, REQ_PLAYER_CODE);
	 * 
	 * }
	 * 
	 * @Override protected void onActivityResult(int requestCode, int
	 * resultCode, Intent data) { super.onActivityResult(requestCode,
	 * resultCode, data); if (requestCode == REQ_PLAYER_CODE && resultCode !=
	 * RESULT_OK) { YouTubeInitializationResult errorReason =
	 * YouTubeStandalonePlayer.getReturnedInitializationResult(data); if
	 * (errorReason.isUserRecoverableError()) { errorReason.getErrorDialog(this,
	 * 0).show(); } else { String errorMessage = String.format("PLAYER ERROR!!",
	 * errorReason.toString()); Toast.makeText(this, errorMessage,
	 * Toast.LENGTH_LONG).show(); } } }
	 */

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		//getFragmentManager().popBackStack();
	}
}