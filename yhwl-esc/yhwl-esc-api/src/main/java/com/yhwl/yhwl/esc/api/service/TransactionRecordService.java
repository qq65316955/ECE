package com.yhwl.yhwl.esc.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhwl.yhwl.esc.api.entity.TransactionRecord;


public interface TransactionRecordService extends IService<TransactionRecord> {
    IPage<TransactionRecord> pageByAddressAndCoin(Page<TransactionRecord> transactionRecordPage, String address, String userName, String coin, String type);

    TransactionRecord findByHash(String hash);

    IPage<TransactionRecord> findByPage(Page page, String sendUser, String getUser, String coin, String status);
}
