package com.scu.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.scu.mapper.TabPaperMapper;
import com.scu.pojo.TabPaper;
import com.scu.pojo.TabPaperExample;
import com.scu.pojo.TabUserExample;
import com.scu.service.PaperService;

public class PaperServiceImpl implements PaperService {

	@Autowired
	private TabPaperMapper mapper;
	
	@Override
	public Map<String, String> contributeFormFormatjudge(TabPaper paper,MultipartFile file) {
		
		Map<String,String> errors = new HashMap<String,String>();
		
		//姓名表项验证
		String username = paper.getName();
		if(username==null||username.isEmpty())
		{
			errors.put("username", "用户名不能为空");
		}
		
		//邮箱表项验证
		String email = paper.getEmail();
		if(email==null||email.isEmpty())
		{
			errors.put("email", "Email不能为空");
		}
		else if(!email.matches("\\w+@\\w+\\.\\w+"))
		{
			errors.put("email", "Email格式错误");
		}
		
		//稿件标题表项验证
		String title = paper.getTitle();
		if(title==null||email.isEmpty())
		{
			errors.put("title", "标题不能为空");
		}
		
		//稿件上传验证
		if(file==null||file.getOriginalFilename()==null&&file.getOriginalFilename().length()>0)
		{
			errors.put("file", "未上传文件");
		}
		else if(file.getSize()>104857600)
		{
			errors.put("file", "文件大小超过100MB");
		}
		return errors;
	}

	
	public long addPaper(TabPaper paper)
	{
		paper.setCreateTime(new Date());
		paper.setLastUpdateTime(new Date());
		mapper.insertSelective(paper);
		System.out.println(paper.getId());
		return paper.getId();
	}


	public List<TabPaper> findPapersByuserId(long userId)
	{
		TabPaperExample example = new TabPaperExample();
		TabPaperExample.Criteria criteria = example.createCriteria();
		criteria.andCommitUserIdEqualTo(userId);
		return mapper.selectByExample(example);
	}

	public TabPaper findPaperByPaperId(String PaperId)
	{
		return mapper.selectByPrimaryKey(Long.valueOf(PaperId));
	}

	public boolean updatePaperByPaperId(TabPaper paper)
	{
		int result = mapper.updateByPrimaryKeySelective(paper);
		return result==1?true:false;
	}

	public boolean deletePaperByPaperId(String Id)
	{
		int result = mapper.deleteByPrimaryKey(Long.valueOf(Id));
		return result==1?true:false;
	}

	public List<TabPaper> findAllPapers()
	{
		TabPaperExample example = new TabPaperExample();
		TabPaperExample.Criteria criteria = example.createCriteria();
		criteria.andStateEqualTo((byte)1);//state为1表示正常
		return mapper.selectByExample(example);
	}
}
