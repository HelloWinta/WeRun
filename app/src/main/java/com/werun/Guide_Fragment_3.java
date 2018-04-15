package com.werun;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.werun.Crop.CropImageUtils;
import com.zkk.view.rulerview.RulerView;

import org.litepal.LitePal;


public class Guide_Fragment_3 extends Fragment implements View.OnClickListener{

    private static final int CHOOSE_PICTURE = 0;
    private static final int TAKE_PICTURE = 1;

    private boolean registerSex; // false is boy  true is girl
    private double registerHeight;
    private double registerWidth;
    private String registerIcon;
    private String registerMotto;
    private double targetWidth;

    private CropImageUtils cropImageUtils;

    private RulerView Ruler_Target_Weight;

    private RoundedImageView RIV_Register_User;
    private EditText ET_Register_Motto;
    private TextView TV_Register_Target_Weight_Value;
    private Button BTN_Register;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        registerSex = bundle.getBoolean("Register_Sex");
        registerHeight = Double.parseDouble(bundle.getString("Register_Height"));
        registerWidth = Double.parseDouble(bundle.getString("Register_Width"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide_fragment_3, container, false);

        initView(view);
        initData();
        listenerWeight();
        return view;
    }

    private void initView(View view) {
        RIV_Register_User = (RoundedImageView) view.findViewById(R.id.RIV_Register_User);
        Ruler_Target_Weight = (RulerView) view.findViewById(R.id.Ruler_Target_Weight);
        ET_Register_Motto = (EditText) view.findViewById(R.id.ET_Register_Motto);
        TV_Register_Target_Weight_Value = (TextView) view.findViewById(R.id.TV_Register_Target_Weight_Value);
        BTN_Register = (Button) view.findViewById(R.id.BTN_Register);
    }

    private void initData() {

        RIV_Register_User.setOnClickListener(this);
        BTN_Register.setOnClickListener(this);
        Ruler_Target_Weight.setValue(55, 20, 200, 0.1f);
        cropImageUtils = new CropImageUtils(this);
    }

    private void listenerWeight() {
        Ruler_Target_Weight.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                TV_Register_Target_Weight_Value.setText(value+"");
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.RIV_Register_User:
                showChoosePicDialog(view);
                break;
            case R.id.BTN_Register:
                registerMotto = ET_Register_Motto.getText().toString();
                targetWidth = Double.valueOf(TV_Register_Target_Weight_Value.getText().toString());
                LitePal.getDatabase();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            default:
                break;
        }
    }


    //配置 dialog
    private void showChoosePicDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("设置头像");
        String[] items = {"选择本地照片", "拍照"};
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case CHOOSE_PICTURE://选择本地照片
                        cropImageUtils.openAlbum();
                        break;
                    case TAKE_PICTURE:
                        cropImageUtils.takePhoto();
                        break;
                }
            }
        });
        builder.create().show();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cropImageUtils.onActivityResult(requestCode, resultCode, data, new CropImageUtils.OnResultListener()
        {
            @Override
            public void takePhotoFinish(String path)
            {
                //拍照回调，去裁剪
                cropImageUtils.cropPicture(path);
            }

            @Override
            public void selectPictureFinish(String path)
            {
                //相册回调，去裁剪
                cropImageUtils.cropPicture(path);
            }

            @Override
            public void cropPictureFinish(String path)
            {
                //裁剪回调
                registerIcon = path;
                Bitmap bitmap= BitmapFactory.decodeFile(path);
                RIV_Register_User.setImageBitmap(bitmap);
            }
        });


    }




}
