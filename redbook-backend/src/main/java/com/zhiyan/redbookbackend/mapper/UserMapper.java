package com.zhiyan.redbookbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhiyan.redbookbackend.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 */

public interface UserMapper extends BaseMapper<User> {

    @Select("select * from tb_user where phone = #{phone}")
    User getByPhone(String phone);

    List<User> listTop5Users(@Param("ids") List<Long> top5UserIds);
}
