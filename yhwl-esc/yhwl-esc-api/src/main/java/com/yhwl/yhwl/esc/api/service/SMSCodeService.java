package com.yhwl.yhwl.esc.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yhwl.yhwl.esc.api.entity.SMSCode;


public interface SMSCodeService extends IService<SMSCode> {
    SMSCode findByPhoneOnType(String phone, String type);
}
