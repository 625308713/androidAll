package com.dai.daicommon.service.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * 分组布局使用
 */
public class GroupBook extends SectionEntity<Book.BooksBean> {
    public GroupBook(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public GroupBook(Book.BooksBean o) {
        super(o);
    }
}
