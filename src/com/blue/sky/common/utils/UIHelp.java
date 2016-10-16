package com.blue.sky.common.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.blue.sky.h5.game.R;

/**
 * UI界面帮助类
 *
 */
public class UIHelp
{

    public final static String TAG = "UIHelp";

    /**
     * 默认时间LENGTH_LONG
     *
     * @param msg
     */
    public static void showToast(Context context,String msg) {

        showToast(context, msg, Toast.LENGTH_LONG);
    }

    /**
     * 不会一直重复重复重复重复的提醒了
     *
     * @param msg
     * @param length
     *            显示时间
     */
    protected static void showToast(Context context, String msg, int length) {
        Toast toast = Toast.makeText(context, msg, length);
        toast.show();
    }

    /**
     * 提示框
     *
     * @param context
     * @param content
     *            内容
     */
    public static void showAlertDialog(Context context, String content)
    {
        showDialog(context, "", content, true, null);
    }

    /**
     * 提示框
     *
     * @param context
     * @param title
     *            标题
     * @param content
     *            内容
     */
    public static void showAlertDialog(Context context, String title, String content)
    {
        showDialog(context, title, content, true, null);
    }

    /**
     * 提示框
     *
     * @param context
     * @param content
     *            内容
     * @param onConfirmDialogClickListener
     *            需要实现接口{@link OnConfirmDialogClickListener}
     */
    public static void showAlertDialog(Context context, String content,
                                       UIHelp.OnConfirmDialogClickListener onConfirmDialogClickListener)
    {
        showDialog(context, "", content, true, onConfirmDialogClickListener);
    }

    /**
     * 提示框
     *
     * @param context
     * @param title
     *            标题
     * @param content
     *            内容
     * @param onConfirmDialogClickListener
     *            需要实现接口{@link OnConfirmDialogClickListener}
     */
    public static void showAlertDialog(Context context, String title, String content,
                                       UIHelp.OnConfirmDialogClickListener onConfirmDialogClickListener)
    {
        showDialog(context, title, content, true, onConfirmDialogClickListener);
    }

    /**
     * 二次确认框
     *
     * @param context
     *            内容
     * @param onConfirmDialogClickListener
     *            需要实现接口{@link OnConfirmDialogClickListener}
     */
    public static void showConfirmDialog(Context context, String content,
                                         UIHelp.OnConfirmDialogClickListener onConfirmDialogClickListener)
    {
        showDialog(context, "", content, false, onConfirmDialogClickListener);
    }

    /**
     * 二次确认框
     *
     * @param context
     * @param title 标题
     * @param content 内容
     * @param onConfirmDialogClickListener
     *            需要实现接口{@link OnConfirmDialogClickListener}
     */
    public static void showConfirmDialog(Context context, String title, String content,
                                         UIHelp.OnConfirmDialogClickListener onConfirmDialogClickListener)
    {
        showDialog(context, title, content, false, onConfirmDialogClickListener);
    }

    /**
     * 弹出框
     *
     * @param context
     *            上下文
     * @param title
     *            标题
     * @param content
     *            内容
     * @param onlyOkButton
     *            只有确定按钮，没有取消按钮
     * @param onConfirmDialogClickListener
     *            执行代码
     *
     */
    protected static void showDialog(Context context, String title, String content, boolean onlyOkButton,
                                     UIHelp.OnConfirmDialogClickListener onConfirmDialogClickListener)
    {
        showDialog(context, title, content, "取消", "确定", onlyOkButton, onConfirmDialogClickListener);
    }

