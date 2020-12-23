package com.coursework;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
/***
 * Класс, отвечающий за все манипуляции с фотографиями *
 */
public class PhotoAdder extends AppCompatActivity {
    static public ArrayList <Bitmap> photos=new ArrayList<>();
    static int ADD_PHOTO=1;
    static int currentPhoto=0;
    static ImageView imageView;


    public static void addPhoto(Activity activity)
{
    photos.clear();
    //Вызываем стандартную галерею для выбора изображения с помощью Intent.ACTION_PICK:
    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

    photoPickerIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
    //Тип получаемых объектов - image:
   photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
    photoPickerIntent.setType("image/*");
    //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
    ActivityCompat.startActivityForResult(activity,photoPickerIntent,ADD_PHOTO,null);


}

    public  void swapPhoto(int id)
    {
        ImageView imageView=findViewById(id);
        if(photos.size()>1) {
            if (currentPhoto < photos.size()-1){
                currentPhoto++;
                imageView.setImageBitmap(photos.get(currentPhoto)); }
                else {
                    currentPhoto=0;
                    imageView.setImageBitmap(photos.get(currentPhoto));}
        }


    }

}
