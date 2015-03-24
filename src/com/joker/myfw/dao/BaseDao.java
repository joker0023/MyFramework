package com.joker.myfw.dao;

import com.joker.myfw.data.Data;
import com.joker23.orm.persistence.POJO;

public class BaseDao<T extends POJO> extends com.joker23.orm.dao.BaseDao<T> {
	
	public BaseDao(Class<T> beanClass) {
		super(beanClass, Data.alias);
	}
	
}
