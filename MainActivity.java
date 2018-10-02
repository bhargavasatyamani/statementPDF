package com.example.bhargav_2.statementpdf;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.bhargav_2.statementpdf.R.layout.activity_generate_statement;

public class MainActivity extends AppCompatActivity {

    File file;
    FileOutputStream fOutStream;
    PdfDocument document;
    PdfDocument.PageInfo pageInfo;
    PdfDocument.Page page;
    LinearLayout myLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Button generateStatementBtn=(Button)findViewById(R.id.generate_stmt_btn);
       generateStatementBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(MainActivity.this,"Button Clicked",Toast.LENGTH_SHORT).show();
               createPDF();
           }
       });

    }


    protected void createPDF(){
        myLayout=(LinearLayout)findViewById(R.id.inner_layout);
        ViewTreeObserver vto = myLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onGlobalLayout() {
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
//                    this.layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                } else {
//                    this.layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                }
                int width  = myLayout.getMeasuredWidth();
                int height = myLayout.getMeasuredHeight();


                file=new File(getCacheDir(),"statement.pdf");

                document=new PdfDocument();
                pageInfo= new PdfDocument.PageInfo.Builder(width,height,1).create();
                page=document.startPage(pageInfo);
                myLayout.draw(page.getCanvas());
                document.finishPage(page);

                try {
                    fOutStream=new FileOutputStream(file);
                    document.writeTo(fOutStream);
                    fOutStream.flush();
                    fOutStream.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                document.close();

                Toast.makeText(MainActivity.this,"Statement Generated",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
