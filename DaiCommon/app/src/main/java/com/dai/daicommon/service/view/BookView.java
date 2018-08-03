package com.dai.daicommon.service.view;

import com.dai.daicommon.service.bean.Book;

public interface BookView extends MyView {
    void onSuccess(Book mBook);
    void onError(String result);
}
