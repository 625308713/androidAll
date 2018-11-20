package com.dai.daicommon.service.view;

import com.dai.daicommon.service.bean.Person;

public interface PersonView extends MyView {
    void onSuccess(Person person);
    void onError(String result);
}
