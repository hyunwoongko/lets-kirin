package com.nineteenwang.electricalimi.cmp;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.widget.Toast;
import es.dmoral.toasty.Toasty;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Locale;

/**
 * @Author : Hyunwoong
 * @When : 2018-08-24 오후 11:24
 * @Homepage : https://github.com/gusdnd852
 */
public class OpenMicService extends Service implements RecognitionListener {

    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    public static SpeechRecognizer speechRecognizer;
    public static TextToSpeech tts;

    @Override public void onCreate() {
        super.onCreate();
        tts = new TextToSpeech(getApplicationContext(), status -> {

                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.KOREAN);
                }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }


    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        Toasty.success(this, "기린이에게 말하세요 !", Toast.LENGTH_SHORT).show();
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
        speechRecognizer.setRecognitionListener(this);
        Intent voice = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        voice.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, OpenMicService.class
                .getPackage().getName());
        voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        voice.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);

        speechRecognizer.startListening(voice);
        return START_STICKY;
    }

    public static void stopSTT() {
        speechRecognizer.stopListening();
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int i) {

    }

    @Override
    public void onResults(Bundle results) {

        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String userQuery = matches.get(0);
        RetrieveFeedTask task = new RetrieveFeedTask();
        task.execute(userQuery);

    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }


    public String GetText(String query) {

        String text;
        BufferedReader reader = null;

        // Send data
        try {
            URL url = new URL("https://api.api.ai/v1/query?v=20150910");

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestProperty("Authorization", "Bearer " + "186099c1e3334bd1a6d628abf8f7669d");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

            JSONObject jsonParam = new JSONObject();
            JSONArray queryArray = new JSONArray();
            queryArray.put(query);
            jsonParam.put("query", queryArray);
            jsonParam.put("name", "Alimi");
            jsonParam.put("lang", "ko");
            jsonParam.put("sessionId", "1234567890");

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(jsonParam.toString());
            wr.flush();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            text = sb.toString();
            JSONObject object1 = new JSONObject(text);
            JSONObject object = object1.getJSONObject("result");
            JSONObject fulfillment = null;
            String speech = null;
            fulfillment = object.getJSONObject("fulfillment");
            speech = fulfillment.optString("speech");
            return speech;

        } catch (Exception ex) {

        } finally {
            try {
                reader.close();
            } catch (Exception ex) {
            }
        }
        return null;
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... voids) {
            return GetText(voids[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}