package com.logic.mapper;

import com.data.bean.AdminBean;
import com.repo.dao.AdminDao;

public class LogInDataMapper {
	private AdminDao adminDao;
	private AdminBean adminBean;
	
	public AdminDao mapBeanDataToDao(AdminBean adminBean) {
		adminDao = new AdminDao();
		
		adminDao.setAdminName(adminBean.getAdminName().toUpperCase());
		adminDao.setPassword(adminBean.getPassword());
		
		return adminDao;
	}
	
	public AdminBean mapDaoDataToBean(AdminDao adminDao) {
		adminBean = new AdminBean();
		
		adminBean.setAdminId(adminDao.getAdminId());
		adminBean.setAdminName(adminDao.getAdminName());
		
		return adminBean;
	}
}