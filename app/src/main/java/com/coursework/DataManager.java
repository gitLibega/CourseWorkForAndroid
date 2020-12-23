package com.coursework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
/***
* Класс отвечающий за все манипуляции связанные с бд в данном проекте
*/
public class DataManager {
    private static SQLiteDatabase db;
    private static dbHelper dbh;
    private static ContentValues cv;
    private static ArrayList list=new ArrayList();

    //контейнеры фильров
    static ArrayList<String> filtersCities=new ArrayList<>();
    static  ArrayList<String> filtersDate=new ArrayList<>();
//контейнеры запросов для фильтрации
    public static String DEFAULT_QUERY_FOR_SELECT_ALL="select "+
        dbh.KEY_ID+" from "+dbh.TABLE_violations;
    public static   String CUSTOM_QUERY_WHERE="select "+
            dbh.KEY_ID+" from "
            +dbh.TABLE_violations+" WHERE";
    public static String DEFAULT_QUERY_FOR_SELECT_ALL_ADDRESSES ="select "
            + dbh.KEY_VIOLATIONGPS+
            " from "+dbh.TABLE_violations;
    public static   String CUSTOM_QUERY_WHERE_FOR_MAP="select "+
            dbh.KEY_VIOLATIONGPS+
            " from "+dbh.TABLE_violations+" WHERE";


    /***
     * Добавление данных в бд
     * @param data -контейнер с данными что нужно передать
     * @param context-текущий контекст программы
     */
    public static void addDataInDB(ArrayList<String> data , Context context)
    {
        cv=new ContentValues();
        dbh=new dbHelper(context);

        list.add(dbh.KEY_NUMBERAUTO);
        list.add(dbh.KEY_VIOLATIONDATE);
        list.add(dbh.KEY_VIOLATIONGPS);

        for (int i = 0; i <list.size() ; i++) {
            cv.put((String) list.get(i),data.get(i));
        }
        db=dbh.getWritableDatabase();
        db.insert(dbh.TABLE_violations, null, cv);
    }

