package com.harvic.BlogListItemFlyIn;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        List<Drawable> drawables = new ArrayList<Drawable>();
        drawables.add(getResources().getDrawable(R.drawable.pic1));
        drawables.add(getResources().getDrawable(R.drawable.pic2));
        drawables.add(getResources().getDrawable(R.drawable.pic3));
        drawables.add(getResources().getDrawable(R.drawable.pic4));
        drawables.add(getResources().getDrawable(R.drawable.pic5));
        drawables.add(getResources().getDrawable(R.drawable.pic6));
        drawables.add(getResources().getDrawable(R.drawable.pic7));
        drawables.add(getResources().getDrawable(R.drawable.pic8));
        drawables.add(getResources().getDrawable(R.drawable.pic9));


        ListView listView = (ListView)findViewById(R.id.list);
        ListAdapter adapter = new ListAdapter(this,listView,drawables,300);
        listView.setAdapter(adapter);
    }
}
