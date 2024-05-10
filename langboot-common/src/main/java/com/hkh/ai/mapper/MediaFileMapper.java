package com.hkh.ai.mapper;

import com.hkh.ai.domain.MediaFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author huangkh
* @description 针对表【media_file(媒体文件)】的数据库操作Mapper
* @createDate 2024-01-11 13:57:30
* @Entity com.hkh.ai.domain.MediaFile
*/
@Mapper
public interface MediaFileMapper extends BaseMapper<MediaFile> {

}




