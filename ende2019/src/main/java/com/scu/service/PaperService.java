package com.scu.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.scu.pojo.TabPaper;

public interface PaperService {

	public Map<String,String> contributeFormFormatjudge(TabPaper paper,MultipartFile file);//投稿表单判断
	
	public long addPaper(TabPaper paper);//添加paper
	
	public List<TabPaper> findPapersByuserId(long userId); //根据网站用户id查询paperList

	public TabPaper findPaperByPaperId(String PaperId);  //根据paper主键查询paper

	public boolean updatePaperByPaperId(TabPaper paper);  //根据paper主键更新paper

	public boolean deletePaperByPaperId(String Id);   //根据paper主键删除paper

	public List<TabPaper> findAllPapers();  //查询所有的paper
}
