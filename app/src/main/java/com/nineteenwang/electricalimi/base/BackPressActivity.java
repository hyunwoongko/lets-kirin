package com.nineteenwang.electricalimi.base;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeInfoDialog;
import com.nineteenwang.electricalimi.R;

/**
 * @Author : Hyunwoong
 * @When : 2018-08-24 오전 5:39
 * @Homepage : https://github.com/gusdnd852
 */
public abstract class BackPressActivity extends BaseActivity {

    @Override public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            AwesomeInfoDialog dialog = new AwesomeInfoDialog(this);
            dialog.setTitle("앱 종료");
            dialog.setMessage("앱을 종료하시겠습니까?");
            dialog.setPositiveButtonText("확인");
            dialog.setPositiveButtonTextColor(R.color.white);
            dialog.setPositiveButtonbackgroundColor(R.color.colorPrimary);
            dialog.setPositiveButtonClick(super::onBackPressed);
            dialog.setNegativeButtonTextColor(R.color.white);
            dialog.setNegativeButtonbackgroundColor(R.color.colorPrimary);
            dialog.setNegativeButtonText("취소");
            dialog.setNegativeButtonClick(dialog::hide);
            dialog.setCancelable(true);
            dialog.setColoredCircle(R.color.colorPrimary);
            dialog.show();
        } else {
            getFragmentManager().popBackStack();
        }
    }

}
