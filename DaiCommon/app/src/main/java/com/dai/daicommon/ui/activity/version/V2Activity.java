package com.dai.daicommon.ui.activity.version;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.allenliu.versionchecklib.callback.OnCancelListener;
import com.allenliu.versionchecklib.core.http.HttpHeaders;
import com.allenliu.versionchecklib.core.http.HttpParams;
import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.NotificationBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomDownloadFailedListener;
import com.allenliu.versionchecklib.v2.callback.CustomDownloadingDialogListener;
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener;
import com.allenliu.versionchecklib.v2.callback.ForceUpdateListener;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.dai.daicommon.R;
import com.dai.daicommon.app.API;
import com.dai.daicommon.service.bean.VersionBean;
import com.dai.daicommon.service.presenter.VersionPresenter;
import com.dai.daicommon.service.view.VersionView;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class V2Activity extends AppCompatActivity {

    private EditText etAddress;
    private RadioGroup radioGroup;
    private CheckBox forceUpdateCheckBox;
    private CheckBox silentDownloadCheckBox;
    private CheckBox silentDownloadCheckBoxAndInstall;

    private CheckBox forceDownloadCheckBox;
    private CheckBox onlyDownloadCheckBox;
    private CheckBox showNotificationCheckBox;
    private CheckBox showDownloadingCheckBox;
    private CheckBox customNotificationCheckBox;
    private CheckBox showDownloadFailedCheckBox;
    private RadioGroup radioGroup2, radioGroup3;
    private DownloadBuilder builder;
    private String updateTitle;//请求版本数据标题
    private String apkUrl;//新包下载地址
    private String updateContent;//版本更新内容
    private VersionPresenter versionPresenter = new VersionPresenter();
    private VersionView vv = new VersionView() {
        @Override
        public void onSuccess(VersionBean vb) {
            updateTitle = vb.getUploadTime();
            apkUrl = vb.getDownloadUrl();
            updateContent = vb.getModifyContent();
            Log.i("dai","请求成功:"+updateTitle+".."+apkUrl+".."+updateContent);
        }

        @Override
        public void onError(String result) {
            Log.i("dai","请求出错:"+result);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v2);
        initView();
        versionPresenter.onCreate();
        versionPresenter.attachView(vv);
    }

    private void initView() {
        etAddress = findViewById(R.id.etAddress);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup2 = findViewById(R.id.radioGroup2);
        radioGroup3 = findViewById(R.id.radioGroup3);

        silentDownloadCheckBox = findViewById(R.id.checkbox2);
        forceUpdateCheckBox = findViewById(R.id.checkbox);
        forceDownloadCheckBox = findViewById(R.id.checkbox3);
        onlyDownloadCheckBox = findViewById(R.id.checkbox4);
        showNotificationCheckBox = findViewById(R.id.checkbox5);
        showDownloadingCheckBox = findViewById(R.id.checkbox6);
        customNotificationCheckBox = findViewById(R.id.checkbox7);
        showDownloadFailedCheckBox = findViewById(R.id.checkbox8);
        silentDownloadCheckBoxAndInstall=findViewById(R.id.checkbox20);

    }

    public void v2Click(View view) {
        switch (view.getId()) {
            case R.id.sendbtn:
                sendRequest();
                break;
            case R.id.cancelBtn:
                AllenVersionChecker.getInstance().cancelAllMission(this);
                break;
            case R.id.version_btn:
                //请求版本数据
                versionPresenter.getVersionInfo();
                break;
        }
    }

    private void sendRequest() {
        if (onlyDownloadCheckBox.isChecked()) {
            //请求版本使用retrofit，这里在获取到版本数据后下载APK
            builder = AllenVersionChecker
                    .getInstance()
                    .downloadOnly(crateUIData());
        } else {
//            //请求是否更新 及更新数据
//            builder = AllenVersionChecker
//                    .getInstance()
//                    .requestVersion()
//                    .setRequestUrl("https://raw.githubusercontent.com/xuexiangjys/XUpdate/master/jsonapi/update_test.json")
//                    .request(new RequestVersionListener() {
//                        @Nullable
//                        @Override
//                        public UIData onRequestVersionSuccess(String result) {
//                            Log.i("dai","...."+result);
//
//                            UIData uiData = UIData.create();
//                            uiData.setTitle(getString(R.string.update_title));
//                            uiData.setDownloadUrl("http://test-1251233192.coscd.myqcloud.com/1_1.apk");
//                            uiData.setContent(getString(R.string.updatecontent));
//                            return uiData;
//                        }
//
//                        @Override
//                        public void onRequestVersionFailure(String message) {
//                            Toast.makeText(V2Activity.this, "request failed", Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
        }
        if (forceUpdateCheckBox.isChecked()) {
            builder.setForceUpdateListener(new ForceUpdateListener() {
                @Override
                public void onShouldForceUpdate() {
                    forceUpdate();
                }
            });
        }
        if (silentDownloadCheckBox.isChecked())
            //静默下载
            builder.setSilentDownload(true);
        if (forceDownloadCheckBox.isChecked())
            //如果本地有安装包缓存也会重新下载apk
            builder.setForceRedownload(true);
        if (!showDownloadingCheckBox.isChecked())
            builder.setShowDownloadingDialog(false);
        if (!showNotificationCheckBox.isChecked())
            builder.setShowNotification(false);
        if (customNotificationCheckBox.isChecked())
            builder.setNotificationBuilder(createCustomNotification());
        if (!showDownloadFailedCheckBox.isChecked())
            builder.setShowDownloadFailDialog(false);
        if(silentDownloadCheckBoxAndInstall.isChecked()) {
            builder.setDirectDownload(true);
           builder.setShowNotification(false);
           builder.setShowDownloadingDialog(false);
           builder.setShowDownloadFailDialog(false);
        }


        //更新界面选择
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.btn1:
                break;
            case R.id.btn2:
                builder.setCustomVersionDialogListener(createCustomDialogOne());
                break;
            case R.id.btn3:
                builder.setCustomVersionDialogListener(createCustomDialogTwo());
                break;
        }

        //下载进度界面选择
        switch (radioGroup2.getCheckedRadioButtonId()) {
            case R.id.btn21:
                break;
            case R.id.btn22:
                builder.setCustomDownloadingDialogListener(createCustomDownloadingDialog());
                break;
        }
        //下载失败界面选择
        switch (radioGroup3.getCheckedRadioButtonId()) {
            case R.id.btn31:
                break;
            case R.id.btn32:
                builder.setCustomDownloadFailedListener(createCustomDownloadFailedDialog());
                break;
        }
        //自定义下载路径
//        builder.setDownloadAPKPath(Environment.getExternalStorageDirectory() + "/ALLEN/AllenVersionPath2/");
        String address = etAddress.getText().toString();
        if (address != null && !"".equals(address))
            builder.setDownloadAPKPath(address);


        builder.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel() {
                Toast.makeText(V2Activity.this,"Cancel Hanlde",Toast.LENGTH_SHORT).show();
            }
        });
        builder.excuteMission(this);
    }

    /**
     * 务必用库传回来的context 实例化你的dialog
     *
     * @return
     */
    private CustomDownloadFailedListener createCustomDownloadFailedDialog() {
        return new CustomDownloadFailedListener(){
            @Override
            public Dialog getCustomDownloadFailed(Context context, UIData versionBundle) {
                BaseDialog baseDialog = new BaseDialog(context, R.style.MyDialog, R.layout.custom_download_failed_dialog);
                return baseDialog;
            }
        };
    }

    /**
     * 强制更新操作
     * 通常关闭整个activity所有界面，这里方便测试直接关闭当前activity
     */
    private void forceUpdate() {
        Toast.makeText(this, "force update handle", Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * 自定义下载中对话框，下载中会连续回调此方法 updateUI
     * 务必用库传回来的context 实例化你的dialog
     *
     * @return
     */
    private CustomDownloadingDialogListener createCustomDownloadingDialog() {
        return new CustomDownloadingDialogListener() {
            @Override
            public Dialog getCustomDownloadingDialog(Context context, int progress, UIData versionBundle) {
                BaseDialog baseDialog = new BaseDialog(context, R.style.MyDialog, R.layout.custom_download_layout);
                return baseDialog;
            }

            @Override
            public void updateUI(Dialog dialog, int progress, UIData versionBundle) {
                TextView tvProgress = dialog.findViewById(R.id.tv_progress);
                ProgressBar progressBar = dialog.findViewById(R.id.pb);
                progressBar.setProgress(progress);
                tvProgress.setText(getString(R.string.versionchecklib_progress, progress));
            }
        };
    }

    /**
     * 务必用库传回来的context 实例化你的dialog
     * 自定义的dialog UI参数展示，使用versionBundle
     *
     * @return
     */
    private CustomVersionDialogListener createCustomDialogOne() {
        return new CustomVersionDialogListener(){
            @Override
            public Dialog getCustomVersionDialog(Context context, UIData versionBundle) {
                BaseDialog baseDialog = new BaseDialog(context, R.style.MyDialog, R.layout.custom_dialog_one_layout);
                TextView textView = baseDialog.findViewById(R.id.tv_msg);
                textView.setText(versionBundle.getContent());
                return baseDialog;
            }
        };
    }

    private CustomVersionDialogListener createCustomDialogTwo() {
        return new CustomVersionDialogListener(){
            @Override
            public Dialog getCustomVersionDialog(Context context, UIData versionBundle) {
                BaseDialog baseDialog = new BaseDialog(context, R.style.MyDialog, R.layout.custom_dialog_two_layout);
                TextView textView = baseDialog.findViewById(R.id.tv_msg);
                textView.setText(versionBundle.getContent());
                baseDialog.setCanceledOnTouchOutside(true);
                return baseDialog;
            }
        };
    }

    private NotificationBuilder createCustomNotification() {
        return NotificationBuilder.create()
                .setRingtone(true)
                .setIcon(R.mipmap.dialog4)
                .setTicker("custom_ticker")
                .setContentTitle("custom title")
                .setContentText(getString(R.string.custom_content_text));
    }

    /**
     * @return
     * @important 使用请求版本功能，可以在这里设置downloadUrl
     * 这里可以构造UI需要显示的数据
     * UIData 内部是一个Bundle
     */
    private UIData crateUIData() {
        UIData uiData = UIData.create();
        uiData.setTitle(updateTitle);
        uiData.setDownloadUrl(apkUrl);
        uiData.setContent(updateContent);
        return uiData;
    }
}
