package com.lp.ams_pms_hook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void attachBaseContext(Context newBase) {
        try {
            HookHelper.hookActivityManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                // 测试AMS HOOK (调用其相关方法)
                Uri uri = Uri.parse("http://wwww.baidu.com");
                Intent t = new Intent(Intent.ACTION_VIEW);
                t.setData(uri);
                startActivity(t);
                break;
            case R.id.button2:
                // 测试PMS HOOK (调用其相关方法)
                getPackageManager().getInstalledApplications(0);
                break;
            default:
                break;
        }
    }
}
