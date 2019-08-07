package org.alie.adhdriver.presenter;


import org.alie.adhdriver.iview.IWelcomView;

/**
 * Created by Alie on 2017/11/16.
 * 类描述
 * 版本
 */

public class PresenterWelcome extends BasePresenter<IWelcomView> {

    private IWelcomView iWelcomView;



    public PresenterWelcome(IWelcomView iWelcomView) {
        this.iWelcomView = iWelcomView;
    }


    public void doGoToMain() {

        iWelcomView.doGoToMain();

    }



}
