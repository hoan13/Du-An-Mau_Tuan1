package hoanhqph30066.fpoly.du_an_mau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class ManChoActivity extends AppCompatActivity {
    ProgressBar progressBar;

    int dem =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_cho);

        progressBar = findViewById(R.id.progress);
        thanhcho();
    }

    private void thanhcho() {
        progressBar = findViewById(R.id.progress);
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                dem++;
                progressBar.setProgress(dem);
                if(dem == 30){
                    t.cancel();
                    startActivity(new Intent(ManChoActivity.this,DangNhapActivity.class));
                }
            }
        };
        t.schedule(tt,0,100);
    }
}