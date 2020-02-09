package com.scu.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.scu.mapper.TabConferenceUserMapper;
import com.scu.pojo.TabConferenceUser;
import com.scu.pojo.TabConferenceUserExample;
import com.scu.pojo.TabPaperExample;
import com.scu.pojo.TabUser;
import com.scu.service.ConferenceService;

public class ConferenceServiceImpl implements ConferenceService {

	@Autowired
	private TabConferenceUserMapper mapper;
	
	@Override
	public Map<String, String> ConferenceRegisterFormFormatJudge(TabConferenceUser conferenceUser) {
		Map<String,String> errors = new HashMap<String,String>();
		
		//姓名表项验证
		String username = conferenceUser.getName();
		if(username==null||username.isEmpty())
		{
			errors.put("username", "用户名不能为空");
		}
		
		//邮箱表项验证
		String email = conferenceUser.getEmail();
		if(email==null||email.isEmpty())
		{
			errors.put("email", "Email不能为空");
		}
		else if(!email.matches("\\w+@\\w+\\.\\w+"))
		{
			errors.put("email", "Email格式错误");
		}
		
		//学校机构表项验证
		String affiliation = conferenceUser.getAffiliation();
		if(affiliation==null||affiliation.isEmpty())
		{
			errors.put("affiliation", "学校机构不能为空");
		}	
		
		return errors;
	}

	
	@Override
	public boolean AddConference(TabConferenceUser conferenceUser) {
		conferenceUser.setCreateTime(new Date());
		conferenceUser.setLastUpdateTime(new Date());
		int result = mapper.insertSelective(conferenceUser);
		return result==1?true:false;
	}


	@Override
	public List<TabConferenceUser> findConferenceuserByWebuserid(long webuserid) {
		TabConferenceUserExample example = new TabConferenceUserExample();
		TabConferenceUserExample.Criteria criteria = example.createCriteria();
		criteria.andRegistUserIdEqualTo(webuserid).andIsPayEqualTo((byte)0);
		
		return mapper.selectByExample(example);
	}


	@Override
	public List<TabConferenceUser> findConferenceuserByName(String name,long registUserId) {
		TabConferenceUserExample example = new TabConferenceUserExample();
		TabConferenceUserExample.Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo(name).andIsPayEqualTo((byte)0).andRegistUserIdNotEqualTo(registUserId);
		
		return mapper.selectByExample(example);
	}

	@Override
	public TabConferenceUser findConferenceUserById(long id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateTypeofConferenceUser(String conferenceUserId, String type){
		TabConferenceUser conferenceUser = new TabConferenceUser();
		conferenceUser.setId(Long.valueOf(conferenceUserId));
		conferenceUser.setIsStudent(Byte.valueOf(type));
		int result = mapper.updateByPrimaryKeySelective(conferenceUser);
		return result==1?true:false;
	}
	
	@Override
	public boolean updateIsPaybyconferenceUserArray(List<String> conferenceUserIdList,byte is_pay){
		TabConferenceUser conferenceUser = new TabConferenceUser();
		int result=0 ; 
		for(int i=0; i<conferenceUserIdList.size();i++)
		{
			conferenceUser.setId(Long.valueOf(conferenceUserIdList.get(i)));
			conferenceUser.setIsPay(is_pay);
			result = mapper.updateByPrimaryKeySelective(conferenceUser);
		}
		return result==0?false:true;
	}



	

}
