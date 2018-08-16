package com.dai.daicommon.service.view;

import com.dai.daicommon.service.bean.VersionBean;

public interface VersionView extends MyView {
    void onSuccess(VersionBean vb);
    void onError(String result);
}
