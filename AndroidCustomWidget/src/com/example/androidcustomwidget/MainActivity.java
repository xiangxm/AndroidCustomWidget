package com.example.androidcustomwidget;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	AttributeMyWidget attributeMyWidget;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		LinearLayout layout = (LinearLayout) findViewById(R.id.layout);

		// 魅族Mx2手机分辨率比较大因此，这里我设置了[120,80]的大小，通常情况下的话[70,35就差不多了]
		attributeMyWidget = new AttributeMyWidget(this, 120, 80);
		// attributeMyWidget = new AttributeMyWidget(this,800) ;
		attributeMyWidget.loadAttriData();
		attributeMyWidget.setName("颜色属性");
		layout.addView(attributeMyWidget);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
