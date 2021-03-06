package cn.univs.app.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import com.android.ais.app.R;

public class MainPagePopupMenu extends PopupWindow implements OnClickListener {

	private Activity activity;
	private View popView;

	private View v_item1;
	private View v_item2;
	private View v_item3;

	private OnItemClickListenerForMianPagePopMenu onItemClickListener;

	public enum MENUITEM {
		ITEM1, ITEM2, ITEM3
	}

	public MainPagePopupMenu(Activity activity) {
		super(activity);
		this.activity = activity;
		LayoutInflater inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		popView = inflater.inflate(R.layout.main_page_popup_menu, null);
		this.setContentView(popView);
		
		this.setWidth(dip2px(activity, 120));
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setTouchable(true); 
		this.setOutsideTouchable(true); 
		ColorDrawable dw = new ColorDrawable(0x00000000);
		this.setBackgroundDrawable(dw);

		
		v_item1 = popView.findViewById(R.id.main_ly_item1);
		v_item2 = popView.findViewById(R.id.main_ly_item2);
		v_item3 = popView.findViewById(R.id.main_ly_item3);
	
		v_item1.setOnClickListener(this);
		v_item2.setOnClickListener(this);
		v_item3.setOnClickListener(this);

	}


	
	public void showLocation(int resourId) {
		showAsDropDown(activity.findViewById(resourId), dip2px(activity, 0),
				dip2px(activity, -8));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		MENUITEM menuitem = null;
		String str = "";
		if (v == v_item1) {
			menuitem = MENUITEM.ITEM1;
			str = "1";
		} else if (v == v_item2) {
			menuitem = MENUITEM.ITEM2;
			str = "2";
		} else if (v == v_item3) {
			menuitem = MENUITEM.ITEM3;
			str = "3";
		}
		if (onItemClickListener != null) {
			onItemClickListener.onClick(menuitem, str);
		}
		dismiss();
	}


	public int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	
	public interface OnItemClickListenerForMianPagePopMenu {
		public void onClick(MENUITEM item, String str);
	}

	public void setOnItemClickListener(OnItemClickListenerForMianPagePopMenu onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

}
