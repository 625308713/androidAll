package com.dai.daicommon.service.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;

public class Level1Person extends AbstractExpandableItem<Person> {
    @Override
    public int getLevel() {
        return 1;
    }
}
