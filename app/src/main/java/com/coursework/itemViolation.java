package com.coursework;

import android.app.Notification;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import static com.coursework.PhotoAdder.currentPhoto;
import static com.coursework.PhotoAdder.photos;

public class itemViolation extends Fragment implements View.OnClickListener {

PhotoAdder ph;
    ArrayList<String> data;
    ArrayList<Bitmap> allPhoto;
    ImageView photo;

    int  currentPh=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      //  return super.onCreateView(inflater, container, savedInstanceState);

        View rootView=inflater.inflate(R.layout.violation_item,container,false);

        data=new ArrayList<>();

        allPhoto=new ArrayList<>();
        TextView auto = (TextView) rootView.findViewById(R.id.auto);
        TextView date = (TextView) rootView.findViewById(R.id.date);
        TextView location = (TextView) rootView.findViewById(R.id.location);

        photo = (ImageView) rootView.findViewById(R.id.images);



        Bundle bundle = this.getArguments();
        if (bundle != null) {
            data = DataManager.outputInfoAboutViolations(getContext(),bundle.getInt("id"));
            allPhoto = DataManager.getPhotos(getContext(),bundle.getInt("id"));


        }
        if(data.size()>0)
        {
            auto.setText(data.get(0));
            date.setText(data.get(1));
            location.setText(data.get(2));
        }
        if(allPhoto.size()>0)
        {
            photo.setImageBitmap(allPhoto.get(0));
        }
        photo.setOnClickListener(this);

        return rootView;

    }



    @Override
    public void onClick(View v) {


            ImageView imageView=v.findViewById(R.id.images);
            if(allPhoto.size()>1) {
                if (currentPh < allPhoto.size()-1){
                    currentPh++;
                    imageView.setImageBitmap(allPhoto.get(currentPh)); }
                else {
                    currentPh=0;
                    imageView.setImageBitmap(allPhoto.get(currentPh));}


            }


        }

    }


