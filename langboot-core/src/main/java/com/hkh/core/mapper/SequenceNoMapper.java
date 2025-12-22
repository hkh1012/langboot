package com.hkh.core.mapper;

import com.hkh.domain.vo.serialnumber.MaxNoEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface SequenceNoMapper {

    void getSequenceNo(HashMap<String,Object> map);

    MaxNoEntity getMaxNo(String prefixKey);

    void updateMaxNo(MaxNoEntity maxNoEntity);
}
