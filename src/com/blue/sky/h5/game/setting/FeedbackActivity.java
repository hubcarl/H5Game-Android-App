package com.blue.sky.h5.game.setting;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.blue.sky.common.activity.BaseActivity;
import com.blue.sky.common.http.HttpAsyncStringResult;
import com.blue.sky.common.http.HttpRequestAPI;

import com.blue.sky.h5.game.R;
import com.blue.sky.common.utils.*;

import java.util.Date;


public class FeedbackActivity extends BaseActivity {

    private EditText txtMessage;
    private EditText txtContact;
    private TextView btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sky_activity_feedback);
        setHeader("意见反馈", true);

        txtMessage = (EditText) findViewById(R.id.message);
        txtContact = (EditText) findViewById(R.id.contact);
        btnSubmit = (TextView) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = txtMessage.getText().toString();
                String contact = txtContact.getText().toString();
                if (validate(content, contact)) {
                    UIHelp.showLoading(FeedbackActivity.this);
                    HttpRequestAPI.feedback(content, contact, new HttpAsyncStringResult() {
                        @Override
                        public void process(String response) {
                            if (Constants.SUCCESS.equals(response)) {
                                showToast("提交成功,谢谢你的参与!");
                                txtMessage.setText(Strings.EMPTY_STRING);
                                txtContact.setText(Strings.EMPTY_STRING);
                            }
                            UIHelp.closeLoading();
                        }
                    });
                }
            }
        });
    }

    private boolean validate(String content, String contact) {
        if (Strings.isEmpty(content)) {
            showToast("请输入反馈信息!");
            return false;
        } else if (content.length() < 10) {
            showToast("反馈信息必须大于10个字符!");
            return false;
        } else if (Strings.isEmpty(contact)) {
            showToast("请输入联系方式!");
            return false;
        } else if (!(Strings.isNumeric(contact) || Strings.isEmail(contact)) || contact.length() < 5) {
            showToast("联系方式格式不对，只能为QQ或者Email!");
            return false;
        } else if (!NetWorkHelper.isConnect(this)) {
            showToast("没有网络,请检查网络!");
            return false;
        } else {
            String feedback_key = "feedback";
            long lastTime = SharedPreferenceUtil.getInstance(this).getLong(feedback_key);
            long currentTime = new Date().getTime() / 1000;
            if (currentTime - lastTime > 15) {
                SharedPreferenceUtil.getInstance(this).putLong(feedback_key, currentTime);
                return true;
            } else {
                showToast("亲,你反馈太频繁了,请稍后再反馈!");
                return false;
            }
        }
    }
}
