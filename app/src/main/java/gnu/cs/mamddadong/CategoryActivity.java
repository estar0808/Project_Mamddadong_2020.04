package gnu.cs.mamddadong;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CategoryActivity extends AppCompatActivity {

    private long backBtnTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_activity);

        getWindow().setStatusBarColor(Color.rgb(246,141,42));

        Button map_icon = findViewById(R.id.map_icon);
        map_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this , MapActivity.class);
                intent.putExtra("NotNull", "NotNull");
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Button chat_icon = findViewById(R.id.chat_icon);
        chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this , ChatActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Button profile_icon = findViewById(R.id.profile_icon);
        profile_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this , ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Button write_icon = findViewById(R.id.write_icon);
        write_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this , WriteActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Button btn_split = findViewById(R.id.split_button);
        btn_split.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this , CategorySplitActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Button btn_help = findViewById(R.id.help_button);
        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this , CategoryHelpActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Button btn_service = findViewById(R.id.service_button);
        btn_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this , CategoryServiceActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if (0 <= gapTime && 2000 >= gapTime) {
            moveTaskToBack(true);
        } else {
            backBtnTime = curTime;
        }
    }
}
