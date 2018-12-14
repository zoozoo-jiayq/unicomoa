package cn.com.qytx.cbb.jpush.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cn.com.qytx.cbb.jpush.domain.PushInfo;

/**
 * 
 * <br/>功能: 推送映射
 * <br/>版本: 1.0
 * <br/>开发人员: REN
 * <br/>创建日期: 2013-11-21
 * <br/>修改日期: 2013-11-21
 * <br/>修改列表:
 */
public class PushInfoMapper  implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

		PushInfo item = new PushInfo();
		item.setPushId(rs.getInt("PushId"));
		item.setSubject(rs.getString("Subject"));
		item.setUserId(rs.getInt("UserId"));
		item.setUserName(rs.getString("UserName"));
		item.setShowContent(rs.getString("ShowContent"));
		item.setPushContent(rs.getString("PushContent"));
		item.setPushTime(rs.getTimestamp("PushTime"));
		item.setInsertTime(rs.getTimestamp("InsertTime"));
		item.setIsDelete(rs.getInt("IsDelete"));
        return item;
	}
}