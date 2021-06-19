package gnu.cs.mamddadong;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.exception.KakaoException;

import java.util.Hashtable;

public class KakaoLoginActivity extends AppCompatActivity {

    private long backBtnTime = 0;

    static long user_id;
    static String user_name;
    static String user_profile;

    private kakao_SessionCallback sessionCallback = new kakao_SessionCallback();
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kakao_login_activity);

        session = Session.getCurrentSession();
        session.addCallback(sessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();

        getWindow().setStatusBarColor(Color.rgb(255,255,255));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class kakao_SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            requestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {}

        public void requestMe() {
            UserManagement.getInstance().me(new MeV2ResponseCallback() {
                        @Override
                        public void onSessionClosed(ErrorResult errorResult) {}

                        @Override
                        public void onFailure(ErrorResult errorResult) {}

                        @Override
                        public void onSuccess(MeV2Response result) {
                            UserAccount kakaoAccount = result.getKakaoAccount();

                            if (kakaoAccount != null) {
                                Profile profile = kakaoAccount.getProfile();

                                if (profile != null) {
                                    user_id = result.getId();
                                    user_name = profile.getNickname();
                                    user_profile =  profile.getProfileImageUrl();

                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("User").child(String.valueOf(user_id));

                                    Hashtable<String, Object> numbers = new Hashtable<>();
                                    numbers.put("userName", profile.getNickname());
                                    numbers.put("profile", profile.getProfileImageUrl());
                                    numbers.put("key", String.valueOf(user_id));

                                    myRef.updateChildren(numbers);

                                    Intent intent = new Intent(getApplication(), MapActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    kakaoAccount.profileNeedsAgreement();
                                }
                            }
                        }
                    });
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
}
