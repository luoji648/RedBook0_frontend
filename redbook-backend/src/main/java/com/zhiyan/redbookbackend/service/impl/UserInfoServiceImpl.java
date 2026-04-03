package com.zhiyan.redbookbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhiyan.redbookbackend.entity.UserInfo;
import com.zhiyan.redbookbackend.mapper.UserInfoMapper;
import com.zhiyan.redbookbackend.service.IUserInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

}
