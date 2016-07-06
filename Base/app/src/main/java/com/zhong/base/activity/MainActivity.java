package com.zhong.base.activity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.zhong.base.R;
import com.zhong.base.entity.TestData;
import com.zhong.base.http.HttpResponse;
import com.zhong.base.http.HttpHelper;
import com.zhong.base.http.ProgressSubscriber.OnResponseListener;
import com.zhong.base.utils.Constants;
import com.zhong.base.utils.PermissionsChecker;

import java.util.List;

/**
 * Created by zhong on 16/7/4.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button   clickMeBN;
    TextView resultTV;

    private PermissionsChecker mPermissionsChecker; // 权限检测器
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[] {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clickMeBN = (Button) findViewById(R.id.click_me_BN);
        resultTV = (TextView) findViewById(R.id.result_TV);
        clickMeBN.setOnClickListener(this);

        mPermissionsChecker = new PermissionsChecker(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = PERMISSIONS;
            if (mPermissionsChecker.lacksPermissions(permissions)) {
                requestPermissions(permissions, PermissionsChecker.WRITE_EXTERNAL_STORAGE_CODE);
            } else {
                //需要权限才能操作的代码
            }
        } else {
            //需要权限才能操作的代码
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    //进行网络请求
    private void getData() {


        HttpHelper.getInstance().getResponseEntity(this, Constants.HISTROY_URL, null, new OnResponseListener<HttpResponse<List<TestData>>>() {

                @Override
                public void onSuccess(HttpResponse<List<TestData>> listHttpResponse) {
                    resultTV.setText("" + listHttpResponse.getResults().size());
                }
        });

    }

    @Override
    public void onClick(View v) {
        getData();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        if (requestCode == PermissionsChecker.WRITE_EXTERNAL_STORAGE_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {

                    } else {
                        Toast.makeText(this, PermissionsChecker.toDescription(requestCode), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
