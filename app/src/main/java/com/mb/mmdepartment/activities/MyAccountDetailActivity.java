package com.mb.mmdepartment.activities;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.login.Data;
import com.mb.mmdepartment.biz.myaccunt.EditPersonalMessageBiz;
import com.mb.mmdepartment.tools.CustomToast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class MyAccountDetailActivity extends BaseActivity implements View.OnClickListener{
    private TextView btn_set_up,tv_born,tv_user_name;
    private int current_year;
    private int month;
    private int day;
    private Spinner sp_job,sp_address,sp_money;
    private ArrayAdapter jobArray;
    private ArrayAdapter addressArray;
    private ArrayAdapter moneyArray;
    private EditText et_summer,tv_nick;
    private RadioButton rb_man,rb_women;
    private String nick_name;

    @Override
    public int getLayout() {
        return R.layout.activity_my_account_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initView();
        setListener();
    }

    private void initData() {
        jobArray = ArrayAdapter.createFromResource(this, R.array.mydetil_job, android.R.layout.simple_spinner_item);
        /** 地区 */
        addressArray = ArrayAdapter.createFromResource(this, R.array.mydetil_address, android.R.layout.simple_spinner_item);
        /** 收入范围 */
        moneyArray = ArrayAdapter.createFromResource(this, R.array.mydetil_money, android.R.layout.simple_spinner_item);
    }

    private void setListener() {
        tv_born.setOnClickListener(this);
        btn_set_up.setOnClickListener(this);
    }

    private void initView() {

        rb_man = (RadioButton) findViewById(R.id.rb_man);
        rb_women = (RadioButton) findViewById(R.id.rb_women);
        if ("1".equals(TApplication.user.getGender())) {
            rb_man.setChecked(true);
        } else if ("2".equals(TApplication.user.getGender())) {
            rb_women.setChecked(true);
        }
        tv_nick = (EditText) findViewById(R.id.tv_nick);
        tv_nick.setText(TApplication.user.getNickname());
        nick_name = tv_nick.getText().toString();
        tv_user_name = (TextView) findViewById(R.id.tv_title);
        tv_user_name.setText(TApplication.user_name);

        btn_set_up = (TextView) findViewById(R.id.btn_set_up);
        tv_born = (TextView) findViewById(R.id.tv_born);

        if (!TextUtils.isEmpty(TApplication.user.getYear())) {
            tv_born.setText(TApplication.user.getYear() + "年" + TApplication.user.getMonth() + "月" + TApplication.user.getDay() + "日");
            current_year=Integer.valueOf(TApplication.user.getYear());
            month=Integer.valueOf(TApplication.user.getMonth());
            day=Integer.valueOf(TApplication.user.getDay());
        }

        sp_job = (Spinner) findViewById(R.id.sp_job);
        sp_address = (Spinner) findViewById(R.id.sp_address);
        sp_money = (Spinner) findViewById(R.id.sp_money);
        sp_job.setAdapter(jobArray);
        sp_address.setAdapter(addressArray);
        sp_money.setAdapter(moneyArray);
        // 获得本地内容的数组
        String[] arrJob = getResources().getStringArray(R.array.mydetil_job);
        String[] arrAddress = getResources().getStringArray(R.array.mydetil_address);
        String[] arrMoney = getResources().getStringArray(R.array.mydetil_money);
        if (!TextUtils.isEmpty(TApplication.user.getOccupation())) {
            for (int i = 0; i < arrJob.length; i++) {
                if (arrJob[i].equals(TApplication.user.getOccupation().trim())) {
                    sp_job.setSelection(i, true);
                    break;
                }
            }
        }
        if (!TextUtils.isEmpty(TApplication.user.getArea())) {
            // 遍历数组 address
            for (int i = 0; i < arrAddress.length; i++) {
                if (arrAddress[i].equals(TApplication.user.getArea().trim())) {
                    sp_address.setSelection(i, true);
                    break;
                }
            }
        }
        if (!TextUtils.isEmpty(TApplication.user.getIncome_range())) {
            // 遍历数组 money
            for (int i = 0; i < arrMoney.length; i++) {
                if (arrMoney[i].equals(TApplication.user.getIncome_range().trim())) {
                    sp_money.setSelection(i, true);
                    break;
                }
            }
        }
        et_summer = (EditText) findViewById(R.id.et_summer);

        et_summer.setText(TApplication.user.getContent());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TApplication.activities.remove(this);
    }

    @Override
    protected void setToolBar(ActionBar action, boolean isTrue) {
        action.setTitle("个人信息修改");
        action.setHomeButtonEnabled(isTrue);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_born:
                final Calendar c = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar c = Calendar.getInstance();
                        c.setTimeInMillis(System.currentTimeMillis());

                        Calendar c1 = Calendar.getInstance();
                        c1.set(year, monthOfYear, dayOfMonth);

                        if (c1.getTimeInMillis() > c.getTimeInMillis()) {

                        } else {
                            current_year = year;
                            month = monthOfYear + 1;
                            day = dayOfMonth;
                            tv_born.setText(year + " 年 " + month + " 月" + day + "日");
                        }
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
                dialog.show();
                break;
            case R.id.btn_set_up:
                if (current_year == 0 || month == 0 || day == 0) {
                    CustomToast.show(this,"提示","没有选择出生年月");
                    return;
                }
                if (TextUtils.isEmpty(et_summer.getText().toString())) {
                    CustomToast.show(this, "提示", "请输入个人简介");
                    return;
                }
                if (TextUtils.isEmpty(nick_name)) {
                    CustomToast.show(this, "提示", "请输入昵称");
                    return;
                }
                nick_name = tv_nick.getText().toString();
                Log.e("nick_name", "====="+nick_name);
                // 判断性别
                String gender = "0";
                if (rb_man.isChecked()) {
                    gender = "1";
                }
                if (rb_women.isChecked()) {
                    gender = "2";
                }
                // 职业类型
                String job = sp_job.getSelectedItem().toString();
                // 居住区域
                String address = sp_address.getSelectedItem().toString();
                // 收入范围
                String money = sp_money.getSelectedItem().toString();
                EditPersonalMessageBiz biz=new EditPersonalMessageBiz(this);

                biz.editPersionMessage(TApplication.user_id,nick_name,String.valueOf(current_year),String.valueOf(month),String.valueOf(day),et_summer.getText().toString(),job,address,money,gender);
                break;
        }
    }
}
