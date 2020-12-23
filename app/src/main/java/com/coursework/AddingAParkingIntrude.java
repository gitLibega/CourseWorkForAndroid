package com.coursework;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddingAParkingIntrude extends AppCompatActivity {
public EditText numberAuto;
public int status=0;
    public TextView time;
    public EditText location;
    public Button addPhoto;
    public Button addviolation;
    public ImageView photo;
    public ArrayList violationComponent;
    public ArrayList photoContainer;
    TextView txt;
    public ArrayList<Double> kek=new ArrayList<>();
    PhotoAdder ph;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_adding_a_parking_intrude);
        MyLocationListener.SetUpLocationListener(this);
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:MM");
        Date date = new Date();
        numberAuto= findViewById(R.id.number);
        violationComponent= new ArrayList();
        time=findViewById(R.id.dateAndTime);
        time.setText(dateFormat.format(date));
        ph=new PhotoAdder();
        photo=findViewById(R.id.imageView2);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ph.swapPhoto(R.id.imageView2);

            }
        });



        location=findViewById(R.id.adress);
        location.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    if(location.getText().toString().length()>0)
                    {
                        try {
                            if(ValidationChecker.checkLocation(location.getText().toString(),getApplicationContext()))
                                location.setBackgroundColor(getResources().getColor(R.color.colorSetAccess));
                            else if(!ValidationChecker.checkLocation(location.getText().toString(),getApplicationContext()))
                                location.setBackgroundColor(getResources().getColor(R.color.colorGetAccess));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }
        });
        numberAuto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {

                    if (ValidationChecker.checkNumber(numberAuto.getText().toString())) {
                        numberAuto.setBackgroundColor(getResources().getColor(R.color.colorSetAccess));
                    } else {
                        numberAuto.setBackgroundColor(getResources().getColor(R.color.colorGetAccess));
                    }

                }
            }
        });
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {

                    try {
                        ClipData cd = data.getClipData();
                        if(data!=null) {
                            if (cd == null) {
                                final Uri imageUri = data.getData();
                                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                                ph.photos.add(selectedImage);

                            }

                            //Получаем URI изображения, преобразуем его в Bitmap
                            //объект и отображаем в элементе ImageView нашего интерфейса:
                            else {
                                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                                    final Uri imageUri = data.getClipData().getItemAt(i).getUri();
                                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                                    ph.photos.add(selectedImage);
                                }

                            }
                            ImageView imageView = findViewById(R.id.imageView2);
                            imageView.setImageBitmap(ph.photos.get(0));
                            txt = findViewById(R.id.textView7);
                            txt.setText(String.valueOf(String.valueOf(PhotoAdder.photos.size())) + " добавлено");
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();

                    }
                }
        }
    }





    public void addAnyThing(View view) throws IOException {

        switch (view.getId()) {

            case R.id.addPhotoBtn:

          PhotoAdder.addPhoto(this);
                //Вызываем стандартную галерею для выбора изображения с помощью Intent.ACTION_PICK:
                //Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                //photoPickerIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                //Тип получаемых объектов - image:
               // photoPickerIntent.setType("image/*");
                //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
              // startActivityForResult(photoPickerIntent,1);
                break;
            case R.id.saveViolation:

               if(ValidationChecker.checkLocation(location.getText().toString(),getApplicationContext())&&
                       ValidationChecker.checkNumber(numberAuto.getText().toString()) &&
               ValidationChecker.checkCountPhoto())
               {
                   ArrayList data=new ArrayList();
                   data.add(numberAuto.getText().toString().toUpperCase());
                   data.add(time.getText().toString());
                   data.add(location.getText().toString());

                   DataManager.addDataInDB(data,getApplicationContext());

                   for(int i=0;i<PhotoAdder.photos.size();i++)
                   {
                       DataManager.addPhotoInDB(PhotoAdder.photos.get(i),getApplicationContext());
                   }
                   Toast toast = Toast.makeText(getApplicationContext(),
                           "Нарушение добавлено!", Toast.LENGTH_SHORT);
                   toast.show();

                   startActivity( new Intent(AddingAParkingIntrude.this,MainMenu.class));
                   return;
               }
               if(!ValidationChecker.checkLocation(location.getText().toString(),getApplicationContext()))
               {
                   Toast toast = Toast.makeText(getApplicationContext(),
                           "Такого адресса не существует!", Toast.LENGTH_SHORT);
                   toast.show();
                   return;
               }
                if(!ValidationChecker.checkNumber(numberAuto.getText().toString()))
            {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Введите номер корректно!", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
                if(!ValidationChecker.checkCountPhoto())
                {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Добавьте фото", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                break;
            case R.id.myLocation:
              location.setText(MyLocationListener.getCountryInfo(MyLocationListener.imHere,getApplicationContext()).getAddressLine(0));


        }
    }
}
