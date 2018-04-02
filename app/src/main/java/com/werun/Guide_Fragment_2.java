package com.werun;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zkk.view.rulerview.RulerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Guide_Fragment_2 extends Fragment implements View.OnClickListener{

    private RulerView ruler_height;
    private RulerView ruler_weight;

    private TextView TV_Register_Height_Value;
    private TextView TV_Register_Weight_Value;

    private Button BTN_Register;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guide_fragment_2, container, false);
        initView(view);
        initData();
        listenHeight();
        listenWeight();
        return view;
    }

    private void initView(View view) {
        ruler_height = (RulerView) view.findViewById(R.id.Ruler_Height) ;
        ruler_weight = (RulerView) view.findViewById(R.id.Ruler_Weight);
        TV_Register_Height_Value = (TextView) view.findViewById(R.id.TV_Register_Height_Value);
        TV_Register_Weight_Value = (TextView) view.findViewById(R.id.Tv_Register_Weight_Value);
        BTN_Register = (Button) view.findViewById(R.id.BTN_Register);
    }

    private void initData() {
        BTN_Register.setOnClickListener(this);
        ruler_height.setValue(165, 80, 250,0.1f);
        ruler_weight.setValue(55,20,200,0.1f);

    }

    private void listenHeight() {
        ruler_height.setOnValueChangeListener(new RulerView.OnValueChangeListener(){
            @Override
            public void onValueChange(float value) {
                TV_Register_Height_Value.setText(value + "");
            }
        });
    }

    private void listenWeight() {
        ruler_weight.setOnValueChangeListener(new RulerView.OnValueChangeListener(){
            @Override
            public void onValueChange(float value) {
                TV_Register_Weight_Value.setText(value + "");
            }
        });
    }

    @Override
    public void onClick(View view) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_guide_content, new Guide_Fragment_3(), null)
                .addToBackStack("gf2")
                .commit();
    }



}
