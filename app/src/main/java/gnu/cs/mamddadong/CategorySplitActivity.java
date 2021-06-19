package gnu.cs.mamddadong;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class CategorySplitActivity extends AppCompatActivity {

    private long backBtnTime = 0;

    private RecyclerView.Adapter adapter;
    private ArrayList<Post> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_split_activity);

        RecyclerView recyclerView = findViewById(R.id.recycler_split);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = database.getReference("Post").child("나눔");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);
                    arrayList.add(post);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        adapter = new PostAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        getWindow().setStatusBarColor(Color.rgb(246,141,42));

        Button map_icon = findViewById(R.id.map_icon);
        map_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategorySplitActivity.this , MapActivity.class);
                intent.putExtra("NotNull", "NotNull");
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Button chat_icon = findViewById(R.id.chat_icon);
        chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategorySplitActivity.this , ChatActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Button profile_icon = findViewById(R.id.profile_icon);
        profile_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategorySplitActivity.this , ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Button write_icon = findViewById(R.id.write_icon);
        write_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategorySplitActivity.this , WriteActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Button category_icon = findViewById(R.id.category_icon);
        category_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategorySplitActivity.this , CategoryActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        final Spinner spinner = findViewById(R.id.category_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.category, R.layout.spinner);
        adapter.setDropDownViewResource(R.layout.spinner);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = spinner.getSelectedItem().toString();
                if(text.equals("부탁")) {
                    Intent intent = new Intent(CategorySplitActivity.this , CategoryHelpActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if(text.equals("봉사")) {
                    Intent intent = new Intent(CategorySplitActivity.this , CategoryServiceActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
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
