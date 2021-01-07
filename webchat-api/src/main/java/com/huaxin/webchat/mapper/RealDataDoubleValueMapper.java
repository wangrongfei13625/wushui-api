package com.huaxin.webchat.mapper;

import java.util.List;
import java.util.Map;

public interface RealDataDoubleValueMapper {

    List<Map<String,Object>> findValueByTime(String Time);

}
