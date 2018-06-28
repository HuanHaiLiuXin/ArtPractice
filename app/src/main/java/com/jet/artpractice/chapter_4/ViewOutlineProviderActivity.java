package com.jet.artpractice.chapter_4;

import android.graphics.Outline;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.jet.artpractice.R;

/**
 * 经过试验
 */
public class ViewOutlineProviderActivity extends AppCompatActivity {
    private View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_outline_provider);
        v = findViewById(R.id.v);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    class Out0 extends ViewOutlineProvider{
        @Override
        public void getOutline(View view, Outline outline) {
            outline.setRoundRect(0,0,view.getWidth(),view.getHeight(),40);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    class Out1 extends ViewOutlineProvider{
        @Override
        public void getOutline(View view, Outline outline) {
            outline.setRoundRect(100,100,view.getWidth()-100,view.getHeight()-100,40);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    class Out2 extends ViewOutlineProvider{
        @Override
        public void getOutline(View view, Outline outline) {
            /*
            Path path1 = new Path();
            path1.addCircle(150, 150, 100, Path.Direction.CW);
            Path path2 = new Path();
            path2.addCircle(200, 200, 100, Path.Direction.CW);
            path1.op(path2, Path.Op.DIFFERENCE);
            Log.e("Jet","path1.isConvex():"+path1.isConvex());
            outline.setConvexPath(path1);
            */
            Path path = new Path();
            path.setFillType(Path.FillType.WINDING);
            path.moveTo(0,0);
            path.rLineTo(50,20);
            path.rLineTo(50,30);
            path.rLineTo(50,40);
            path.rLineTo(50,50);
            path.close();
            Log.e("Jet","path.isConvex():"+path.isConvex());
            outline.setConvexPath(path);
        }
    }
    int outLineIndex = 0;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void toggleOutline(View view) {
        v.setClipToOutline(false);
        if(outLineIndex == 0){
            v.setOutlineProvider(new Out0());
        }
        else if(outLineIndex == 1){
            v.setOutlineProvider(new Out1());
        }
        else if(outLineIndex == 2){
            v.setOutlineProvider(new Out2());
        }
        v.setClipToOutline(true);
        outLineIndex = (++outLineIndex) % 3;
    }
}
