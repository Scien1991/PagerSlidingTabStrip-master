/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.astuetz.viewpager.extensions.sample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SuperAwesomeCardFragment extends Fragment implements View.OnClickListener {

	private static final String ARG_POSITION = "position";

    @InjectView(R.id.web_yougoshu)
	WebView webView;

    ArrayList<HashMap<String, String>> mList;
    MyListViewAdapter mladapter;

	private int position;

	public static SuperAwesomeCardFragment newInstance(int position) {
		SuperAwesomeCardFragment f = new SuperAwesomeCardFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		position = getArguments().getInt(ARG_POSITION);
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
		final View rootView;
        ListView listView;
		switch (position){
			case 0:
				rootView = inflater.inflate(R.layout.layout_mainpage,container,false);
				ViewCompat.setElevation(rootView,50);
                listView = (ListView)rootView.findViewById(R.id.showtopic);
                mList = new ArrayList<>();
                HashMap<String, String> item;
                for (int a = 0; a < 10; a++) {
                    item = new HashMap<>();
                    item.put("title", "Title " + String.valueOf(a+1));
                    item.put("desc", "Description " + String.valueOf(a+1));
                    item.put("url", "http://hogehoge.com/" + String.valueOf(a + 1));
                    mList.add(item);
                }
                //アダプター作成してリストアイテムを追加
                mladapter = new MyListViewAdapter(getActivity(), mList);
                //アダプターを設定
                listView.setAdapter(mladapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("debug", "listview clicked.");
                        ListView listView =(ListView)parent;
                        HashMap item = (HashMap)listView.getItemAtPosition(position);
                        Log.d("debug", "Title:" + item.get("title") + ",  Desc:" + item.get("desc"));
                    }
                });
                Button btn_renew = (Button)rootView.findViewById(R.id.renew);
                btn_renew.setOnClickListener(this);
                break;
            case 1:
				rootView = inflater.inflate(R.layout.layout_setting,container,false);
				ViewCompat.setElevation(rootView,50);
				break;
			case 2:
				rootView = inflater.inflate(R.layout.layout_nanj_yougoshu,container,false);
				ViewCompat.setElevation(rootView, 50);
				//ButterKnife.inject(this, rootView);
				webView =(WebView) rootView.findViewById(R.id.web_yougoshu);
				webView.setWebViewClient(new WebViewClient());
				webView.loadUrl("http://wikiwiki.jp/livejupiter/?MenuBar");
				webView.requestFocusFromTouch();

                webView.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() != KeyEvent.ACTION_DOWN) {
                            return false;
                        }

                        switch (keyCode) {
                            case KeyEvent.KEYCODE_BACK:
                                if (webView.canGoBack())
                                    webView.goBack();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
				break;
			default:
				rootView = null;
				break;
		}
        if (rootView != null) {
            rootView.setFocusableInTouchMode(true);
        }
        return rootView;
	}

    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(), "更新しています..", Toast.LENGTH_SHORT).show();
        xmlpurse();

    }

    private void xmlpurse() {

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                _xmlpurse();
                return null;
            }

            @Override
            protected void onPostExecute(Void result){
                Log.d("xmlpurse_background", "finish");
            }
        };
        task.execute();
    }

    private void _xmlpurse(){
        XmlPullParser xmlPullParser = Xml.newPullParser();
        try {

            URL url = new URL("http://blog.livedoor.jp/nanjstu/");
            URLConnection connection = url.openConnection();
            xmlPullParser.setInput(connection.getInputStream(), "UTF-8");
            String tag = null;
            int eventType = xmlPullParser.getEventType();
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//
//                switch (eventType) {
//                    case XmlPullParser.START_TAG:
//                        tag = xmlPullParser.getName();
//                        if (tag.equals("item")) {
//                            Log.d("XmlPullParserSampleUrl", "item");
//                        }
//                        break;
//                    case XmlPullParser.TEXT:
//                            String text = xmlPullParser.getText();
//                            // 空白データは対象外
//                            if (text.trim().length() != 0) {
//                                if (tag.equals("title")) {
//                                    Log.d("XmlPullParserSampleUrl", "title:" + text);
//                                } else if (tag.equals("link")) {
//                                    Log.d("XmlPullParserSampleUrl", "link:" + text);
//                                }
//                        }
//                        break;
//                    case XmlPullParser.END_TAG:
//                        tag = xmlPullParser.getName();
//                        if (tag.equals("item")) {
//                            // ListViewに反映するためのArrayAdapterに追加
//                            //mAdapter.add(item);
//                            Log.d("XmlPullParserSampleUrl", "END_TAG");
//                        }
//                        break;
//
//   }
//                eventType = xmlPullParser.next();
//            }
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if(eventType == XmlPullParser.START_DOCUMENT) {
                    Log.d("XmlPullParserSample", "Start document");
                } else if(eventType == XmlPullParser.END_DOCUMENT) {
                    Log.d("XmlPullParserSample", "End document");
                } else if(eventType == XmlPullParser.START_TAG) {
                    Log.d("XmlPullParserSample", "Start tag "+xmlPullParser.getName());
                } else if(eventType == XmlPullParser.END_TAG) {
                    Log.d("XmlPullParserSample", "End tag "+xmlPullParser.getName());
                } else if(eventType == XmlPullParser.TEXT) {
                    Log.d("XmlPullParserSample", "Text "+xmlPullParser.getText());
                }
                eventType = xmlPullParser.next();
            }
        } catch(Exception e) {
            Log.e("XmlPullParserSampleUrl", e.toString());
        }
    }


}