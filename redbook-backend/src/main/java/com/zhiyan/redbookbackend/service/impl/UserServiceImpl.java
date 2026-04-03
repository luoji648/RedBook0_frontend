package com.zhiyan.redbookbackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhiyan.redbookbackend.dto.LoginFormDTO;
import com.zhiyan.redbookbackend.dto.Result;
import com.zhiyan.redbookbackend.dto.UserDTO;
import com.zhiyan.redbookbackend.entity.User;
import com.zhiyan.redbookbackend.mapper.UserMapper;
import com.zhiyan.redbookbackend.service.IUserService;
import com.zhiyan.redbookbackend.util.RedisConstants;
import com.zhiyan.redbookbackend.util.RegexUtils;
import com.zhiyan.redbookbackend.util.SystemConstants;
import com.zhiyan.redbookbackend.util.UserHolder;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result sendCode(String phone, HttpSession session) {
        // 1.校验手机号
        if (RegexUtils.isPhoneInvalid(phone)) {
            // 2.如果不符合，返回错误信息
            return Result.fail("手机号格式错误");
        }
        // 3.符合，生成验证码
        String code = RandomUtil.randomNumbers(6);
        // 4.保存验证码到redis
        stringRedisTemplate.opsForValue().set(RedisConstants.LOGIN_CODE_KEY + phone, code, RedisConstants.LOGIN_CODE_TTL, TimeUnit.MINUTES);
        // 5.发送验证码
        log.debug("发送短信验证码成功，验证码：" + code);
        return Result.ok();
    }

    @Override
    public Result login(LoginFormDTO loginForm, HttpSession session) {
        // 1.校验手机号
        String phone = loginForm.getPhone();
        if (RegexUtils.isPhoneInvalid(phone)) {
            // 如果不符合，返回错误信息
            return Result.fail("手机号格式错误");
        }
        // 2.校验验证码
        String cacheCode = stringRedisTemplate.opsForValue().get(RedisConstants.LOGIN_CODE_KEY + phone);
        String code = loginForm.getCode();
        if (cacheCode == null || !cacheCode.equals(code)) {
            return Result.fail("验证码错误");
        }

        // 3.根据手机号查询用户
        User user = userMapper.getByPhone(phone);

        // 4.校验用户是否存在
        if (user == null) {
            // 如果用户不存在，创建新用户
            user = CreateUserWithPhone(phone);
        }

        // 保存用户信息到redis
        // 1.生成token
        String token = UUID.randomUUID().toString();

        // 2.User转成HashMap
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));

        // 3.存储
        stringRedisTemplate.opsForHash().putAll(RedisConstants.LOGIN_USER_KEY + token, userMap);
        stringRedisTemplate.expire(RedisConstants.LOGIN_USER_KEY + token, RedisConstants.LOGIN_USER_TTL, TimeUnit.SECONDS);
        return Result.ok(token);
    }

    @Override
    public Result sign() {
        // 1.获取用户id
        Long userId = UserHolder.getUser().getId();
        // 2.获取日期信息
        LocalDateTime now = LocalDateTime.now();
        // 3.拼接key
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyy/MM"));
        String key = RedisConstants.USER_SIGN_KEY + userId +  keySuffix;
        // 4.获取今天是本月的第几天
        int dayOfMonth = now.getDayOfMonth();
        // 5.写入Redis SEIBIT key offset 1
        stringRedisTemplate.opsForValue().setBit(key, dayOfMonth - 1, true);
        return Result.ok();
    }

    @Override
    public Result signCount() {
        // 1.获取用户id
        Long userId = UserHolder.getUser().getId();
        // 2.获取日期信息
        LocalDateTime now = LocalDateTime.now();
        // 3.拼接key
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyy/MM"));
        String key = RedisConstants.USER_SIGN_KEY + userId +  keySuffix;
        // 4.获取今天是本月的第几天
        int dayOfMonth = now.getDayOfMonth();

        // 5.从Redis中本月所有签到数据 BITFEILD get udayOfMonth 0
        List<Long> bitMapList = stringRedisTemplate.opsForValue().bitField
                (key, BitFieldSubCommands.create()
                        .get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(0)
                );
        // 判断是否为空
        if (CollectionUtil.isEmpty(bitMapList)) {
            return Result.ok(0);
        }
        Long num = bitMapList.get(0);
        if (num == null || num == 0) {
            return Result.ok(0);
        }
        // 6.循环
        int cnt = 0; // 计数器
        while (true) {
            if ((num & 1) == 0) {
                break;
            }
            cnt++;
            num >>>= 1;
        }
        return Result.ok(cnt);
    }

    private User CreateUserWithPhone(String phone) {
        User user = new User();
        user.setPhone(phone);
        user.setNickName(SystemConstants.USER_NICK_NAME_PREFIX + RandomUtil.randomString(10));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        userMapper.insert(user);
        return user;
    }
}
