package com.snow.security.core.controller;

import com.snow.lib.result.ResultVO;
import com.snow.lib.result.ResultVOUtil;
import com.snow.security.core.service.UserService;
import com.snow.security.core.support.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * @create by SNOW 2018.07.12
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResultVO me(@Autowired Principal principal) {
        return ResultVOUtil.success(principal);
    }

    @PostMapping("/registration")
    public ResultVO registration(@RequestBody @Valid UserDto userDto) {
        if (null != userService.registerNewUserAccount(userDto)) {
            return ResultVOUtil.success();
        } else {
            return ResultVOUtil.error(-1, "注册失败");
        }
    }
}
