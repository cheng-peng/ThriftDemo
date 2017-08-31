package com.cxp.thriftdemo.file_connect;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.cxp.thriftdemo.R;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;

/**
 * 文 件 名: FileActivity
 * 创 建 人: CXP
 * 创建日期: 2017-08-31 14:58
 * 描    述: 文件传输
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */
public class FileActivity extends AppCompatActivity {

    private TextView tv;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if ((boolean) msg.obj) {
                        tv.setText("文件上传成功！");
                        tv.setTextColor(Color.GREEN);
                    } else if ((boolean) msg.obj) {
                        tv.setText("文件上传失败！");
                        tv.setTextColor(Color.RED);
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        tv = (TextView) findViewById(R.id.file_status);

    }

    public void clickLis(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                // 测试文件路径
                String filePath = Environment.getExternalStorageDirectory() + "/cxp_logo.png";

                // 构造文件数据
                byte[] bytes = toByteArray(filePath);
                FileData fileData = new FileData();
                fileData.name = filePath;
                fileData.buff = ByteBuffer.wrap(bytes);

                // 构造Thrift客户端，发起请求
                try {
                    TSocket socket = new TSocket("192.168.1.116", 12345);
                    socket.setSocketTimeout(60 * 1000);
                    TFramedTransport framedTransport = new TFramedTransport(socket);
                    if (!framedTransport.isOpen()) {
                        framedTransport.open();
                    }
                    TBinaryProtocol binaryProtocol = new TBinaryProtocol(framedTransport);
                    FileService.Client client = new FileService.Client(binaryProtocol);

                    Message message = new Message();
                    message.what = 1;
                    message.obj = client.uploadFile(fileData);
                    handler.sendMessage(message);
                } catch (Exception x) {
                    x.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 文件转化为字节数组
     */
    private static byte[] toByteArray(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    //页面跳转
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, FileActivity.class);
        context.startActivity(intent);
    }
}
