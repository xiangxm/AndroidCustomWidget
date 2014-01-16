package com.example.androidcustomwidget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidcustomwidget.MyButton.ChangedCheckCallBackAttri;

/**
 * 商品属性自定义控件
 * 
 * 1.设定子view固定宽度 containerLayout.setmCellWidth(70);
 * containerLayout.setmCellHeight(35);// 根据子VIEW宽度来，设置这个
 * 
 * attriBtn.setmWidth(70);//根据内容来这里要设置 attriBtn.setmHeight(35);
 * 
 * 2.根据内容来：
 * 
 */
public class AttributeMyWidget extends LinearLayout {

	private Context context;
	private CustomLinearLayout containerLayout;
	private TextView attriName;
	private LinearLayout.LayoutParams params;

	private int childViewWidth;
	private int childViewHeight;

	public AttributeMyWidget(Context context, int childViewWidth,
			int childViewHeight) {
		super(context, null);
		this.context = context;
		LayoutInflater.from(context).inflate(
				R.layout.attribute_mywidget_layout, this, true);

		params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		containerLayout = (CustomLinearLayout) this
				.findViewById(R.id.attri_radiogrouplayout);
		this.childViewWidth = childViewWidth;
		this.childViewHeight = childViewHeight;
		containerLayout.setmCellWidth(childViewWidth);
		containerLayout.setmCellHeight(childViewHeight);
		containerLayout.setLayoutParams(params);
		// containerLayout.setLayoutTotalWidth(350);

		attriName = (TextView) this.findViewById(R.id.attriname);

		attriDetailList = new ArrayList<Map<String, Object>>();
		radioBtnList = new ArrayList<MyButton>();
	}

	/** jwattribtn容器宽度 **/
	private int customLayoutWidth = 0;

	/**
	 * 根据内容来构造器
	 * 
	 * @param context
	 * @param customLayoutWidth
	 *            customLayout总宽度
	 */
	public AttributeMyWidget(Context context, int customLayoutWidth) {
		super(context, null);
		this.context = context;
		LayoutInflater.from(context).inflate(
				R.layout.attribute_mywidget_layout, this, true);

		params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		containerLayout = (CustomLinearLayout) this
				.findViewById(R.id.attri_radiogrouplayout);
		containerLayout.setLayoutParams(params);

		this.customLayoutWidth = customLayoutWidth;
		containerLayout.setLayoutTotalWidth(customLayoutWidth);

		attriName = (TextView) this.findViewById(R.id.attriname);

		attriDetailList = new ArrayList<Map<String, Object>>();
		radioBtnList = new ArrayList<MyButton>();
	}

	/**
	 * 固定宽度
	 * 
	 * @param context
	 * @param attrs
	 * @param childViewWidth
	 *            containerLayout子view的宽
	 * @param childViewHeight
	 *            containerLayout子view的高
	 */
	public AttributeMyWidget(Context context, AttributeSet attrs,
			int childViewWidth, int childViewHeight) {
		super(context, attrs);
		this.context = context;
		LayoutInflater.from(context).inflate(
				R.layout.attribute_mywidget_layout, this, true);

		this.childViewWidth = childViewWidth;
		this.childViewHeight = childViewHeight;
		containerLayout = (CustomLinearLayout) this
				.findViewById(R.id.attri_radiogrouplayout);
		containerLayout.setmCellWidth(childViewWidth);
		containerLayout.setmCellHeight(childViewHeight);// 根据子VIEW宽度来，设置这个

		params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		containerLayout.setLayoutParams(params);
		attriName = (TextView) this.findViewById(R.id.attriname);
		// containerLayout.setLayoutTotalWidth(350);//根据子view内容来，设置这个
		attriDetailList = new ArrayList<Map<String, Object>>();
		radioBtnList = new ArrayList<MyButton>();
	}

	/**
	 * 设置属性标题
	 * 
	 * @param name
	 */
	public void setName(String name) {

		attriName.setText(name);
	}

	/**
	 * 具体属性List
	 */
	private List<Map<String, Object>> attriDetailList;

