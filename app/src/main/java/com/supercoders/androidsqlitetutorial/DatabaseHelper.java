package com.supercoders.androidsqlitetutorial;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String  DATABASE_NAME="product_db";
    private static final String TABLE_NAME="products";
    private static final String ID="id";
    private static final String name="name";
    private static final String price="price";
    private static final String city="city";
    private static final String description="description";
    private static final String created_at="created_at";


    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        String table_query="CREATE TABLE if not EXISTS "+TABLE_NAME+
                "("+
                ID+" INTEGER PRIMARY KEY,"+
                name+" TEXT ,"+
                price+" TEXT ,"+
                city+ " TEXT ,"+
               description+" TEXT ,"+
                created_at+ " TEXT "+
                ")";
        db.execSQL(table_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
     }

    public void AddProduct(ProductModel productModel){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(name,productModel.getName());
        contentValues.put(price,productModel.getPrice());
        contentValues.put(city,productModel.getCity());
        contentValues.put(description,productModel.getDescription());
        contentValues.put(created_at,productModel.getCreated_at());
        db.insert(TABLE_NAME,null,contentValues);
        db.close();
    }

    public ProductModel getProduct(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_NAME,new String[]{ID,name,price,city,description,created_at},ID+" = ?",new String[]{String.valueOf(id)},null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        ProductModel productModel=new ProductModel(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
        db.close();
        return productModel;
    }
    public ProductModel searchProduct(String name){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_NAME,new String[]{ID,name,price,city,description,created_at},name+" = ?",new String[]{String.valueOf(name)},null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        ProductModel productModel=new ProductModel(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
        db.close();
        return productModel;
    }
    public List<ProductModel> getAllProducts(){
        List<ProductModel> productModelList=new ArrayList<>();
        String query="SELECT * from "+TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                ProductModel productModel=new ProductModel(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(4),cursor.getString(3),cursor.getString(5));
                productModelList.add(productModel);
            }
            while (cursor.moveToNext());

        }
        db.close();
        return productModelList;
    }

    public int updateProduct(ProductModel productModel){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(name,productModel.getName());
        contentValues.put(price,productModel.getPrice());
        contentValues.put(city,productModel.getCity());
        contentValues.put(description,productModel.getDescription());
        return db.update(TABLE_NAME,contentValues,ID+"=?",new String[]{String.valueOf(productModel.getId())});

    }

    public void deleteProduct(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,ID+"=?",new String[]{id});
        db.close();
    }

    public int getTotalCount(){
        String query="SELECT * from "+TABLE_NAME;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(query,null);
        return cursor.getCount();
    }
}
