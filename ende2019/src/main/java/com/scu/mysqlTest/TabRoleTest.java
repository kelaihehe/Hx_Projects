/*
package com.scu.mysqlTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.scu.mapper.TabRoleMapper;
import com.scu.mapper.TabUserMapper;
import com.scu.pojo.TabRole;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring/applicationContext-dao.xml"})
public class TabRoleTest {

	@Autowired
	private TabRoleMapper roleMapper;
	
	@Test
	public void insertTest1()
	{
		//普通会员
		TabRole role = new TabRole();
		role.setRoleName("member");
		role.setDescription("contribute and register");
		role.setAuthority(1);
		int result = roleMapper.insert(role);
		System.out.println(result);
	}
	
	@Test
	public void insertTest2()
	{

		//编辑
		TabRole role = new TabRole();
		role.setRoleName("editor");
		role.setDescription("distribute paper");
		role.setAuthority(2);
		int result = roleMapper.insert(role);
		System.out.println(result);
	}
	
	@Test
	public void insertTest3()
	{
		//管理员
		TabRole role = new TabRole();
		role.setRoleName("administrator");
		role.setDescription("administrate");
		role.setAuthority(3);
		int result = roleMapper.insert(role);
		System.out.println(result);
	}
}
*/
