package gnu.cs.mamddadong;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

public class ChattingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatAdapter mAdapter;

    EditText etText;
    Button btnSend;
    String stEmail;
    FirebaseDatabase database;
    ArrayList<Chat> chatArrayList;
    String myKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting_activity);

        getWindow().setStatusBarColor(Color.rgb(234,234,234));

        database = FirebaseDatabase.getInstance();
        chatArrayList = new ArrayList<>();
        stEmail = getIntent().getStringExtra("userName");
        btnSend = findViewById(R.id.btnSend);
        etText = findViewById(R.id.etText);
        ImageView profile = findViewById(R.id.profile2);
        TextView nickname = findViewById(R.id.nickname2);

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ChatAdapter(chatArrayList, KakaoLoginActivity.user_name);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        Intent in = getIntent();
        final String stChatId = in.getStringExtra("friendid");
        final String stProfile = in.getStringExtra("profile");
        final String stName = in.getStringExtra("name");

        nickname.setText(stName);
        Glide.with(this)
                .load(stProfile)
                .into(profile);
        myKey = String.valueOf(KakaoLoginActivity.user_id);

        ChildEventListener childEventListener = new ChildEventListener() {
                @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Chat chat = dataSnapshot.getValue(Chat.class);
                chatArrayList.add(chat);
                mAdapter.notifyDataSetChanged();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.scrollToPosition(mAdapter.getItemCount()-1);
                        }
                    }, 10);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        DatabaseReference ref = database.getReference("User").child(stChatId).child(myKey).child("chat"); //
        ref.addChildEventListener(childEventListener);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etText = findViewById(R.id.etText);
                String get_etText = etText.getText().toString();
                get_etText = get_etText.trim();

                if(get_etText.getBytes().length <= 0){
                    Toast.makeText(getApplicationContext(), "채팅을 입력하세요", Toast.LENGTH_SHORT).show();
                } else {
                    String stText = etText.getText().toString();
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String datetime = dateformat.format(c.getTime());

                    DatabaseReference myRef = database.getReference("User").child(stChatId).child(myKey).child("chat").child(datetime);
                    Hashtable<String, String > numbers
                            = new Hashtable<String, String>();
                    numbers.put("userName", String.valueOf(KakaoLoginActivity.user_name));
                    numbers.put("text",stText);
                    myRef.setValue(numbers);

                    DatabaseReference myRef2 = database.getReference("User").child(myKey).child(stChatId).child("chat").child(datetime);
                    Hashtable<String, String > numbers2
                            = new Hashtable<String, String>();
                    numbers2.put("userName", String.valueOf(KakaoLoginActivity.user_name));
                    numbers2.put("text",stText);
                    myRef2.setValue(numbers2);

                    etText.setText(null);
                }
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
}
