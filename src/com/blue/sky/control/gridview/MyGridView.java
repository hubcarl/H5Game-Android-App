package com.blue.sky.control.gridview;

import android.widget.GridView;

/**
 * Scrollbar组件中嵌入另一个拥有Scrollbar的组件，会混淆滑动事件，导致只显示一到两行数据。<br><br>
 * 那么就换一种思路，首先让子控件的内容全部显示出来，禁用了它的滚动。<br>
 * 如果超过了父控件的范围则显示父控件的scrollbar滚动显示内容，思路是这样，一下是代码。<br>
 *
 */
public class MyGridView extends GridView {

	public MyGridView(android.content.Context context, android.util.AttributeSet attrs) {  
        super(context, attrs);  
    } 
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
                MeasureSpec.AT_MOST); 
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
