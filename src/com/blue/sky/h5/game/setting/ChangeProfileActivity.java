package com.blue.sky.h5.game.setting;

import android.content.Intent;
import android.os.Bundle;
import com.blue.sky.common.activity.BaseActivity;
import com.blue.sky.common.utils.UIHelp;
import com.blue.sky.h5.game.R;

public class ChangeProfileActivity extends BaseActivity {

    public static final int PHOTO_ALBUM_GET_RESULT_CODE = 101;
    public static final int PHOTO_CAMERA_GET_RESULT_CODE = 102;
    public static final int PHOTO__ALBUM_FIX_RESULT_CODE = 103;
    public static final int PHOTO_CAMERA_FIX_RESULT_CODE = 104;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sky_activity_about);
        initView();
        initListener();
    }

    private void initView() {
        setHeader("编辑资料", true);

    }

    private void initListener() {

    }


    /**
     * 是否
     *
     */
    private void showConfirmDialog() {
        if (true) {
            UIHelp.showConfirmDialog(this, "您的修改尚未提交，是否放弃修改", new UIHelp.OnConfirmDialogClickListener() {
                @Override
                public void onOkClick() {
                    finish();
                }

                @Override
                public void onCancelClick() {

                }
            });
        } else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

        }
        String result = null;
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                result = bundle.getString("result");
            }
        }

        switch (requestCode) {
            case PHOTO_ALBUM_GET_RESULT_CODE:
                if (data != null && data.getData() != null) {
                    //iconFile = PhotoUtil.cropPhotoIntent4HD(this, data.getData(), PHOTO__ALBUM_FIX_RESULT_CODE);
                }
                break;
            case PHOTO_CAMERA_FIX_RESULT_CODE:
            case PHOTO__ALBUM_FIX_RESULT_CODE:

                break;
        }
    }

}
