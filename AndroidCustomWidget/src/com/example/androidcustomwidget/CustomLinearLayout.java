package com.example.androidcustomwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 
 * 实现自动换行
 * 
 */
public class CustomLinearLayout extends LinearLayout {

	private Context mContext;

	public CustomLinearLayout(Context context) {
		super(context);
		mContext = context;
	}

	public CustomLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	/** 控件总行数 **/
	private int num = 1;
	/** 按钮总行数 **/
	private int columns = 0;

	/** 设置子view的宽度外部设定，需要根据子view的宽度布局换行时会用 **/
	private int mCellWidth = 0;
	/** 设置子view的高度 **/
	private int mCellHeight = 0;
	/** //layout总宽度 **/
	int layoutTotalWidth = 0;

	public int getLayoutTotalWidth() {
		return layoutTotalWidth;
	}

	public void setLayoutTotalWidth(int layoutTotalWidth) {
		this.layoutTotalWidth = layoutTotalWidth;
	}

	public int getmCellWidth() {
		return mCellWidth;
	}

	public void setmCellWidth(int mCellWidth) {
		this.mCellWidth = mCellWidth;
	}

	public int getmCellHeight() {
		return mCellHeight;
	}

	public void setmCellHeight(int mCellHeight) {
		this.mCellHeight = mCellHeight;
	}

	/**
	 * 根据LinearLayout总宽度自动匹配布局
	 */
	public void autoLayout() {

		int cellWidth = 0;// 子空间宽
		int cellHeight = 0;// 子空间高
		int totalWidth = 0;// 每一行总宽度

		int x = 0;
		int y = 0;

		int count = getChildCount();// 5
		for (int j = 0; j < count; j++) {

			View childView = getChildAt(j);// [35,70]
			// 获取子控件Child的宽高[38,31]
			cellWidth = ((MyButton) childView).getRealWidth();
			cellHeight = ((MyButton) childView).getRealHeight();

			// +5 保持+5间距
			int left = x + 5;
			int top = y;
			// childView.layout(x, y, x + cellWidth, y + cellHeight);
			// 布局子控件0
			childView.layout(left, top, left + cellWidth, top + cellHeight);
			x = left + 5;

			if (totalWidth > this.getLayoutTotalWidth()) {
				// 换行
				x = 0;
				totalWidth = 0;
				y += cellHeight + 5;
				num++;
			} else {
				x += cellWidth;
				totalWidth += x;
			}
		}
	}

	/**
	 * 根据子view宽度布局
	 */
	public void byChildWidthLayout(boolean changed, int l, int t, int r, int b) {

		int cellWidth = mCellWidth;
		int cellHeight = mCellHeight;
		columns = (r - l) / cellWidth;
		if (columns < 0) {
			columns = 1;
		}
		int x = 0;
		int y = 0;
		int i = 0;
		int count = getChildCount();
		for (int j = 0; j < count; j++) {
			View childView = getChildAt(j);
			// 获取子控件Child的宽高
			int w = ((MyButton) childView).getmWidth();
			int h = ((MyButton) childView).getmHeight();
			// 计算子控件的顶点坐标
			int left = x + 5;
			int top = y;
			// 布局子控件0
			childView.layout(left, top, left + w, top + h);
			x = left + 5;

			if (i >= (columns - 1)) {
				i = 0;
				x = 0;
				y += cellHeight + 5;
				num++;
			} else {
				i++;
				x += cellWidth;

			}
		}
	}

	/**
	 * 自动换行模式下
	 * 
	 * @param widthMeasureSpec
	 * @param heightMeasureSpec
	 */
	public void onMeasureByChildContent(int widthMeasureSpec,
			int heightMeasureSpec) {

		int count = getChildCount();
		int layoutWidth = 0;
		int childHeight = 0; // 子view的高
		int a = this.getLayoutParams().width;
		int b = this.getLayoutParams().height;
		// Log.e("customlayout 随内容...", "getLayoutParams宽度"+a
		// +"**********************" + "getLayoutParams高度"+ b ) ;
		// 设置子空间Child的宽高
		for (int i = 0; i < count; i++) {
			View childView = getChildAt(i);

			layoutWidth += ((MyButton) childView).getRealWidth();
			childHeight = ((MyButton) childView).getHeight();
		}
		// 设置容器控件所占区域大小

		setMeasuredDimension(resolveSize(layoutWidth, widthMeasureSpec),
				resolveSize(num * childHeight, heightMeasureSpec));

		// layoutTotalWidth = this.getWidth() ;
		Log.e("总宽度", "************************" + layoutTotalWidth);

	}

	/**
	 * 
	 * 子view宽度来
	 * 
	 * @param widthMeasureSpec
	 * @param heightMeasureSpec
	 */
	public void onMeasureByChildWidth(int widthMeasureSpec,
			int heightMeasureSpec) {

		// 创建测量参数
		int cellWidthSpec = MeasureSpec.makeMeasureSpec(mCellWidth,
				MeasureSpec.EXACTLY);
		int cellHeightSpec = MeasureSpec.makeMeasureSpec(mCellHeight,
				MeasureSpec.EXACTLY);
		int count = getChildCount();
		// 设置子空间Child的宽高
		for (int i = 0; i < count; i++) {
			View childView = getChildAt(i);

			childView.measure(cellWidthSpec, cellHeightSpec);
		}
		// 设置容器控件所占区域大小
		setMeasuredDimension(
				resolveSize(mCellWidth * columns + 5, widthMeasureSpec),
				resolveSize(mCellHeight * num + 10, heightMeasureSpec));

	}

	/**
	 * 控制子控件的换行
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		byChildWidthLayout(changed, l, t, r, b);
		// autoLayout();
	}

	/**
	 * 计算控件及子控件所占区域
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// onMeasureByChildContent(widthMeasureSpec, heightMeasureSpec);

		onMeasureByChildWidth(widthMeasureSpec, heightMeasureSpec);
	}

}