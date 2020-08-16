package com.sakura.tm.web.controller.smbms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sakura.tm.common.annotation.Permission;
import com.sakura.tm.common.entity.SmbmsUser;
import com.sakura.tm.common.util.PageResult;
import com.sakura.tm.service.SmbmsUserService;
import com.sakura.tm.web.query.SmbmsUserQuery;

/**
 * @author 李七夜
 * Created by 李七夜 on 2020/7/31 15:53
 */
@RestController
@RequestMapping("/smbms/user")
public class SmbmsUserController {

    @Autowired
    private SmbmsUserService smbmsUserService;

    @CrossOrigin(origins = "*")
    @Permission(noLogin = true)
    @RequestMapping("/list")
    public PageResult<SmbmsUser> list(SmbmsUserQuery smbmsUserQuery) {
        return smbmsUserService.list(smbmsUserQuery);

    }
}
