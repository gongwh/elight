package com.snow.security.core.controller;

import com.snow.lib.result.ResultVO;
import com.snow.lib.result.ResultVOUtil;
import com.snow.security.core.repository.entity.User;
import com.snow.security.core.service.UserService;
import com.snow.security.core.support.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @create by SNOW 2018.07.12
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResultVO me(@AuthenticationPrincipal User user) {
        return ResultVOUtil.success(user);
    }

    @PostMapping("/registration")
    public ResultVO registration(@RequestBody @Valid UserDto userDto) {
        User user = userService.registerNewUserAccount(userDto);
        if (null != user) {
            // 剔除敏感信息
            user.setPassword(null);
            return ResultVOUtil.success(user);
        } else {
            return ResultVOUtil.error(-1, "注册失败");
        }
    }
}
