package com.scu.service;

import java.util.List;
import java.util.Map;

import com.scu.pojo.TabConferenceUser;
import com.scu.pojo.TabUser;

public interface ConferenceService {

	public Map<String,String> ConferenceRegisterFormFormatJudge(TabConferenceUser conferenceUser);

	public boolean AddConference(TabConferenceUser conferenceUser);
	
	public List<TabConferenceUser> findConferenceuserByWebuserid(long webuserid);
	
	public List<TabConferenceUser> findConferenceuserByName(String name,long registUserId);//根据会议注册的用户名进行查询,且不包括registUserId

	public TabConferenceUser findConferenceUserById(long id);//根据主键查询
	
	public boolean updateTypeofConferenceUser(String conferenceUserId,String type);
	
	public boolean updateIsPaybyconferenceUserArray(List<String> conferenceUserIdList,byte is_pay);
}
