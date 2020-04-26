package com.yhwl.yhwl.esc.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhwl.yhwl.esc.api.entity.AccountInfo;
import com.yhwl.yhwl.esc.api.mapper.AccountInfoMapper;
import com.yhwl.yhwl.esc.api.service.AccountInfoService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AccountInfoServiceImpl extends ServiceImpl<AccountInfoMapper, AccountInfo> implements AccountInfoService {

    @Override
    public AccountInfo findByUserName(String userName) {
//        QueryWrapper<AccountInfo> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_name", userName);
        return baseMapper.findByUserName(userName);
    }

    @Override
    public AccountInfo findByNickName(String nickname) {
        QueryWrapper<AccountInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("nick_name",nickname);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public AccountInfo findByInvitationCode(String fromuser) {
        QueryWrapper<AccountInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("invitation_code",fromuser);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public AccountInfo findByBackups(String word) {
        QueryWrapper<AccountInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("backups",word);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage findByPage(Page page, String username,String nickname,String phone) {
        QueryWrapper<AccountInfo> queryWrapper=new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)) {
            queryWrapper.like("user_name", username.trim());
        }
        if(StringUtils.isNotBlank(phone)){
            queryWrapper.eq("phone",phone);
        }
        if (StringUtils.isNotBlank(nickname)){
        	queryWrapper.like("nick_name",nickname.trim());
		}
        queryWrapper.orderByDesc("create_date");
        return baseMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int findTodayUser() {
        QueryWrapper<AccountInfo> wrapper = new QueryWrapper<>();
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR,1);
        Date tomorrow = calendar.getTime();
        wrapper.between("create_date",zero,tomorrow);
        return baseMapper.selectCount(wrapper);
    }

    @Override
    public int findAllUserCount() {
        return baseMapper.findAllUserCount();
    }

    @Override
    public List<Map<String, Object>> increaseData(Integer days) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_YEAR,0-days);
        Date before = calendar.getTime();
        QueryWrapper<AccountInfo> wrapper = new QueryWrapper<>();
        wrapper.between("create_date",before,date);
        wrapper.orderByAsc("create_date");
        List<AccountInfo> accounts = baseMapper.selectList(wrapper);
        List<Map<String,Object>> list= new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        for(int i = 0;i < days;i++) {
            Map<String,Object> map = new HashMap<>();
            Date dis = calendar.getTime();
            map.put("name",sdf.format(dis));
            int sum = 0;
            for (AccountInfo account : accounts) {
                Calendar c1 = Calendar.getInstance();
                c1.setTime(account.getCreateDate());
                if(calendar.get(Calendar.DAY_OF_YEAR) == c1.get(Calendar.DAY_OF_YEAR)) {
                    sum ++;
                }
            }
            map.put("value",sum);
            list.add(map);
            calendar.add(Calendar.DAY_OF_YEAR,1);
        }
        return list;
    }

    @Override
    public List<AccountInfo> listOther() {
        return baseMapper.listOther();
    }

	@Override
	public List<String> listByActive() {

		return baseMapper.listByActive();
	}

}
