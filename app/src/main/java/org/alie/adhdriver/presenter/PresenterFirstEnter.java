package org.alie.adhdriver.presenter;


import org.alie.adhdriver.SpUtil;
import org.alie.adhdriver.constant.ConstSp;
import org.alie.adhdriver.iview.IFirstEnterView;

/**
 * Created by Alie on 2017/11/16.
 * 类描述
 * 版本
 */

public class PresenterFirstEnter extends BasePresenter<IFirstEnterView> {

    private IFirstEnterView iFirstEnterView;


    public PresenterFirstEnter(IFirstEnterView iFirstEnterView) {
        this.iFirstEnterView = iFirstEnterView;
//        this.iFirstEnter = new IFirstEnterImpl();
    }


    /**
     * 权限判断
     */
    public void doPermissionCheck() {

        iFirstEnterView.doPermissionCheck();

    }

    public void goToWelCome() {

        iFirstEnterView.doGoToWelcome();
    }


    public void goToMain() {

        iFirstEnterView.doGoToMain();

    }


    public void doGoToMainOrWelCome() {

        boolean loadOrNot = SpUtil.getInstance().getBoolenValue(ConstSp.SP_KEY_LOAD_OR_NOT, false);


        if (loadOrNot) {

            iFirstEnterView.doGoToMain();

        } else {
            iFirstEnterView.doGoToWelcome();

        }

    }

    public void doShowPermissionDialog() {

        iFirstEnterView.doShowPermissionDialog();

    }


}
