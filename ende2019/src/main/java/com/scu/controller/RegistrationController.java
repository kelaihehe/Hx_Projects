package com.scu.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scu.pojo.Msg;
import com.scu.pojo.TabConferenceUser;
import com.scu.pojo.TabPaper;
import com.scu.service.ConferenceService;

@Controller
public class RegistrationController {

	@Autowired
	private ConferenceService conferenceService;
	
	@RequestMapping("/queryRegistration")
	@ResponseBody
	public Msg queryRegistration(@RequestParam(value="page",defaultValue="1") Integer pn,HttpServletRequest request) throws UnsupportedEncodingException
	{
		String userid = null;
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length>0)
		{
			for(Cookie c : cookies)
			{
				if(c.getName().equals("userid"))
				{
					userid = URLDecoder.decode(c.getValue(),"utf-8");					
				}
			}
		}		
		List<TabConferenceUser> conferenceUserList = new ArrayList<TabConferenceUser>();
		
		PageHelper.startPage(pn, 6);
		conferenceUserList = conferenceService.findConferenceuserByWebuserid(Long.valueOf(userid));

		PageInfo pageInfo = new PageInfo(conferenceUserList,5);
		return Msg.success().addData("PageInfo", pageInfo); 
	}
	
	@RequestMapping("/queryOtherRegistration")
	@ResponseBody
	public Msg queryOtherRegistration(@RequestParam(value="page",defaultValue="1") Integer pn,String name,HttpServletRequest request) throws UnsupportedEncodingException
	{
		String userid = null;
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length>0)
		{
			for(Cookie c : cookies)
			{
				if(c.getName().equals("userid"))
				{
					userid = URLDecoder.decode(c.getValue(),"utf-8");					
				}
			}
		}
		List<TabConferenceUser> conferenceUserList = new ArrayList<TabConferenceUser>();
		
		PageHelper.startPage(pn, 6);
		conferenceUserList = conferenceService.findConferenceuserByName(name,Long.valueOf(userid));

		PageInfo pageInfo = new PageInfo(conferenceUserList,5);
		return Msg.success().addData("PageInfo", pageInfo); 
	}
	
	@RequestMapping("/updateTypeofConferenceUser")
	@ResponseBody
	public Msg updateTypeofConferenceUser(String conferenceUserId,String type,HttpServletRequest request) throws UnsupportedEncodingException
	{		
		if(conferenceService.updateTypeofConferenceUser(conferenceUserId, type))
		{
			return Msg.success();
		}
		else
		{
			return Msg.fail();
		}
	}
}
