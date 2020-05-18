package com.example.fpgins.BottomNavigation.AgentDashboard.AgentFragments;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fpgins.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;

public class ViewActivity extends AppCompatActivity {

    private PDFView pdfView;
    private AVLoadingIndicatorView progressBar;
    private ImageView mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        pdfView = findViewById(R.id.pdf_viewer);
        progressBar = findViewById(R.id.progress_pdf);
        mBackButton = findViewById(R.id.img_backbutton);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        if (getIntent() != null){
            String viewType = getIntent().getStringExtra("ViewType");
            if (viewType != null || TextUtils.isEmpty(viewType)){
                if (viewType.equals("assets")){
                    //pwede din na from uri
                    pdfView.fromAsset("sample.pdf")
                            .password(null)//If have password
                            .defaultPage(0) //Open default page, you can remember this  value to open fro, last time
                            .enableSwipe(true)
                            .swipeHorizontal(false)
                            .enableDoubletap(true) //double tap to zoom

                            .onDraw(new OnDrawListener() {
                                @Override
                                public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
                                    //Code here if you want to do something
                                }
                            })

                            .onDrawAll(new OnDrawListener() {
                                @Override
                                public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
                                    //Code here if you want to do something
                                }
                            })

                            .onPageError(new OnPageErrorListener() {
                                @Override
                                public void onPageError(int page, Throwable t) {
                                    //Code here if you want to do something
                                }
                            })

                            .onTap(new OnTapListener() {
                                @Override
                                public boolean onTap(MotionEvent e) {
                                    return true;
                                }
                            })

                            .onRender(new OnRenderListener() {
                                @Override
                                public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                                    pdfView.fitToWidth();//Fixed screen size width
                                }
                            })

                            .enableAnnotationRendering(true)
                            .invalidPageColor(Color.RED)
                            .load();
                }else if (viewType.equals("internet"))
                {
                    progressBar.setVisibility(View.VISIBLE);
                    FileLoader.with(this)
                            .load("http://www.africau.edu/images/default/sample.pdf")
                            .fromDirectory("PDFFiles", FileLoader.DIR_EXTERNAL_PUBLIC)
                            .asFile(new FileRequestListener<File>() {
                                @Override
                                public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                                    progressBar.setVisibility(View.GONE);

                                    File pdfFile = response.getBody();

                                    pdfView.fromFile(pdfFile)
                                            .password(null)//If have password
                                            .defaultPage(0) //Open default page, you can remember this  value to open fro, last time
                                            .enableSwipe(true)
                                            .swipeHorizontal(false)
                                            .enableDoubletap(true) //double tap to zoom

                                            .onDraw(new OnDrawListener() {
                                                @Override
                                                public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
                                                    //Code here if you want to do something
                                                }
                                            })

                                            .onDrawAll(new OnDrawListener() {
                                                @Override
                                                public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
                                                    //Code here if you want to do something
                                                }
                                            })

                                            .onPageError(new OnPageErrorListener() {
                                                @Override
                                                public void onPageError(int page, Throwable t) {
                                                    //Code here if you want to do something
                                                }
                                            })

                                            .onTap(new OnTapListener() {
                                                @Override
                                                public boolean onTap(MotionEvent e) {
                                                    return true;
                                                }
                                            })

                                            .onRender(new OnRenderListener() {
                                                @Override
                                                public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                                                    pdfView.fitToWidth();//Fixed screen size width
                                                }
                                            })

                                            .enableAnnotationRendering(true)
                                            .invalidPageColor(Color.RED)
                                            .load();
                                }

                                @Override
                                public void onError(FileLoadRequest request, Throwable t) {
                                    Toast.makeText(ViewActivity.this, ""+ t.getMessage(), Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);

                                }
                            });

                }
            }
        }

    }
}
