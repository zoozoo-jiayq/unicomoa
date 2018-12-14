package cn.com.qytx.cbb.jpush.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cn.com.qytx.cbb.jpush.domain.PushUser;



/**
 * 
 * <br/>功能:  推送人员映射
 * <br/>版本: 1.0
 * <br/>开发人员: REN
 * <br/>创建日期: 2013-11-21
 * <br/>修改日期: 2013-11-21
 * <br/>修改列表:
 */
public class PushUserMapper  implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		PushUser item = new PushUser();
		item.setSendNo(rs.getInt("SendNo"));
		item.setPushId(rs.getInt("PushId"));
		item.setUserType(rs.getInt("UserType"));
		item.setUserId(rs.getInt("UserId"));
        return item;
	}
}