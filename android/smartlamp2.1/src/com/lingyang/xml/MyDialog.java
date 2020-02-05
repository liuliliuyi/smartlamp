package com.lingyang.xml;

import java.util.ArrayList;

import com.example.lanya_lingyang_5.MainActivity;
import com.parking.smarthome.R;

 
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyDialog extends Dialog {

	private ArrayList<String> js_show = new ArrayList<String>();
	private boolean flag = false;

	public MyDialog(Context context, ArrayList<String> js_show) {
		super(context);
		// TODO Auto-generated constructor stub
		this.js_show = js_show;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		switch (zhuanhuan(js_show.get(22)) % 16) {
		case 0:
			setContentView(R.layout.js_show_1);
			break;
		case 1:
			setContentView(R.layout.js_show_2);
			break;
		case 2:
			setContentView(R.layout.js_show_3);
			break;
		case 3:
			setContentView(R.layout.js_show_4);
			break;

		default:

			break;
		}

		// ���ñ���
		setTitle("���ݽ��ճɹ�");
		int a = 0;
		int b = 0;

		if (zhuanhuan(js_show.get(2)) > 16 && zhuanhuan(js_show.get(2)) < 24) {
			a = zhuanhuan(js_show.get(2)) - 16;
			((TextView) findViewById(R.id.fanhui_51)).setText(" 12 V");
		} else {
			if (zhuanhuan(js_show.get(2)) > 32) {
				a = zhuanhuan(js_show.get(2)) - 32;
				((TextView) findViewById(R.id.fanhui_51)).setText(" 24 V");
			} else {
				((TextView) findViewById(R.id.fanhui_51)).setText(" 0 V");
			}
		}

		switch (a) {
		case 1:
			((TextView) findViewById(R.id.fanhui_1)).setText(" " + "�������");
			((TextView) findViewById(R.id.fanhui_2)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.fanhui_3)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.fanhui_6)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.fanhui_7)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.fanhui_8)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.textView6)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.textView7)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.textView8)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.textView2)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.textView3)).setVisibility(View.GONE);
			break;
		case 2:
			((TextView) findViewById(R.id.fanhui_1)).setText(" " + "��Ԫ﮵�");
			((TextView) findViewById(R.id.fanhui_2)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.fanhui_3)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.fanhui_6)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.fanhui_7)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.fanhui_8)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.textView6)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.textView7)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.textView8)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.textView2)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.textView3)).setVisibility(View.GONE);
			break;
		case 3:
			((TextView) findViewById(R.id.fanhui_1)).setText(" " + "������");
			((TextView) findViewById(R.id.fanhui_6)).setText(" "
					+ String.valueOf(zhuanhuan(js_show.get(7)) / 10.0) + " V");
			((TextView) findViewById(R.id.fanhui_7)).setText(" "
					+ String.valueOf(zhuanhuan(js_show.get(8)) / 10.0) + " V");
			((TextView) findViewById(R.id.fanhui_8)).setText(" "
					+ String.valueOf(zhuanhuan(js_show.get(9)) / 10.0) + " V");
			break;
		case 4:
			((TextView) findViewById(R.id.fanhui_1)).setText(" " + "�Զ����������");
			((TextView) findViewById(R.id.fanhui_2)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.fanhui_3)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.fanhui_6)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.fanhui_7)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.fanhui_8)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.textView6)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.textView7)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.textView8)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.textView2)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.textView3)).setVisibility(View.GONE);
			break;
		case 5:
			((TextView) findViewById(R.id.fanhui_1)).setText(" " + "�Զ�����Ԫ﮵�");
			((TextView) findViewById(R.id.fanhui_2)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.fanhui_3)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.fanhui_6)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.fanhui_7)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.fanhui_8)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.textView6)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.textView7)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.textView8)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.textView2)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.textView3)).setVisibility(View.GONE);
			break;
		case 6:
			((TextView) findViewById(R.id.fanhui_1)).setText(" " + "�Զ��彺����");
			((TextView) findViewById(R.id.fanhui_6)).setText(" "
					+ String.valueOf(zhuanhuan(js_show.get(7)) / 10.0) + " V");
			((TextView) findViewById(R.id.fanhui_7)).setText(" "
					+ String.valueOf(zhuanhuan(js_show.get(8)) / 10.0) + " V");
			((TextView) findViewById(R.id.fanhui_8)).setText(" "
					+ String.valueOf(zhuanhuan(js_show.get(9)) / 10.0) + " V");
			break;
		default:
			break;
		}
		switch (Integer.parseInt(js_show.get(47).toString().trim(), 16)) {
		case 0:
			((TextView) findViewById(R.id.fanhui_60)).setText(" " + "SY-D-40");
			break;
		case 1:
			((TextView) findViewById(R.id.fanhui_60)).setText(" " + "SY-C-30");
			break;
		case 2:
			((TextView) findViewById(R.id.fanhui_60)).setText(" " + "SY-D-60");
			break;
		case 3:
			((TextView) findViewById(R.id.fanhui_60)).setText(" " + "SY-D-80");
			break;
		case 4:
			((TextView) findViewById(R.id.fanhui_60)).setText(" " + "SY-D-100");
			break;
		case 5:
			((TextView) findViewById(R.id.fanhui_60)).setText(" " + "SY-D-150");
			break;
		case 6:
			((TextView) findViewById(R.id.fanhui_60)).setText(" " + "SY-D-200");
			break;

		case 10:
			((TextView) findViewById(R.id.fanhui_60)).setText(" " + "SY-PD-20");
			break;

		case 20:
			((TextView) findViewById(R.id.fanhui_60))
					.setText(" " + "SY-KC-10A");
			break;

		default:
			((TextView) findViewById(R.id.fanhui_60)).setText(" " + "��");
			break;
		}

		((TextView) findViewById(R.id.fanhui_61)).setText(" "
				+ String.valueOf(Integer.parseInt(js_show.get(48).toString()
						.trim(), 16)));

		((TextView) findViewById(R.id.fanhui_2)).setText(" "
				+ String.valueOf(zhuanhuan(js_show.get(3)) / 10.0) + " V");
		((TextView) findViewById(R.id.fanhui_3)).setText(" "
				+ String.valueOf(zhuanhuan(js_show.get(4)) / 10.0) + " V");
		((TextView) findViewById(R.id.fanhui_4)).setText(" "
				+ String.valueOf(zhuanhuan(js_show.get(5)) / 10.0) + " V");
		((TextView) findViewById(R.id.fanhui_5)).setText(" "
				+ String.valueOf(zhuanhuan(js_show.get(6)) / 10.0) + " V");
		((TextView) findViewById(R.id.fanhui_9)).setText(" "
				+ String.valueOf(zhuanhuan(js_show.get(10)) / 10.0) + " V");
		((TextView) findViewById(R.id.fanhui_10)).setText(" "
				+ String.valueOf(zhuanhuan(js_show.get(11)) / 10.0) + " V");
		if (zhuanhuan(js_show.get(12)) > 128) {
			((TextView) findViewById(R.id.fanhui_11)).setText(" " + "-"
					+ String.valueOf(zhuanhuan(js_show.get(12)) - 128) + " ��");
		} else {
			((TextView) findViewById(R.id.fanhui_11)).setText(" "
					+ String.valueOf(zhuanhuan(js_show.get(12))) + " ��");
		}
		((TextView) findViewById(R.id.fanhui_12)).setText(" "
				+ String.valueOf(zhuanhuan(js_show.get(13))) + " ��");
		((TextView) findViewById(R.id.fanhui_13)).setText(" "
				+ String.valueOf(zhuanhuan(js_show.get(14)) / 10.0) + " V");

		((TextView) findViewById(R.id.fanhui_14)).setText(" "
				+ String.valueOf(zhuanhuan(js_show.get(15)) * 20) + " mA");
		if (Integer.parseInt(js_show.get(15), 16) != 0) {
			((TextView) findViewById(R.id.textView14))
					.setVisibility(View.VISIBLE);
			((TextView) findViewById(R.id.fanhui_14))
					.setVisibility(View.VISIBLE);
		} else {
			((TextView) findViewById(R.id.textView14)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.fanhui_14)).setVisibility(View.GONE);
		}

		if (zhuanhuan(js_show.get(22)) >= 0 && zhuanhuan(js_show.get(22)) < 15) {
			b = zhuanhuan(js_show.get(22));
			((TextView) findViewById(R.id.fanhui_115)).setText(" " + "��");
		} else {
			if (zhuanhuan(js_show.get(22)) >= 16
					&& zhuanhuan(js_show.get(22)) < 31) {
				b = zhuanhuan(js_show.get(22)) - 16;
				((TextView) findViewById(R.id.fanhui_115)).setText(" " + "С");
			} else {
				if (zhuanhuan(js_show.get(22)) >= 32
						&& zhuanhuan(js_show.get(22)) < 47) {
					b = zhuanhuan(js_show.get(22)) - 32;
					((TextView) findViewById(R.id.fanhui_115)).setText(" "
							+ "��");
				} else {
					if (zhuanhuan(js_show.get(22)) >= 48
							&& zhuanhuan(js_show.get(22)) < 63) {
						b = zhuanhuan(js_show.get(22)) - 48;
						((TextView) findViewById(R.id.fanhui_115)).setText(" "
								+ "��");
					}
				}
			}
		}
		switch (b) {
		case 0:
			((TextView) findViewById(R.id.fanhui_15)).setText(" " + "һ��ģʽ");
			break;
		case 1:
			((TextView) findViewById(R.id.fanhui_15)).setText(" " + "ʱ��ģʽ");
			break;
		case 2:
			((TextView) findViewById(R.id.fanhui_15)).setText(" " + "���ģʽ");
			break;
		case 3:
			((TextView) findViewById(R.id.fanhui_15)).setText(" " + "�˹�ģʽ");
			break;

		default:
			break;
		}
		// ((TextView)
		// findViewById(R.id.fanhui_15)).setText(String.valueOf(zhuanhuan(js_show.get(22))));
		((TextView) findViewById(R.id.fanhui_16)).setText(" "
				+ String.valueOf(zhuanhuan(js_show.get(23)) * 10) + "��");
		if (zhuanhuan(js_show.get(24)) < 128) {
			((TextView) findViewById(R.id.fanhui_17)).setText(" "
					+ String.valueOf(zhuanhuan(js_show.get(24)) * 2) + " W");
		} else {
			((TextView) findViewById(R.id.fanhui_17)).setText(" "
					+ String.valueOf((zhuanhuan(js_show.get(24)) - 128) * 2)
					+ " W");
		}

		((TextView) findViewById(R.id.fanhui_18)).setText(" "
				+ String.valueOf(zhuanhuan(js_show.get(25)) * 10) + "��");
		if (zhuanhuan(js_show.get(26)) < 128) {
			((TextView) findViewById(R.id.fanhui_19)).setText(" "
					+ String.valueOf(zhuanhuan(js_show.get(26)) * 2) + " W");
		} else {
			((TextView) findViewById(R.id.fanhui_19)).setText(" "
					+ String.valueOf((zhuanhuan(js_show.get(26)) - 128) * 2)
					+ " W");
		}

		((TextView) findViewById(R.id.fanhui_20)).setText(" "
				+ String.valueOf(zhuanhuan(js_show.get(27)) * 10) + "��");
		if (zhuanhuan(js_show.get(28)) < 128) {
			((TextView) findViewById(R.id.fanhui_21)).setText(" "
					+ String.valueOf(zhuanhuan(js_show.get(28)) * 2) + " W");
		} else {
			((TextView) findViewById(R.id.fanhui_21)).setText(" "
					+ String.valueOf((zhuanhuan(js_show.get(28)) - 128) * 2)
					+ " W");
		}

		((TextView) findViewById(R.id.fanhui_22)).setText(" "
				+ String.valueOf(zhuanhuan(js_show.get(29)) * 10) + "��");
		if (zhuanhuan(js_show.get(30)) < 128) {
			((TextView) findViewById(R.id.fanhui_23)).setText(" "
					+ String.valueOf(zhuanhuan(js_show.get(30)) * 2) + " W");
		} else {
			((TextView) findViewById(R.id.fanhui_23)).setText(" "
					+ String.valueOf((zhuanhuan(js_show.get(30)) - 128) * 2)
					+ " W");
		}

		((TextView) findViewById(R.id.fanhui_24)).setText(" "
				+ String.valueOf(zhuanhuan(js_show.get(31)) * 10) + "��");
		if (zhuanhuan(js_show.get(32)) < 128) {
			((TextView) findViewById(R.id.fanhui_25)).setText(" "
					+ String.valueOf(zhuanhuan(js_show.get(32)) * 2) + " W");
		} else {
			((TextView) findViewById(R.id.fanhui_25)).setText(" "
					+ String.valueOf((zhuanhuan(js_show.get(32)) - 128) * 2)
					+ " W");
		}

		((TextView) findViewById(R.id.fanhui_26)).setText(" "
				+ String.valueOf(zhuanhuan(js_show.get(33)) * 10) + "��");
		if (zhuanhuan(js_show.get(34)) < 128) {
			((TextView) findViewById(R.id.fanhui_27)).setText(" "
					+ String.valueOf(zhuanhuan(js_show.get(34)) * 2) + " W");
		} else {
			((TextView) findViewById(R.id.fanhui_27)).setText(" "
					+ String.valueOf((zhuanhuan(js_show.get(34)) - 128) * 2)
					+ " W");
		}

		((TextView) findViewById(R.id.fanhui_28)).setText(" "
				+ String.valueOf(zhuanhuan(js_show.get(35)) * 10) + "��");
		if (zhuanhuan(js_show.get(36)) < 128) {
			((TextView) findViewById(R.id.fanhui_29)).setText(" "
					+ String.valueOf(zhuanhuan(js_show.get(36)) * 2) + " W");
		} else {
			((TextView) findViewById(R.id.fanhui_29)).setText(" "
					+ String.valueOf((zhuanhuan(js_show.get(36)) - 128) * 2)
					+ " W");
		}

		((TextView) findViewById(R.id.fanhui_30)).setText(" "
				+ String.valueOf(zhuanhuan(js_show.get(37)) * 10) + "��");
		if (zhuanhuan(js_show.get(38)) < 128) {
			((TextView) findViewById(R.id.fanhui_31)).setText(" "
					+ String.valueOf(zhuanhuan(js_show.get(38)) * 2) + " W");
		} else {
			((TextView) findViewById(R.id.fanhui_31)).setText(" "
					+ String.valueOf((zhuanhuan(js_show.get(38)) - 128) * 2)
					+ " W");
		}

		if (zhuanhuan(js_show.get(42)) == 0) {
			((TextView) findViewById(R.id.fanhui_32)).setText(" " + "20 ��");
		} else {
			((TextView) findViewById(R.id.fanhui_32)).setText(" "
					+ String.valueOf(zhuanhuan(js_show.get(42))) + " ��");
		}

		((TextView) findViewById(R.id.fanhui_33)).setText(" "
				+ String.valueOf(zhuanhuan(js_show.get(43)) / 10.0) + " V");
		((TextView) findViewById(R.id.fanhui_34)).setText(" "
				+ String.valueOf(zhuanhuan(js_show.get(44))) + " %");
		if (zhuanhuan(js_show.get(45)) <= 128) {
			((TextView) findViewById(R.id.fanhui_35)).setText(" "
					+ String.valueOf(zhuanhuan(js_show.get(45)) * 2) + " W");
		} else {
			((TextView) findViewById(R.id.fanhui_35)).setText(" "
					+ String.valueOf((zhuanhuan(js_show.get(45)) - 128) * 2)
					+ " W");
		}

		((TextView) findViewById(R.id.fanhui_36)).setText(" "
				+ String.valueOf(zhuanhuan(js_show.get(46)) * 2) + " W");

		((TextView) findViewById(R.id.fanhui_60))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_61))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_51))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_1))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_2))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_3))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_4))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_5))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_6))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_7))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_8))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_9))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_10))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_11))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_12))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_13))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_14))
				.setTextSize(MainActivity.textSize);

		((TextView) findViewById(R.id.fanhui_15))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_16))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_17))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_18))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_19))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_20))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_21))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_22))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_23))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_24))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_25))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_26))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_27))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_28))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_29))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_30))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_31))
				.setTextSize(MainActivity.textSize);

		((TextView) findViewById(R.id.fanhui_32))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_33))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_34))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_35))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_36))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.fanhui_115))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.textView1))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.textView1)).setText(AlignedTextUtils
				.formatStr6(((TextView) findViewById(R.id.textView1)).getText()
						.toString()));

		((TextView) findViewById(R.id.textView2))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.textView2)).setText(AlignedTextUtils
				.formatStr6(((TextView) findViewById(R.id.textView2)).getText()
						.toString()));

		((TextView) findViewById(R.id.textView3))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.textView3)).setText(AlignedTextUtils
				.formatStr6(((TextView) findViewById(R.id.textView3)).getText()
						.toString()));

		((TextView) findViewById(R.id.textView4))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.textView4)).setText(AlignedTextUtils
				.formatStr6(((TextView) findViewById(R.id.textView4)).getText()
						.toString()));

		((TextView) findViewById(R.id.textView5))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.textView5)).setText(AlignedTextUtils
				.formatStr6(((TextView) findViewById(R.id.textView5)).getText()
						.toString()));

		((TextView) findViewById(R.id.textView6))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.textView6)).setText(AlignedTextUtils
				.formatStr6(((TextView) findViewById(R.id.textView6)).getText()
						.toString()));

		((TextView) findViewById(R.id.textView7))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.textView7)).setText(AlignedTextUtils
				.formatStr6(((TextView) findViewById(R.id.textView7)).getText()
						.toString()));

		((TextView) findViewById(R.id.textView8))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.textView8)).setText(AlignedTextUtils
				.formatStr6(((TextView) findViewById(R.id.textView8)).getText()
						.toString()));

		((TextView) findViewById(R.id.textView9))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.textView9)).setText(AlignedTextUtils
				.formatStr6(((TextView) findViewById(R.id.textView9)).getText()
						.toString()));

		((TextView) findViewById(R.id.textView10))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.textView10)).setText(AlignedTextUtils
				.formatStr6(((TextView) findViewById(R.id.textView10))
						.getText().toString()));

		((TextView) findViewById(R.id.textView11))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.textView11)).setText(AlignedTextUtils
				.formatStr6(((TextView) findViewById(R.id.textView11))
						.getText().toString()));

		((TextView) findViewById(R.id.textView12))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.textView12)).setText(AlignedTextUtils
				.formatStr6(((TextView) findViewById(R.id.textView12))
						.getText().toString()));

		((TextView) findViewById(R.id.textView13))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.textView13)).setText(AlignedTextUtils
				.formatStr6(((TextView) findViewById(R.id.textView13))
						.getText().toString()));

		((TextView) findViewById(R.id.textView14))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.textView14)).setText(AlignedTextUtils
				.formatStr6(((TextView) findViewById(R.id.textView14))
						.getText().toString()));

		((TextView) findViewById(R.id.textView15))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.textView15)).setText(AlignedTextUtils
				.formatStr6(((TextView) findViewById(R.id.textView15))
						.getText().toString()));

		((TextView) findViewById(R.id.textView16))
				.setTextSize(MainActivity.textSize);

		((TextView) findViewById(R.id.textView17))
				.setTextSize(MainActivity.textSize);

		((TextView) findViewById(R.id.textView18))
				.setTextSize(MainActivity.textSize);

		((TextView) findViewById(R.id.textView19))
				.setTextSize(MainActivity.textSize);

		((TextView) findViewById(R.id.textView20))
				.setTextSize(MainActivity.textSize);

		((TextView) findViewById(R.id.textView21))
				.setTextSize(MainActivity.textSize);

		((TextView) findViewById(R.id.textView22))
				.setTextSize(MainActivity.textSize);

		((TextView) findViewById(R.id.textView23))
				.setTextSize(MainActivity.textSize);

		((TextView) findViewById(R.id.textView24))
				.setTextSize(MainActivity.textSize);

		((TextView) findViewById(R.id.textView25))
				.setTextSize(MainActivity.textSize);

		((TextView) findViewById(R.id.textView26))
				.setTextSize(MainActivity.textSize);

		((TextView) findViewById(R.id.textView27))
				.setTextSize(MainActivity.textSize);

		((TextView) findViewById(R.id.textView28))
				.setTextSize(MainActivity.textSize);

		((TextView) findViewById(R.id.textView29))
				.setTextSize(MainActivity.textSize);

		((TextView) findViewById(R.id.textView30))
				.setTextSize(MainActivity.textSize);

		((TextView) findViewById(R.id.textView31))
				.setTextSize(MainActivity.textSize);

		((TextView) findViewById(R.id.textView32))
				.setTextSize(MainActivity.textSize);

		((TextView) findViewById(R.id.textView33))
				.setTextSize(MainActivity.textSize);

		((TextView) findViewById(R.id.textView34))
				.setTextSize(MainActivity.textSize);

		((TextView) findViewById(R.id.textView35))
				.setTextSize(MainActivity.textSize);

		((TextView) findViewById(R.id.textView36))
				.setTextSize(MainActivity.textSize);

		((TextView) findViewById(R.id.textView38))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.textView115))
				.setTextSize(MainActivity.textSize);

		((TextView) findViewById(R.id.textView40))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.textView40)).setText(AlignedTextUtils
				.formatStr6(((TextView) findViewById(R.id.textView40))
						.getText().toString()));
		((TextView) findViewById(R.id.textView41))
				.setTextSize(MainActivity.textSize);
		((TextView) findViewById(R.id.textView41)).setText(AlignedTextUtils
				.formatStr6(((TextView) findViewById(R.id.textView41))
						.getText().toString()));

		// setTitle("���ݽ��ճɹ�s"+String.valueOf(Integer.parseInt(MainActivity.dc,
		// 16)%16)+"     "+(String.valueOf(Integer.parseInt(MainActivity.wm,
		// 16)%16))+"   "+String.valueOf(Integer.parseInt(MainActivity.wm,
		// 16)/16)+"   "+String.valueOf(Integer.parseInt(MainActivity.jg,
		// 16)%16));
		((TextView) findViewById(R.id.cpy_ok))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub

						ArrayList<String> n = new ArrayList<String>();
						n.add(String.valueOf(Integer.parseInt(MainActivity.dc,
								16) % 16));
						XmlSaveUtil.GetInstance().writeXML(2, n, "wPN_1.xml");
						n.clear();
						n.add(String.valueOf(Integer.parseInt(MainActivity.wm,
								16) % 16 + 1));
						ms((Integer.parseInt(MainActivity.wm, 16) % 16) + 1);
						XmlSaveUtil.GetInstance().writeXML(2, n, "wPN_2.xml");
						n.clear();
						n.add(String.valueOf(Integer.parseInt(MainActivity.wm,
								16) % 16));
						XmlSaveUtil.GetInstance()
								.writeXML(2, n, "mPattern.xml");
						n.clear();
						n.add(String.valueOf(Integer.parseInt(MainActivity.wm,
								16) / 16));
						XmlSaveUtil.GetInstance().writeXML(6, n, "mRA.xml");
						n.clear();
						n.add(String.valueOf(Integer.parseInt(MainActivity.jg,
								16) % 16));
						XmlSaveUtil.GetInstance().writeXML(2, n, "wPN_3.xml");
						n.clear();
						cancel();
					}
				});
		((TextView) findViewById(R.id.cpy_quit))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						ArrayList<String> sm = new ArrayList<String>();
						for (int i = 0; i < 20; i++) {
							sm.add("0");
						}
						XmlSaveUtil.GetInstance().writeXML(10, sm,
								"break_MS_1.xml");
						XmlSaveUtil.GetInstance().writeXML(10, sm,
								"break_MS_2.xml");
						XmlSaveUtil.GetInstance().writeXML(10, sm,
								"break_MS_3.xml");

						cancel();
					}
				});
	}

	private int zhuanhuan(String str) {

		return Integer.valueOf(str, 16);

	}

	private void ms(int x) {
		ArrayList<String> sr = new ArrayList<String>();
		ArrayList<String> s = new ArrayList<String>();
		ArrayList<String> z = new ArrayList<String>();
		ArrayList<String> n = new ArrayList<String>();
		Read_XML rx = new Read_XML();
		s.clear();
		z.clear();
		sr.clear();
		n.clear();
		switch (x) {
		case 1:
			sr.clear();

			sr = rx.read_xml_all(2);
			for (int i = 3; i < sr.size(); i++) {
				if (Integer.parseInt(sr.get(i).toString().trim(), 16) != 0) {

					break;
				}
			}
			if (true) {

				if (sr.size() != 0) {
					for (int i = 3; i < 19; i++) {
						if (i % 2 != 0) {
							s.add(String.valueOf(zhuanhuan(sr.get(i))));
						}
					}
					XmlSaveUtil.GetInstance().writeXML(3, s,
							"mguangKong_shi_1.xml");
					s.clear();
					for (int i = 3; i < 19; i++) {
						if (i % 2 == 0) {
							if (zhuanhuan(sr.get(i)) - 128 >= 0) {
								s.add(String.valueOf(zhuanhuan(sr.get(i)) - 128));
								z.add("1");
							} else {
								s.add(String.valueOf(zhuanhuan(sr.get(i))));
								z.add("0");
							}
						}
					}
					XmlSaveUtil.GetInstance().writeXML(3, s,
							"mguangKong_liang_1.xml");
					XmlSaveUtil.GetInstance().writeXML(3, z,
							"mguangKong_on_1.xml");
					s.clear();
					sr.clear();
					sr = rx.read_xml_all(3);
					for (int i = 0; i < 8; i++) {
						s.add(String.valueOf(zhuanhuan(sr.get(6))));
					}
					XmlSaveUtil.GetInstance().writeXML(3, s,
							"mguangKong_xiu_1.xml");
				}
			}
			break;
		case 2:
			sr.clear();

			sr = rx.read_xml_all(2);
			for (int i = 3; i < sr.size(); i++) {
				if (Integer.parseInt(sr.get(i).toString().trim(), 16) != 0) {
					flag = true;
					break;
				}
			}
			if (true) {
				if (sr.size() != 0) {
					for (int i = 3; i < 19; i++) {
						if (i % 2 != 0) {
							s.add(String.valueOf(zhuanhuan(sr.get(i))));
						}
					}
					XmlSaveUtil.GetInstance().writeXML(3, s,
							"mguangKong_shi_2.xml");
					s.clear();
					for (int i = 3; i < 19; i++) {
						if (i % 2 == 0) {
							if (zhuanhuan(sr.get(i)) - 128 >= 0) {
								s.add(String.valueOf(zhuanhuan(sr.get(i)) - 128));
								z.add("1");
							} else {
								s.add(String.valueOf(zhuanhuan(sr.get(i))));
								z.add("0");
							}
						}
					}
					XmlSaveUtil.GetInstance().writeXML(3, s,
							"mguangKong_liang_2.xml");
					XmlSaveUtil.GetInstance().writeXML(3, z,
							"mguangKong_on_2.xml");
					s.clear();
					sr.clear();
					sr = rx.read_xml_all(3);
					for (int i = 0; i < 8; i++) {
						s.add(String.valueOf(zhuanhuan(sr.get(6))));
					}
					XmlSaveUtil.GetInstance().writeXML(3, s,
							"mguangKong_xiu_2.xml");
					s.clear();
					s.add(String.valueOf(Integer.valueOf(sr.get(6), 16)));
					XmlSaveUtil.GetInstance().writeXML(2, s, "mPn_1.xml");
				}
				
			}
			break;
		case 3:
			sr = rx.read_xml_all(3);
			if (sr.size() != 0) {
//				setTitle("FFFFF"+sr.get(6)+"   "+sr.get(4));
				if (zhuanhuan(sr.get(4)) - 128 >= 0) {
					s.add(String.valueOf(zhuanhuan(sr.get(4)) - 128));
					s.add("1");
					s.add(String.valueOf(Integer.valueOf(sr.get(6), 16)));

				} else {
					s.add(String.valueOf(zhuanhuan(sr.get(4))));
					s.add("0");
					s.add(String.valueOf(Integer.valueOf(sr.get(6), 16)));
				}
				XmlSaveUtil.GetInstance().writeXML(4, s, "mgkong_3.xml");
				s.clear();
				s.add(String.valueOf(Integer.valueOf(sr.get(6), 16)));
				XmlSaveUtil.GetInstance().writeXML(2, s, "mPn_2.xml");
			}
			
			break;
		case 4:
			
			sr = rx.read_xml_all(3);
//			setTitle("FFFFF"+sr.get(6)+"   "+sr.get(4));
			if (sr.size() != 0) {

				if (zhuanhuan(sr.get(5)) - 128 >= 0) {
					s.add(String.valueOf(zhuanhuan(sr.get(5)) - 128));
					s.add("1");
					s.add(String.valueOf(Integer.valueOf(sr.get(6), 16)));

				} else {
					s.add(String.valueOf(zhuanhuan(sr.get(5)) ));
					s.add("0");
					s.add(String.valueOf(Integer.valueOf(sr.get(6), 16)));
				}
				XmlSaveUtil.GetInstance().writeXML(4, s, "mrenGong_4.xml");
				
			}
			s.clear();
			s.add(String.valueOf(Integer.valueOf(sr.get(6), 16)));
			XmlSaveUtil.GetInstance().writeXML(2, s, "mPn_3.xml");
			break;
		default:
			break;
		}
	}
}
