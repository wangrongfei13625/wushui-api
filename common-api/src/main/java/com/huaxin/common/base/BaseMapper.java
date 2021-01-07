package com.huaxin.common.base;

import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by ${wangrf} on 2020-12-07
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T>,IdsMapper<T> {
}
