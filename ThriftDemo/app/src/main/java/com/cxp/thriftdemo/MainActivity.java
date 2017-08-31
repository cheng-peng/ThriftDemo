package com.cxp.thriftdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cxp.thriftdemo.file_connect.FileActivity;
import com.cxp.thriftdemo.string_connect.StringActivity;


public class MainActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

    }

    public void clickLis(View view) {
        switch (view.getId()) {
            case R.id.main_bt_string:
                //字符串传输
                StringActivity.startActivity(mContext);
                break;
            case R.id.main_bt_file:
                //文件传输
                FileActivity.startActivity(mContext);
                break;

        }
    }

}
