package com.coursework;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
/***
* Класс, отвечающий за проверку данных, перед занесением их в бд
*/
public  class ValidationChecker {
    /***
     * проверка текста на формат номера автомобиля
     * @param numberAuto - входный текст
     * @return bool
     */
    public static boolean checkNumber(String numberAuto)
    {
        if (numberAuto.matches("[а-я,А-Я]\\d{3}[а-я,А-Я]{2}\\d{2,3}")) return true;
        else return false;
    }

    /***
     * проверка на существование такого адресса
     * @param location-входной адресс
     * @param context-текущий контекст
     * @return bool
     * @throws IOException
     */
    public static boolean checkLocation(String location, Context context) throws IOException
       {if(location.length()>0)
       {
           ArrayList<Double> kek = MyLocationListener.getLocationLatLong(context,location);
           if(kek.get(0)!=0 && kek.get(1)!=0) return true;
           else return false;
        }
        else {
           return false;
       }
}

    /***
     * проверка количества фото
     * @return
     */
        public static boolean checkCountPhoto()
        {
            if(PhotoAdder.photos.size()>0)return true;
            else return false;
        }

}