    /**
     * 弹出框（完整方法）
     *
     * @param context
     * @param title
     *            标题
     * @param content
     *            内容
     * @param cancelStr
     *            取消按钮文字
     * @param okStr
     *            确定按钮文字
     * @param onlyOkButton
     *            只有确定按钮，没有取消按钮
     * @param onConfirmDialogClickListener
     *            需要实现接口{@link OnConfirmDialogClickListener}
     *
     */
    protected static void showDialog(final Context context, String title, String content, String cancelStr,
                                     String okStr, boolean onlyOkButton, final UIHelp.OnConfirmDialogClickListener onConfirmDialogClickListener)
    {
        // 设置主题样式-context
        final Dialog dialog = new Dialog(context, R.style.exit_dialog);
        dialog.setContentView(R.layout.sky_common_dialog);

        // 设置标题-title
        TextView titleTv = (TextView) dialog.findViewById(R.id.title);
        titleTv.setText(Strings.isEmpty(title) ? "提示" : title);

        // 设置内容-content
        TextView contentTv = (TextView) dialog.findViewById(R.id.content);
        contentTv.setText(content);

        // 是否显示取消按钮
        View cancle = dialog.findViewById(R.id.cancelLayout);
        cancle.setVisibility(onlyOkButton ? View.GONE : View.VISIBLE);

        // 取消按钮
        Button btnCancel = (Button) dialog.findViewById(R.id.bt_cancel);
        btnCancel.setText(Strings.isEmpty(cancelStr) ? "取消" : cancelStr);
        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (onConfirmDialogClickListener != null)
                {
                    onConfirmDialogClickListener.onCancelClick();
                }
                dialog.dismiss();
            }
        });

        // 确定按钮
        Button btnOK = (Button) dialog.findViewById(R.id.bt_confirm);
        btnOK.setText(Strings.isEmpty(okStr) ? "确定" : okStr);
        btnOK.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (onConfirmDialogClickListener != null)
                {
                    onConfirmDialogClickListener.onOkClick();
                }
                dialog.dismiss();
            }
        });

        // 显示
        dialog.show();
    }

    /**
     * 二次确认Dialog中按钮点击的事件监听接口<br>
     * 该接口配合方法
     * {@link UIHelp#showConfirmDialog(Context, String, OnConfirmDialogClickListener)}
     * 一起使用<br>
     * 现在定义的方法是监听"确定"按钮点击事件
     *
     */
    public interface OnConfirmDialogClickListener
    {
        /**
         * 该方法只要实现具体业务逻辑就好. 方法执行完成后, 会自动关闭Dialog
         */
        public void onOkClick();

        /**
         * 如果点击取消, 有自己的业务要实现, 可以实现该方法; 否则空实现就行. 方法执行完毕自动关闭Dialog
         */
        public void onCancelClick();
    }


    public interface OnVerticalMenuClickListener
    {
        /**
         * 菜单第一个按钮的逻辑实现。在返回true，则表示实现的方法处理完毕。方法执行完成后,会自动关闭Dialog
         */
        public boolean onFirstClick();

        /**
         * 菜单第二个按钮的逻辑实现。方法执行完成后,会自动关闭Dialog
         */
        public boolean onSecondClick();

        /**
         * 如果点击取消,有自己的业务要实现,可以实现该方法; 否则空实现就行，方法执行完毕自动关闭Dialog
         */
        public boolean onCancelClick();
    }

    private static void setParams(LayoutParams lay, Activity context)
    {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Rect rect = new Rect();
        View view = context.getWindow().getDecorView();
        view.getWindowVisibleDisplayFrame(rect);
        lay.height = dm.heightPixels - rect.top;
        lay.width = dm.widthPixels;
    }

    /*
     * ChenRen/2013-07-06
     */
    private static ProgressDialog progressDialog;

    /** 默认显示"数据加载中..." */
    public static void showLoading(Context context)
    {
        showLoading(context, "数据加载中...");
    }

    /**
     *
     * @param context
     * @param message
     *            如果不传该参数, 默认显示"数据加载中...". 见: {@link #showLoading(Context)}
     */
    public static void showLoading(Context context, String message)
    {
        // 隐藏键盘
        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    public static void closeLoading()
    {
        if (progressDialog != null)
        {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /**
     * 发送App异常崩溃报告
     *
     * @param cont
     * @param crashReport
     */
    public static void sendAppCrashReport(final Context cont, final String crashReport)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(cont);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle("应用程序错误");
        builder.setMessage("很抱歉，应用程序出现错误，即将退出。\n请提交错误报告，我们会尽快修复这个问题！");
        builder.setPositiveButton("提交报告", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        });
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                // 退出
                // AppManager.getAppManager().AppExit(cont);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        });
        builder.show();
    }

    public static void hideSoftInputFromWindow(EditText editText)
    {
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static void showSoftInputFromWindow(EditText editText)
    {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
    }

    /**
     * ListView滑动到底部
     *
     * @param listView
     *
     */
    public static void listViewScrollToBottom(final ListView listView)
    {
        if (listView != null && listView.getAdapter() != null)
        {
            listViewSetSelection(listView, listView.getAdapter().getCount() - 1);
        }
    }

    /**
     * ListView滑动到指定Position
     *
     * @param listView
     * @param position
     */
    public static void listViewSetSelection(final ListView listView, final int position)
    {
        if (listView != null)
        {
            new Handler().postDelayed(new Runnable()
            {
                public void run()
                {
                    listView.setSelection(position);
                }
            }, 300);
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }

    public static String getPullRefreshLabel(Context context){
        return "更新于" + DateUtils.formatDateTime(context, System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
    }

}
