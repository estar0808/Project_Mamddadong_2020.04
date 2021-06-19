package gnu.cs.mamddadong;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class LookupActivity extends AppCompatActivity {

    private long backBtnTime = 0;

    String Id;
    ArrayList<Post> postArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lookup_activity);

        postArrayList = new ArrayList<>();
        TextView name = findViewById(R.id.nickname);
        TextView location = findViewById(R.id.lookup_location);
        TextView title = findViewById(R.id.lookup_title);
        TextView content = findViewById(R.id.lookup_content);
        ImageView image = findViewById(R.id.show_image);
        ImageView profile = findViewById(R.id.profile);
        TextView day = findViewById(R.id.lookup_date);
        Button chatButton = findViewById(R.id.btn_connect_to_chat);

        Intent in = getIntent();
        final String ptKey = in.getStringExtra("ptKey");
        final String ptname = in.getStringExtra("ptname");
        final String pttitle = in.getStringExtra("pttitle");
        final String ptlocation = in.getStringExtra("ptlocation");
        final String ptcontext = in.getStringExtra("ptcontext");
        final String ptimage = in.getStringExtra("ptimage");
        final String ptprofile = in.getStringExtra("ptprofile");
        final String ptday = in.getStringExtra("ptday");
        final String ptid = in.getStringExtra("ptid");

        Id = String.valueOf(ptKey);
        name.setText(ptname);
        location.setText(ptlocation);
        title.setText(pttitle);
        content.setText(ptcontext);
        day.setText(ptday);
        Glide.with(this)
                .load(ptimage)
                .into(image);
        Glide.with(this)
                .load(ptprofile)
                .into(profile);

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stFriendId = ptid;
                String stprofile = ptprofile;
                String stName = ptname;
                Intent in = new Intent(LookupActivity.this, ChattingActivity.class);
                in.putExtra("name", stName);
                in.putExtra("profile", stprofile);
                in.putExtra("friendid", stFriendId);
                startActivity(in);
            }
        });

        getWindow().setStatusBarColor(Color.rgb(246,141,42));

        Button map_icon = findViewById(R.id.map_icon);
        map_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LookupActivity.this , MapActivity.class);
                intent.putExtra("NotNull", "NotNull");
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Button category_icon = findViewById(R.id.category_icon);
        category_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LookupActivity.this , CategoryActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Button chat_icon = findViewById(R.id.chat_icon);
        chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LookupActivity.this , ChatActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Button profile_icon = findViewById(R.id.profile_icon);
        profile_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LookupActivity.this , ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });


        Button write_icon = findViewById(R.id.write_icon);
        write_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LookupActivity.this , WriteActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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