package com.nineteenwang.electricalimi.core.view;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.nineteenwang.electricalimi.R;
import com.nineteenwang.electricalimi.databinding.MainView;
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
 * @When : 2018-08-24 오후 6:15
 * @Homepage : https://github.com/gusdnd852
 */

public class MainActivity extends AppCompatActivity {
    private final int REQ_CODE_SPEECH_INPUT = 100;
    ImageButton btnSpeak;
    TextView txtSpeechInput, outputText;
    TextToSpeech tts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
        btnSpeak = findViewById(R.id.btnSpeak);
        txtSpeechInput = findViewById(R.id.txtSpeechInput);
        outputText = findViewById(R.id.outputTex);
        btnSpeak.setOnClickListener(view -> promptSpeechInput());
        tts = new TextToSpeech(getApplicationContext(), status -> {

            if (status != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.KOREAN);
            }
        });
    }

    private void promptSpeechInput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.KOREAN);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "무엇이든 말해보세요");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "orry! Your device doesn\\'t support speech input",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String userQuery = result.get(0);
                    txtSpeechInput.setText(userQuery);
                    RetrieveFeedTask task = new RetrieveFeedTask();
                    task.execute(userQuery);
                }
                break;
            }

        }
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
            outputText.setText(s);
            tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}