	/** 默认被选中属性id **/
	private String selectedId = "";

	public String getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}

	/**
	 * 获取当前被选中属性的id
	 * 
	 * @return
	 */
	public String getAttriDetail() {
		if (attriDetailList.isEmpty()
				|| selectedIndex >= attriDetailList.size()) {
			return "";
		}
		return (String) (attriDetailList.get(selectedIndex).get("code"));
	}

	/** 选择时，第几个属性被修改了，默认是1 **/
	private int flag = 1;

	/**
	 * @param art_id
	 *            商品id 根据id查询属性 flag :0 -->颜色 1-->尺寸
	 */
	public void loadAttriData() {

		String s[] = new String[] { "红色", "蓝色", "紫色", "黄色", "卡其色", "狼色" ,"天蓝色","钱绿色","周杰伦","马来西亚","喜欢"};
		Map<String, Object> map;
		for (int i = 0; i < s.length; i++) {

			map = new HashMap<String, Object>();

			map.put("name", s[i]);
			attriDetailList.add(map);
		}

		if (attriDetailList == null) {
			return;
		}
		initView(attriDetailList);
	}

	/*** 存放所有new出来的radioButton */
	private List<MyButton> radioBtnList = null;

	/**
	 * 控件中被选中的某一个 MyButton 的index。 默认第一个被选中
	 **/
	private int selectedIndex = 0;

	/**
	 * 初始化界面
	 * 
	 * @param s
	 */
	public void initView(final List<Map<String, Object>> attriDetailList) {

		MyButton attriBtn;

		for (int m = 0; m < attriDetailList.size(); m++) {

			attriBtn = new MyButton(context, (String) attriDetailList.get(m)
					.get("name"));
			attriBtn.setTag(m);
			// if (m == 0) {
			// attriBtn.setChecked(true);
			// }

			if (childViewWidth != 0 && childViewHeight != 0) {
				// 固定宽度
				attriBtn.setmWidth(childViewWidth);// 根据内容来这里要设置
				attriBtn.setmHeight(childViewHeight);
			}
			attriBtn.setCallback(new ChangedCheckCallBackAttri() {

				@Override
				public void ChangedCheck(MyButton attributeBtn) {

					if (attributeBtn.getTag() == null) {
						return;
					}
					int index = Integer.valueOf(attributeBtn.getTag()
							.toString());
					if (index != selectedIndex) {

						MyButton btn = radioBtnList.get(selectedIndex);
						btn.setChecked(false);

						selectedIndex = index;

					}

					Toast.makeText(context,
							"你点击了" + attriDetailList.get(index).get("name"),
							Toast.LENGTH_SHORT).show();
					// String attId = getAttriDetail();
					// if (attId.equals("")) {
					// return;
					// }
					//
					// // 重选属性时，回调，更新库存，价格数据显示
					// AttributeMyWidget.this.modifyedListener.modifyed(attId,
					// flag);
				}
			});

			if (!TextUtils.isEmpty(selectedId)
					&& ((String) (attriDetailList.get(m).get("code").toString()))
							.equals(selectedId)) {

				attriBtn.setChecked(true);
				selectedIndex = m;
			}
			radioBtnList.add(attriBtn);
			containerLayout.addView(attriBtn);
		}
	}

	/**
	 * 设置某个按钮不可顶级
	 */
	public void setEnabled() {

		MyButton btn = radioBtnList.get(selectedIndex);
		btn.setEnabled(false);

	}

	public void setMarginTop(int x) {

		params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(0, x, 0, 0);
		this.setLayoutParams(params);

	}

	private onAttriModifyedListener modifyedListener;

	public void setModifyedListener(onAttriModifyedListener modifyedListener) {
		this.modifyedListener = modifyedListener;
	}

	/**
	 * 选择属性时回调
	 */
	public interface onAttriModifyedListener {

		/**
		 * @param id
		 *            该属性id
		 * @param flag
		 *            颜色1，尺码2
		 */
		void modifyed(String id, int flag);

	}

}