package gnu.cs.mamddadong;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;
import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener, MapView.POIItemEventListener {

    private long backBtnTime = 0;

    int i = 0;
    private MapView mMapView;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};

    ArrayList <ArrayList<String>> KeyArray;
    ArrayList <String> Keys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        KeyArray = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Post").child("부탁");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);

                    Keys = new ArrayList<>();

                    MapPOIItem CustomMarker = new MapPOIItem();
                    CustomMarker.setItemName(post.getTitle());
                    CustomMarker.setTag(i);
                    CustomMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(post.getLat(),post.getLon()));
                    CustomMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
                    CustomMarker.setCustomImageResourceId(R.drawable.map_marker);

                    Keys.add(post.getKey());
                    Keys.add(post.getName());
                    Keys.add(post.getTitle());
                    Keys.add(post.getLocation());
                    Keys.add(post.getContent());
                    Keys.add(post.getImage());
                    Keys.add(post.getProfile());
                    Keys.add(post.getDay());
                    Keys.add(post.getUser_id());

                    KeyArray.add(Keys);

                    CustomMarker.setCustomImageAutoscale(true);
                    CustomMarker.setCustomImageAnchor(0.5f, 1.0f);
                    mMapView.addPOIItem(CustomMarker);
                    i++;
                }
            }

            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        databaseReference = database.getReference("Post").child("봉사");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);

                    Keys = new ArrayList<>();

                    MapPOIItem CustomMarker = new MapPOIItem();
                    CustomMarker.setItemName(post.getTitle());
                    CustomMarker.setTag(i);
                    CustomMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(post.getLat(),post.getLon()));
                    CustomMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
                    CustomMarker.setCustomImageResourceId(R.drawable.map_marker);

                    Keys.add(post.getKey());
                    Keys.add(post.getName());
                    Keys.add(post.getTitle());
                    Keys.add(post.getLocation());
                    Keys.add(post.getContent());
                    Keys.add(post.getImage());
                    Keys.add(post.getProfile());
                    Keys.add(post.getDay());
                    Keys.add(post.getUser_id());

                    KeyArray.add(Keys);

                    CustomMarker.setCustomImageAutoscale(true);
                    CustomMarker.setCustomImageAnchor(0.5f, 1.0f);
                    mMapView.addPOIItem(CustomMarker);
                    i++;
                }
            }

            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        databaseReference = database.getReference("Post").child("나눔");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);

                    Keys = new ArrayList<>();

                    MapPOIItem CustomMarker = new MapPOIItem();
                    CustomMarker.setItemName(post.getTitle());
                    CustomMarker.setTag(i);
                    CustomMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(post.getLat(),post.getLon()));
                    CustomMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
                    CustomMarker.setCustomImageResourceId(R.drawable.map_marker);

                    Keys.add(post.getKey());
                    Keys.add(post.getName());
                    Keys.add(post.getTitle());
                    Keys.add(post.getLocation());
                    Keys.add(post.getContent());
                    Keys.add(post.getImage());
                    Keys.add(post.getProfile());
                    Keys.add(post.getDay());
                    Keys.add(post.getUser_id());

                    KeyArray.add(Keys);

                    CustomMarker.setCustomImageAutoscale(true);
                    CustomMarker.setCustomImageAnchor(0.5f, 1.0f);
                    mMapView.addPOIItem(CustomMarker);
                    i++;
                }
            }

            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        getWindow().setStatusBarColor(Color.rgb(246,141,42));

        Button category_icon = findViewById(R.id.category_icon);
        category_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this , CategoryActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Button chat_icon = findViewById(R.id.chat_icon);
        chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this , ChatActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Button profile_icon = findViewById(R.id.profile_icon);
        profile_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this , ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Button write_icon = findViewById(R.id.write_icon);
        write_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this , WriteActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        mMapView = findViewById(R.id.kakao_map);
        mMapView.setCurrentLocationEventListener(this);
        mMapView.setPOIItemEventListener(this);

        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        } else {
            checkRunTimePermission();
        }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mMapView.setShowCurrentLocationMarker(false);
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters) {}

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {}

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {}

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {}

    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {}

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {}

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {
            boolean check_result = true;

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            if (check_result) {
                mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
                    Toast.makeText(MapActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(MapActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void checkRunTimePermission() {
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {
            mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapActivity.this, REQUIRED_PERMISSIONS[0])) {
                Toast.makeText(MapActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(MapActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
        }
    }

    private void showDialogForLocationServiceSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" + "위치 설정을 수정하시겠어요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GPS_ENABLE_REQUEST_CODE) {
            if (checkLocationServicesStatus()) {
                if (checkLocationServicesStatus()) {
                    checkRunTimePermission();
                }
            }
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {}

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {}

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
        String ptKey = KeyArray.get(mapPOIItem.getTag()).get(0);
        String ptname = KeyArray.get(mapPOIItem.getTag()).get(1);
        String pttitle = KeyArray.get(mapPOIItem.getTag()).get(2);
        String ptlocation = KeyArray.get(mapPOIItem.getTag()).get(3);
        String ptcontext = KeyArray.get(mapPOIItem.getTag()).get(4);
        String ptimage = KeyArray.get(mapPOIItem.getTag()).get(5);
        String ptprofile = KeyArray.get(mapPOIItem.getTag()).get(6);
        String ptday = KeyArray.get(mapPOIItem.getTag()).get(7);
        String ptid = KeyArray.get(mapPOIItem.getTag()).get(8);
        Intent in = new Intent(MapActivity.this, LookupActivity.class);
        in.putExtra("ptKey", ptKey);
        in.putExtra("ptname", ptname);
        in.putExtra("pttitle", pttitle);
        in.putExtra("ptlocation", ptlocation);
        in.putExtra("ptcontext", ptcontext);
        in.putExtra("ptimage", ptimage);
        in.putExtra("ptprofile", ptprofile);
        in.putExtra("ptday", ptday);
        in.putExtra("ptid", ptid);
        startActivity(in);
        overridePendingTransition(0,0);
    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {}
}