package com.example.lab04part01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DownloadActivity extends AppCompatActivity {

    public static final String KEY_DRAWABLE = "keyDrawable";

    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;
    private int progressStatus;
    private static int staticStatus;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        imageView.setVisibility(View.GONE);
        // Gone means removed, Invisible means still, cannot see
        staticStatus = 0;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressStatus < 100) {
                    progressStatus = downloadImage();  // update the progressStatus
                    handler.post(new Runnable() { // set another thread to update the progress bar
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });
                }

                if(progressStatus == 100) {  // when status is 100%
                    handler.post(new Runnable() {  // another thread to change the visibility
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            textView.setVisibility(View.GONE);
                            imageView.setVisibility(View.VISIBLE);
                            imageView.setImageResource(R.drawable.ultraman);
                        }
                    });
                }
            }

            private int downloadImage() {  // simulating the download
                // in an actual app, this is where the download takes place
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return ++staticStatus;
            }

        }).start();  // start the thread running

    }

    public void onReturnClick(View view) {
        Intent intent = new Intent();
        intent.putExtra(KEY_DRAWABLE, R.drawable.ultraman);
        setResult(RESULT_OK, intent);
        finish();
    }
}
