package com.blue.sky.h5.game.setting;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.*;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.blue.sky.common.activity.BaseActivity;
import com.blue.sky.common.utils.Strings;
import com.blue.sky.common.utils.UIHelp;
import com.blue.sky.h5.game.R;

public class InputActivity extends BaseActivity implements View.OnClickListener {

    public static final int VALIDATE_EMAIL = 0;
    public static final int VALIDATE_PHOHE = 1;
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String SINGLE_LINE = "singleLine";
    public static final String MAXEMS = "maxems";
    public static final String VALIDATE = "validate";

    private TextView titleTv;
    private EditText contentEt;
    private TextView wordCountTv;
    private String title;
    private String content;

    /**
     * 输入页面
     *
     * @param context     都懂的
     * @param title       标题
     * @param content     输入框内容
     * @param isSigleLine 是否单行
     * @param maxems      字数限制
     * @param validate    输入验证
     * @param intentCode  返回码
     */
    public static void launcher(Context context, String title, String content, boolean isSigleLine, int maxems,
                                int validate, int intentCode) {
        Intent intent = new Intent();
        intent.setClass(context, InputActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(CONTENT, content);
        intent.putExtra(SINGLE_LINE, isSigleLine);
        // intent.putExtra(INPUT_TYPE, inputType);
        intent.putExtra(MAXEMS, maxems);
        intent.putExtra(VALIDATE, validate);
        if (intentCode != -1) {
            ((Activity) context).startActivityForResult(intent, intentCode);
        } else {
            context.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sky_activity_info_input);

        initView();
        initDate();
    }

    private void initDate() {
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");

        // @ChenRen/2013-07-04
        // 通过singleLine(true|false)决定是否可以换行编辑
        // 如果key不存在, 就会返回false
        if (getIntent().getBooleanExtra("singleLine", false)) {
            contentEt.setSingleLine(true);
            contentEt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 75));
            contentEt.setPadding(28, 0, 28, 0);
            contentEt.setGravity(Gravity.CENTER_VERTICAL);
        }

        if (title != null) {
            titleTv.setText(title);
            contentEt.setHint("请输入" + title);
        }

        int textLength = 0;
        // 设置预设内容
        if (!Strings.isEmpty(content)) {
            contentEt.setText(content);
            // 设置光标位置
            Editable etext = contentEt.getText();
            textLength = content.length();
            Selection.setSelection(etext, textLength);
        }

        if (getIntent().hasExtra("inputType")) {
            // 设置inputType, 比如电话号码会指定inputType为InputType.TYPE_CLASS_PHONE
            contentEt.setInputType(getIntent().getIntExtra("inputType", InputType.TYPE_CLASS_TEXT));
        }

        int maxems = -1; // 没限制
        maxems = getIntent().getIntExtra(MAXEMS, -1);
        wordCountTv.setText(maxems == -1 ? Strings.EMPTY_STRING : textLength + " / " + maxems);

        // 内容输入事件监听
        final int tempMaxems = maxems;
        if (tempMaxems != -1)
            contentEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxems)});
        contentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // 内容长度限制
                int length = s.length();

                if (tempMaxems != -1 && length > tempMaxems) {
                    s.delete(tempMaxems, length - 1); // 因为是下标, 所以length - 1
                    length = tempMaxems; // 修改当前输入的文字的长度
                }

                // 修改字数提示文字
                if (tempMaxems > 0) {
                    wordCountTv.setText(length + " / " + tempMaxems);
                } else {
                    wordCountTv.setText(length + Strings.EMPTY_STRING);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

        });
    }

    private void initView() {
        setHeader("填写资料", true);

        titleTv = (TextView) findViewById(R.id.commonTitle);
        contentEt = (EditText) findViewById(R.id.etInput);
        wordCountTv = (TextView) findViewById(R.id.zishu);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rightButton: {
                UIHelp.hideSoftInputFromWindow(contentEt);

                String value = contentEt.getText().toString();
                if (Strings.isNotEmpty(value) && !validate(value))
                    return;

                Intent intent = getIntent();
                intent.putExtra("result", value);
                setResult(RESULT_OK, intent);
                finish();
            }
            break;
            case R.id.leftButton: {
                UIHelp.hideSoftInputFromWindow(contentEt);
                finish();
            }
            break;
        }
    }

    /**
     * 数据校验, 通过:true; 不通过:false<br>
     * 因为不是所有的数据格式要求系统都自带校验, 有些需要手动校验
     *
     * @param value
     * @return
     */
    private boolean validate(String value) {
        int validate = getIntent().getIntExtra(VALIDATE, -1);
        if (validate == VALIDATE_EMAIL) {
            boolean flag = Strings.isEmail(value);
            if (!flag)
                showToast("请输入正确的邮箱地址");
            return flag;
        } else if (validate == VALIDATE_PHOHE) {
            boolean isPhone = Strings.isMobile(value);
            if (!isPhone)
                showToast("请输入正确的手机号码");
            return isPhone;
        }

        return true;
    }

}