    /***
     * выборка минимальной даты
     * @param context-текущий контекст
     * @return
     */
    public static String minDate(Context context)
    {
        String date="";
        cv=new ContentValues();
        dbh=new dbHelper(context);

        Cursor cursor = db.rawQuery("select Min(Date) from violations", new String[]{});
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            date=cursor.getString(0);
            cursor.moveToNext();
        }
        return date;

    }

    /***
     * выборка максимальной даты
     * @param context-текущий контекст
     * @return
     */
    public static String maxDate(Context context)
    {
        String date="";
        cv=new ContentValues();
        dbh=new dbHelper(context);

        Cursor cursor = db.rawQuery("select Max(Date) from violations", new String[]{});
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            date=cursor.getString(0);
            cursor.moveToNext();
        }
        return date;

    }

    /***
     * добавляем фото в бд
     * @param photo-битмаповое представление изображения
     * @param context-текущий контекст
     */
    public static void addPhotoInDB(Bitmap photo, Context context)
    {
        int mx=-1;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        cv=new ContentValues();
        dbh=new dbHelper(context);
        db=dbh.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT MAX("+dbh.KEY_ID+") FROM "+dbh.TABLE_violations+"",new String [] {});
        if (cursor != null) if(cursor.moveToFirst())
        {
            mx= cursor.getInt(0);

        }
       cv.put(dbh.KEY_PHOTO, byteArray);
        cursor.moveToFirst();
       cv.put(dbh.ID_VIOLATION,mx);


        db.insert(dbh.TABLE_photos, null, cv);
    }

    /***
     * Получаем идентификаторы
     * @param context-текущий контекст
     * @param location- локация по которой идет выборка
     * @return идентификаторы с данной локацией
     */
    public  static ArrayList<Integer> getIdByLocation(Context context, String location)
    {
        {

            ArrayList<Integer> infoByLocation = new ArrayList<>();

            dbh = new dbHelper(context);
            db = dbh.getReadableDatabase();
            Cursor cursor = db.rawQuery("select "+ dbh.KEY_ID+" from "+dbh.TABLE_violations+" where "+dbh.KEY_VIOLATIONGPS+"='"+location+"'", new String[]{});
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                infoByLocation.add(cursor.getInt(cursor.getColumnIndex(dbh.KEY_ID)));
                cursor.moveToNext();
            }
            return infoByLocation;
        }
    }

    /***
     *
     * @param context-текущий контекст
     * @return возвращиаем идентификаторы нужных наружений
     */
    public  static ArrayList<String> outputViolations(Context context)
    {
        {
            String _query;
            ArrayList<String> infoAboutViolations = new ArrayList<>();
            if (filtersCities.size()>0 || filtersDate.size()>0 ) {
               _query=CUSTOM_QUERY_WHERE;
            }
            else{
                _query=DEFAULT_QUERY_FOR_SELECT_ALL;
            }
            dbh = new dbHelper(context);
            db = dbh.getReadableDatabase();
            Cursor cursor = db.rawQuery(_query, new String[]{});
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                infoAboutViolations.add(cursor.getString(cursor.getColumnIndex(dbh.KEY_ID)));
                cursor.moveToNext();
            }
            return infoAboutViolations;
        }
    }

    /***
     * получаем информацию по текущему id
     * @param context- текущий контекст
     * @param id-текущий id
     * @return информацию по id
     */
    public static ArrayList<String> outputInfoAboutViolations(Context context,int id)
    {
        ArrayList<String> infoAboutViolations=new ArrayList<>();
        dbh=new dbHelper(context);
        db=dbh.getReadableDatabase();
        Cursor cursor= db.rawQuery("select * from "+dbh.TABLE_violations+" where "+dbh.KEY_ID+"="+id,new String [] {});
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false)
        {
            infoAboutViolations.add(cursor.getString(cursor.getColumnIndex(dbh.KEY_NUMBERAUTO)));
            infoAboutViolations.add(cursor.getString(cursor.getColumnIndex(dbh.KEY_VIOLATIONDATE)));
            infoAboutViolations.add(cursor.getString(cursor.getColumnIndex(dbh.KEY_VIOLATIONGPS)));
            cursor.moveToNext();
        }
        return infoAboutViolations;
    }

    /***
     * получаем фотографии по текущему id
     * @param context-текущий контекст
     * @param id- текущий id
     * @return Коллекцию с битмаповым представлением изображений
     */
    public static ArrayList<Bitmap> getPhotos(Context context,int id)
    {
        ArrayList<Bitmap> photos=new ArrayList<>();
        dbh=new dbHelper(context);
        db=dbh.getReadableDatabase();
        Cursor cursor= db.rawQuery("select "+dbh.KEY_PHOTO+" from "+dbh.TABLE_photos+" where "+dbh.ID_VIOLATION+"="+id,new String [] {});
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false)
        {

            photos.add(BitmapFactory.decodeByteArray
                    (cursor.getBlob(cursor.getColumnIndex(dbh.KEY_PHOTO)),
                    0, cursor.getBlob(cursor.getColumnIndex(dbh.KEY_PHOTO)).length));
            cursor.moveToNext();
        }
        return photos;
    }

    /***
     * Выборка id по нужному адрессу
     * @param context- текущий контекст     *
     * @return
     */
    public static ArrayList<String> selectAllAddresses(Context context)
    {
        String _query;
        ArrayList <String> addresses=new ArrayList<>();
        if (filtersCities.size()>0 || filtersDate.size()>0 ) {
            _query=CUSTOM_QUERY_WHERE_FOR_MAP;
        }
        else{
            _query= DEFAULT_QUERY_FOR_SELECT_ALL_ADDRESSES;
        }
        dbh=new dbHelper(context);
        db=dbh.getReadableDatabase();
        Cursor cursor= db.rawQuery(_query,new String [] {});
        cursor.moveToFirst();
                while(cursor.isAfterLast()==false)
        {
            addresses.add(cursor.getString(cursor.getColumnIndex(dbh.KEY_VIOLATIONGPS)));
            cursor.moveToNext();
        }
        return addresses;
    }

}
