package com.example.tacademy.sampleaddressbook1;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Tacademy on 2016-02-16.
 */
public class AddressView extends FrameLayout{
    public AddressView(Context context) {
        super(context);
        init();
    }

    TextView nameView, phoneView, homeView, officeView;
    private void init(){
        inflate(getContext(), R.layout.view_address, this);
        nameView = (TextView)findViewById(R.id.text_name);
        phoneView = (TextView)findViewById(R.id.text_phone);
        homeView = (TextView)findViewById(R.id.text_home);
        officeView = (TextView)findViewById(R.id.text_office);
    }

    AddressData data;
    public void setAddressData(AddressData data){
        this.data = data;
        nameView.setText(data.name);
        phoneView.setText(data.phone);
        homeView.setText(data.home);
        officeView.setText(data.office);
    }
}
