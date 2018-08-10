package com.dai.daicommon.ui.activity.camera;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dai.daicommon.R;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;

import java.io.File;
import java.util.ArrayList;

public class CustomerCameraActivity extends TakePhotoActivity {
    //是否裁剪
    private boolean isCrop = true;
    private TakePhoto takePhoto;
    //可最多照片选择数
    private int limit = 5;
    //是否从文件中选择照片
    private boolean isFromFile = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        takePhoto = getTakePhoto();
        initView();
    }

    private void initView() {
        //拍照
        findViewById(R.id.btn_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置压缩
                configCompress(takePhoto,true,true,false);
                //设置是否自己纠正拍照角度
                configTakePhotoOption(takePhoto,true);
                Uri imageUri = getImgUri();
                if (isCrop) {
                    //裁剪
                    takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions(800,800,1,false));
                } else {
                    takePhoto.onPickFromCapture(imageUri);
                }
            }
        });
        //从相册选择
        findViewById(R.id.btn_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri imageUri = getImgUri();
                //选择多个
                if (limit > 1) {
                    if (isCrop) {
                        takePhoto.onPickMultipleWithCrop(limit, getCropOptions(800,800,1,false));
                    } else {
                        takePhoto.onPickMultiple(limit);
                    }
                }else{
                    //一个
                    if (isFromFile) {
                        if (isCrop) {
                            takePhoto.onPickFromDocumentsWithCrop(imageUri, getCropOptions(800,800,1,false));
                        } else {
                            takePhoto.onPickFromDocuments();
                        }
                        return;
                    } else {
                        if (isCrop) {
                            takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions(800,800,1,false));
                        } else {
                            takePhoto.onPickFromGallery();
                        }
                    }
                }
            }
        });
    }

    //创建一个图片的标识 file://
    private Uri getImgUri(){
        File file = new File(Environment.getExternalStorageDirectory(), "/daiCommon/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);
        return imageUri;
    }

    //压缩设置 TakePhoto对象，是否压缩，是否保存原图，是否使用第三方Lunban压缩
    private void configCompress(TakePhoto takePhoto,boolean isCompress,boolean enableRawFile,boolean isLunban) {
        if (!isCompress) {
            //不压缩
            takePhoto.onEnableCompress(null, false);
            return;
        }
        int maxSize = 102400;//设置压缩后最大值不超过
        int width = 800;//压缩后宽
        int height = 800;//压缩后高
        boolean showProgressBar = true;//是否显示压缩进度条
        CompressConfig config;
        if (isLunban) {
            //自带压缩
            config = new CompressConfig.Builder().setMaxSize(maxSize)
                    .setMaxPixel(width >= height ? width : height)
                    .enableReserveRaw(enableRawFile)
                    .create();
        } else {
            //Luban第三方库压缩
            LubanOptions option = new LubanOptions.Builder().setMaxHeight(height).setMaxWidth(width).setMaxSize(maxSize).create();
            config = CompressConfig.ofLuban(option);
            config.enableReserveRaw(enableRawFile);
        }
        takePhoto.onEnableCompress(config, showProgressBar);
    }

    //设置是否可自己纠正拍照的照片角度
    private void configTakePhotoOption(TakePhoto takePhoto,boolean isCorrect) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(true);
        builder.setCorrectImage(isCorrect);
        takePhoto.setTakePhotoOptions(builder.create());
    }

    //裁剪设置 height高，width宽，wayType裁剪方式 1：宽x高  2：宽/高,isOwnCrop置使用自带的裁剪 false使用第三方的
    private CropOptions getCropOptions(int height,int width,int wayType,boolean isOwnCrop) {
        CropOptions.Builder builder = new CropOptions.Builder();
        //两种设置宽高方式
        if (wayType == 1) {
            builder.setOutputX(width).setOutputY(height);//宽x高
        } else {
            builder.setAspectX(width).setAspectY(height); //宽/高
        }
        builder.setWithOwnCrop(isOwnCrop);//设置使用自带的裁剪 false使用第三方的
        return builder.create();
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    //拍照,选择照片返回
    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        showImg(result.getImages());
    }

    private void showImg(ArrayList<TImage> images) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("images", images);
        startActivity(intent);
    }
}
