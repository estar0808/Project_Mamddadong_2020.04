package gnu.cs.mamddadong;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Bundle;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_activity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplication(), KakaoLoginActivity.class));
                overridePendingTransition(0, 0);
                LoadingActivity.this.finish();
            }
        },1200);

        getWindow().setStatusBarColor(Color.rgb(246,141,42));
    }
}
