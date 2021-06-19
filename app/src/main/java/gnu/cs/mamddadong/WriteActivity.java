package gnu.cs.mamddadong;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

public class WriteActivity extends AppCompatActivity implements View.OnClickListener {

    static String PostKey;

    private long backBtnTime = 0;

    private static final int PICK_FROM_ALBUM = 0;
    private static final int CROP_FROM_PHOTO = 1;
    private Uri cropedPhotoUri = null;
    private ImageView mPhotoImageView;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private String fileUrl;
    static Date currentTime;

    public Bitmap photo;
    static int isMoved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_activity);

        final Spinner category = findViewById(R.id.category_spinner);
        final EditText location = findViewById(R.id.post_location);
        final EditText title = findViewById(R.id.post_title);
        final EditText content = findViewById(R.id.post_content);

        Spinner spinner = findViewById(R.id.category_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.category, R.layout.spinner);
        adapter.setDropDownViewResource(R.layout.spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Intent intent_map;
        intent_map = getIntent();
        Uri receive_uri = intent_map.getParcelableExtra("uri_return");

        if (isMoved == 0) {

        } else if(receive_uri != null) {
            String receive_category = intent_map.getExtras().getString("category_return");
            String receive_title = intent_map.getExtras().getString("title_return");
            String receive_content = intent_map.getExtras().getString("content_return");
            String address = intent_map.getStringExtra("address");

            location.setText(address);
            switch (receive_category) {
                case "나눔":
                    category.setSelection(0);
                    break;
                case "부탁":
                    category.setSelection(1);
                    break;
                case "봉사":
                    category.setSelection(2);
                    break;
            }

            title.setText(receive_title);
            content.setText(receive_content);

            cropedPhotoUri = receive_uri;
            mPhotoImageView = findViewById(R.id.stuff_select);

            try {
                photo = MediaStore.Images.Media.getBitmap(getContentResolver(), receive_uri);
                mPhotoImageView.setImageBitmap(photo);
            } catch (Exception e) {
                e.printStackTrace();
            }

            isMoved = 0;
        } else {
            String receive_category = intent_map.getExtras().getString("category_return");
            String receive_title = intent_map.getExtras().getString("title_return");
            String receive_content = intent_map.getExtras().getString("content_return");

            String address = intent_map.getStringExtra("address");
            location.setText(address);

            switch (receive_category) {
                case "나눔":
                    category.setSelection(0);
                    break;
                case "부탁":
                    category.setSelection(1);
                    break;
                case "봉사":
                    category.setSelection(2);
                    break;
            }

            title.setText(receive_title);
            content.setText(receive_content);

            isMoved = 0;
        }

        getWindow().setStatusBarColor(Color.rgb(246,141,42));

        ScrollView scrollView = findViewById(R.id.scrollView);
        scrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        Button map_icon = findViewById(R.id.map_icon);
        map_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteActivity.this , MapActivity.class);
                intent.putExtra("NotNull", "NotNull");
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Button category_icon = findViewById(R.id.category_icon);
        category_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteActivity.this , CategoryActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Button chat_icon = findViewById(R.id.chat_icon);
        chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteActivity.this , ChatActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Button profile_icon = findViewById(R.id.profile_icon);
        profile_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteActivity.this , ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Button mButton = findViewById(R.id.stuff_select_button);
        mPhotoImageView = findViewById(R.id.stuff_select);
        mButton.setOnClickListener(this);

        Button location_icon = findViewById(R.id.location_icon);
        location_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteActivity.this , LocationSelActivity.class);

                Spinner post_category = (Spinner) findViewById(R.id.category_spinner);
                EditText post_title = (EditText)findViewById(R.id.post_title);
                EditText post_content = (EditText)findViewById(R.id.post_content);

                intent.putExtra("uri_info", cropedPhotoUri);
                intent.putExtra("category_info", post_category.getSelectedItem().toString());
                intent.putExtra("title_info",post_title.getText().toString());
                intent.putExtra("content_info",post_content.getText().toString());

                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Button submit_btn = findViewById(R.id.post_submit_icon);
        submit_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                EditText post_location = findViewById(R.id.post_location);
                EditText post_title = findViewById(R.id.post_title);
                EditText post_content = findViewById(R.id.post_content);
                String get_location = post_location.getText().toString();
                String get_title = post_title.getText().toString();
                String get_content = post_content.getText().toString();
                get_location = get_location.trim();
                get_title = get_title.trim();
                get_content = get_content.trim();

                if(get_location.getBytes().length <= 0 || get_title.getBytes().length <= 0 || get_content.getBytes().length <= 0 ){
                    Toast.makeText(getApplicationContext(), "위치, 제목, 내용을 입력하세요", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                    photo = null;
                    cropedPhotoUri = null;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isMoved = 0;
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

    public void onClick(View v) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            doTakeAlbumAction();
        }
    }

    private void doTakeAlbumAction() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case PICK_FROM_ALBUM: {
                Uri mImageCaptureUri = data.getData();
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");
                intent.putExtra("outputX", 360);
                intent.putExtra("outputY", 360);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_PHOTO);
                break;
            }
            case CROP_FROM_PHOTO: {
                final Bundle extras = data.getExtras();
                if (extras != null) {
                    photo = extras.getParcelable("data");
                    mPhotoImageView.setImageBitmap(photo);
                    cropedPhotoUri = getImageUri(getApplicationContext(), photo);
                }
                break;
            }
        }
    }

    private void uploadFile() {
        final Spinner category = findViewById(R.id.category_spinner);
        final EditText location = findViewById(R.id.post_location);
        final EditText title = findViewById(R.id.post_title);
        final EditText content = findViewById(R.id.post_content);

        if (cropedPhotoUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("게시글을 업로드 중입니다");
            progressDialog.show();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat(KakaoLoginActivity.user_id + "_yyyyMMdd_hhmmss");
            Date now = new Date();
            String filename = formatter.format(now) + ".jpeg";
            final StorageReference storageRef = storage.getReferenceFromUrl("gs://gnu-cs-mamddadong.appspot.com").child("post_images/" + filename);
            storageRef.putFile(cropedPhotoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "업로드 완료", Toast.LENGTH_SHORT).show();
                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            fileUrl = uri.toString();
                            PostKey = databaseReference.child("Post").child((String) category.getSelectedItem()).push().getKey();
                            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("Post").child((String) category.getSelectedItem()).child(PostKey);
                            DatabaseReference myRef2 = database.getReference("MyPost").child(String.valueOf(KakaoLoginActivity.user_id)).child(PostKey);

                            Hashtable<String, String > post = new Hashtable<>();

                            post.put("category", (String) category.getSelectedItem());
                            post.put("location", location.getText().toString());
                            post.put("title", title.getText().toString());
                            post.put("content", content.getText().toString());
                            post.put("key", PostKey);
                            post.put("name", KakaoLoginActivity.user_name);
                            post.put("image", fileUrl);
                            post.put("profile", KakaoLoginActivity.user_profile);
                            post.put("user_id", String.valueOf(KakaoLoginActivity.user_id));
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                            String datetime = dateformat.format(c.getTime());
                            post.put("day", datetime);
                            myRef.setValue(post);
                            myRef2.setValue(post);

                            Hashtable<String, Object> post2 = new Hashtable<>();
                            post2.put("lat", LocationSelActivity.mapCenterLat);
                            post2.put("lon", LocationSelActivity.mapCenterLon);
                            myRef.updateChildren(post2);
                            myRef2.updateChildren(post2);

                        }
                    });

                    Intent intent = new Intent(WriteActivity.this, MapActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "업로드 실패", Toast.LENGTH_SHORT).show();
                        }
            });
        } else {
            PostKey = databaseReference.child("Post").child((String) category.getSelectedItem()).push().getKey();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Post").child((String) category.getSelectedItem()).child(PostKey);
            DatabaseReference myRef2 = database.getReference("MyPost").child(String.valueOf(KakaoLoginActivity.user_id)).child(PostKey);

            Hashtable<String, String > post = new Hashtable<>();
            post.put("category", (String) category.getSelectedItem());
            post.put("location", location.getText().toString());
            post.put("title", title.getText().toString());
            post.put("content", content.getText().toString());
            post.put("key", PostKey);
            post.put("image", "https://firebasestorage.googleapis.com/v0/b/gnu-cs-mamddadong.appspot.com/o/post_images%2Fpost_default_image.jpg?alt=media&token=f8392443-70a1-4465-91cc-5a0e819b0eb4");
            post.put("name", KakaoLoginActivity.user_name);
            post.put("profile", KakaoLoginActivity.user_profile);
            post.put("user_id", String.valueOf(KakaoLoginActivity.user_id));
            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
            String datetime = dateformat.format(c.getTime());
            post.put("day", datetime);
            myRef.setValue(post);
            myRef2.setValue(post);

            Hashtable<String, Object> post2 = new Hashtable<>();
            post2.put("lat", LocationSelActivity.mapCenterLat);
            post2.put("lon", LocationSelActivity.mapCenterLon);
            myRef.updateChildren(post2);
            myRef2.updateChildren(post2);

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("게시글을 업로드 중입니다");
            progressDialog.show();
            Toast.makeText(getApplicationContext(), "업로드 완료", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(WriteActivity.this, MapActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title" + "_" + (currentTime = Calendar.getInstance().getTime()), null);
        return Uri.parse(path);
    }
}