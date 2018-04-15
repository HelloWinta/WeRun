package com.werun;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Guide_Fragment_1 extends Fragment implements View.OnClickListener{

    private Button BTN_Next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guide_fragment_1, container, false);

        initView(view);
        initData();


        return view;

    }

    private void initView(View view) {
        BTN_Next = (Button) view.findViewById(R.id.BTN_Next_Fragment2);
    }

    private void initData() {
        BTN_Next.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BTN_Next_Fragment2:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_guide_content, new Guide_Fragment_2(), null)
                        .addToBackStack("gf1")
                        .commit();
                break;
            default:
                break;
        }
    }

}
