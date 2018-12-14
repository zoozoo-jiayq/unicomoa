/**
 *
 */
package cn.com.qytx.oa.email.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import cn.com.qytx.oa.email.domain.EmailBox;
import cn.com.qytx.oa.email.service.EmailConst;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.utils.CbbConst;

/**
 * 功能:邮件箱数据层Dao类
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-03-14
 * 修改日期: 2013-03-14
 * 修改人员: 汤波涛
 * 修改列表:初始加入的方法
 */
@Component
public class EmailBoxDao extends BaseDao<EmailBox, Integer> implements Serializable{

    /**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 查询指定用户ID的所有邮件箱
     *
     * @param userId 用户ID
     * @return 符合条件的邮件箱集合
     */
    @SuppressWarnings("unchecked")
    public List<EmailBox> findAllByUserId(int userId) {
        return super.findAll(" userId=?1 and isDelete=?2 ",new Sort(Direction.ASC, "boxType","orderNo","boxName")
                ,userId, CbbConst.NOT_DELETE);
    }

    /**
     * 查询指定用户ID的所有自定义邮件箱
     *
     * @param userId 用户ID
     * @return 符合条件的邮件箱集合
     */
    @SuppressWarnings("unchecked")
    public List<EmailBox> findAllDiyBoxByUserId(int userId) {
        String condition=" userId=?1 and boxType=?2 and isDelete=?3 ";
        return super.findAll(condition,new Sort(Direction.ASC, "orderNo","boxName"), userId, EmailConst.EMAIL_BOX_TYPE_DIY, CbbConst.NOT_DELETE);
    }

    /**
     * 查询单个邮件箱
     *
     * @param id 邮件箱ID
     * @return 符合该ID的邮件箱
     */
    public EmailBox findById(Integer id) {
        return (EmailBox) super.findOne(" id=?1 and isDelete=?2", id, CbbConst.NOT_DELETE);
    }

    /**
     * 根据用户id和邮件箱类型获取一个系统默认的邮件箱
     *
     * @param userId  用户ID
     * @param boxType 邮件箱类型
     * @return 符合条件的一个邮件箱对象
     */
    public EmailBox findDefaultBoxByUserIdAndBoxType(int userId, int boxType) {
        String condition = " userId=?1 and boxType=?2 and isDelete=?3";
        Object[] values = {userId, boxType, CbbConst.NOT_DELETE};
        return (EmailBox) super.findOne(condition, values);
    }
    

    public void deleteEmailBoxByUserIds(String userIds){
    	this.executeQuery("update EmailBox x set x.isDelete = ? where x.userId in ("+userIds+") ", CbbConst.DELETED);
    }
}
