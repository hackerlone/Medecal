package com.lhfeiyu.thirdparty.wx.menu;

/**
 * 复合类型的按钮
 * 
 * @author liufeng
 * @date 2013-08-08
 */
public class ComplexButton extends Button {
	private Button[] sub_button;

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}
}
