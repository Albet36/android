package com.supercoders.androidsqlitetutorial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    TextView datalist;
    TextView datalist_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper=new DatabaseHelper(MainActivity.this);
        Button delete=findViewById(R.id.delete_data);
        Button insert=findViewById(R.id.insert_data);
        Button update=findViewById(R.id.update_data);
        Button read=findViewById(R.id.refresh_data);
        Button search=findViewById(R.id.search_btn);
        datalist=findViewById(R.id.all_data_list);
        datalist_count=findViewById(R.id.data_list_count);

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               refreshData();

            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowInputDialog();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateIdDialog();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             showDeleteDialog();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchDialog();
            }
        });

    }

    private void refreshData() {
        datalist_count.setText("ALL DATA COUNT : "+databaseHelper.getTotalCount());

        List<ProductModel> productModelList=databaseHelper.getAllProducts();
        datalist.setText("");
        for(ProductModel productModel:productModelList){
            datalist.append("ID : "+productModel.getId()+" | Names : "+productModel.getName()+" | Email : "+productModel.getPrice()+" | city : "+productModel.getCity()+ " | PHONE : "+productModel.getDescription()+" \n\n");
        }
    }


    private void searchDialog() {
        AlertDialog.Builder al=new AlertDialog.Builder(MainActivity.this);
        View view=getLayoutInflater().inflate(R.layout.activity_main,null);
        al.setView(view);
        final EditText search_input=view.findViewById(R.id.search_edt);
        Button search_btn=view.findViewById(R.id.search_btn);
        final AlertDialog alertDialog=al.show();

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.searchProduct(search_input.getText().toString());
                alertDialog.dismiss();
                refreshData();

            }
        });


    }
    private void showDeleteDialog() {
        AlertDialog.Builder al=new AlertDialog.Builder(MainActivity.this);
        View view=getLayoutInflater().inflate(R.layout.delete_dialog,null);
        al.setView(view);
        final EditText id_input=view.findViewById(R.id.id_input);
        Button delete_btn=view.findViewById(R.id.delete_btn);
        final AlertDialog alertDialog=al.show();

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteProduct(id_input.getText().toString());
                alertDialog.dismiss();
                refreshData();

            }
        });


    }

    private void showUpdateIdDialog() {
        AlertDialog.Builder al=new AlertDialog.Builder(MainActivity.this);
        View view=getLayoutInflater().inflate(R.layout.update_id_dialog,null);
        al.setView(view);
        final EditText id_input=view.findViewById(R.id.id_input);
        Button fetch_btn=view.findViewById(R.id.update_id_btn);
        final AlertDialog alertDialog=al.show();
        fetch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataDialog(id_input.getText().toString());
                alertDialog.dismiss();
                refreshData();
            }
        });

    }

    private void showDataDialog(final String id) {
        ProductModel productModel=databaseHelper.getProduct(Integer.parseInt(id));
        AlertDialog.Builder al=new AlertDialog.Builder(MainActivity.this);
        View view=getLayoutInflater().inflate(R.layout.update_dialog,null);
        final EditText name=view.findViewById(R.id.name);
        final EditText price=view.findViewById(R.id.price);
        final EditText city=view.findViewById(R.id.city);
        final EditText description=view.findViewById(R.id.desctiption);
        Button update_btn=view.findViewById(R.id.update_btn);
        al.setView(view);

        name.setText(productModel.getName());
        price.setText(productModel.getPrice());
        city.setText(productModel.getCity());
        description.setText(productModel.getDescription());

        final AlertDialog alertDialog=al.show();
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductModel productModel=new ProductModel();
                productModel.setName(name.getText().toString());
                productModel.setId(id);
                productModel.setPrice(price.getText().toString());
                productModel.setCity(city.getText().toString());
                productModel.setDescription(description.getText().toString());
                databaseHelper.updateProduct(productModel);
                alertDialog.dismiss();
                refreshData();
            }
        });
    }

    private void ShowInputDialog() {
        AlertDialog.Builder al=new AlertDialog.Builder(MainActivity.this);
        View view=getLayoutInflater().inflate(R.layout.insert_dialog,null);
        final EditText name=view.findViewById(R.id.name);
        final EditText price=view.findViewById(R.id.price);
        final EditText city=view.findViewById(R.id.city);
        final EditText description=view.findViewById(R.id.desctiption);
        Button insertBtn=view.findViewById(R.id.insert_btn);
        al.setView(view);

        final AlertDialog alertDialog=al.show();

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductModel productModel =new ProductModel();
                productModel.setName(name.getText().toString());
                productModel.setPrice(price.getText().toString());
                productModel.setCity(city.getText().toString());
                productModel.setDescription(description.getText().toString());
                Date date=new Date();
                productModel.setCreated_at(""+date.getTime());
                databaseHelper.AddProduct(productModel);
                alertDialog.dismiss();
                refreshData();
            }
        });
    }
}