package com.blue.sky.web.api;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.SystemClock;

public class JSInterface {
    private final Object LOCK = new Object();
    
    private SearchTask searchTask;
    private String searchResult;
    
    public void calltest() {
        android.util.Log.d("", "@@@ JSInterface calltest ");
    }
    
 
    public void search(String keyword) {
        synchronized(LOCK) {
            android.util.Log.d("", "@@@ JSInterface search " + keyword);
            searchResult = null;
            if (searchTask != null) {
                searchTask.cancel(true);
            }
            searchTask = new SearchTask();
            searchTask.execute(keyword);
        }
    }
    
    public String getSearchResult() {
        synchronized(LOCK) {
            android.util.Log.d("", "@@@ JSInterface getSearchResult " + searchResult);
            String tmp = searchResult;
            searchResult = null;
            return tmp;
        }
    }
    
    private class SearchTask extends AsyncTask<String, Integer, String> {
        
        private String keyword;
        protected String doInBackground(String... param) {
            android.util.Log.d("", "@@@ JSInterface doInBackground keyword " + keyword);
            keyword = param[0];
            SystemClock.sleep(5000);
            return "1111" + keyword + "  2222" + keyword;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(String result) {
            android.util.Log.d("", "@@@ JSInterface onPostExecute result " + result);
            JSONObject json = new JSONObject();
            try {
                json.put("keyword", keyword);
                json.put("result", result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            searchResult = json.toString();
            searchTask = null;
            android.util.Log.d("", "@@@ JSInterface onPostExecute searchResult " + searchResult);
        }
    }

}
