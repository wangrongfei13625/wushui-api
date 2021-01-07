package com.huaxin.webchat.mapper;

import java.util.List;
import java.util.Map;

public interface EtlWarnConfigMapper {

    List<Map<String,Object>> findById(Map<String,Object> params);

    void saveOfUpdateConfig (Map<String,Object> params);

    void deleteEtlWarnConfig (Map<String,Object> params);

}
