package com.adc.mycircle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupPage extends AppCompatActivity {

    private ImageView groupCoverPhoto;
    private CircleImageView groupProfileImage;
    private TextView groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);

        groupCoverPhoto = (ImageView) findViewById(R.id.group_cover_photo);
        groupProfileImage = (CircleImageView) findViewById(R.id.group_profile_image);
        groupName = (TextView) findViewById(R.id.group_name_for_group_page);

    }
}
