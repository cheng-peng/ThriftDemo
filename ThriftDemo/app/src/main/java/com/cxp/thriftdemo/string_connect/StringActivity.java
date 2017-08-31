package com.cxp.thriftdemo.string_connect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cxp.thriftdemo.R;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * 文 件 名: StringActivity
 * 创 建 人: CXP
 * 创建日期: 2017-08-31 14:58
 * 描    述: 字符串传输
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */
public class StringActivity extends AppCompatActivity {

    private TextView tv;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    tv.setText(msg.obj.toString());
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string);
        tv = (TextView) findViewById(R.id.string_show);

    }

    public void clickLis(View view) {
        //获取输入框的信息
        EditText editText = (EditText) findViewById(R.id.string_et);
        final String strParam = editText.getText().toString();
        //开启线程，执行网络远程调用
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //构建Thrift传输和协议
                    TTransport tTransport = getTTransport();
                    TProtocol protocol = new TBinaryProtocol(tTransport);
                    //构建客户端,传入字符串进行反转操作
                    HelloWorldService.Client client = new HelloWorldService.Client(protocol);
                    String str = client.sayHello(strParam);

                    Log.e("CXP", "返回结果：" + str);

                    //发送信息
                    Message message = new Message();
                    message.what = 1;
                    message.obj = "返回结果：" + str;
                    handler.sendMessage(message);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //构建Thrift传输协议
    private TTransport getTTransport() throws Exception {
        try {
            //主机、端口、超时
            TSocket tSocket = new TSocket("192.168.1.116", 1234, 5000);
            //根据socket构建thrift
            TTransport tTransport = new TFramedTransport(tSocket);
            if (!tTransport.isOpen()) {
                tTransport.open();
            }
            return tTransport;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //页面跳转
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, StringActivity.class);
        context.startActivity(intent);
    }

}
