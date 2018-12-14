/**
 *
 */
package cn.com.qytx.oa.email.service;

import java.io.Serializable;
import java.util.List;

import cn.com.qytx.oa.email.domain.EmailBody;
import cn.com.qytx.oa.email.domain.EmailTo;
import cn.com.qytx.oa.email.vo.EmailSearchVo;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能:电子邮件服务接口类
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-03-14
 * 修改日期: 2013-03-14
 * 修改人员: 汤波涛
 * 修改列表:初始加入的方法
 */
public interface IEmail extends BaseService<EmailBody>, Serializable {

    /**
     * 保存并发送邮件
     *
     * @param emailBody 邮件体
     */
    public void saveAndSend(EmailBody emailBody);

    /**
     * 发送指定id的邮件
     *
     * @param emailBodyId 邮件体id
     */
    public void saveAndSend(int emailBodyId);

    /**
     * 更新邮件体（草稿和已发送的未读邮件）
     *
     * @param emailBody 邮件体对象
     */
    public void update(EmailBody emailBody);

    /**
     * 更新并发送邮件
     *
     * @param emailBody 邮件体
     */
    public void updateAndSend(EmailBody emailBody);

    /**
     * 发送草稿箱邮件
     *
     * @param emailBodyId 邮件体id
     */
    public boolean updateAndSendDraft(int emailBodyId);


    /**
     * 删除一封草稿或者已发邮件(同步取消发送收信人中为查看的)
     *
     * @param emailBodyId 邮件体ID
     */
    public void deleteEmailBody(int emailBodyId);

    /**
     * 更新邮件接受信息（收件箱邮件）
     * 星级、查看状态、移动邮件箱、恢复删除
     *
     * @param emailTo 接收的邮件
     */
    public void updateEmailTo(EmailTo emailTo);

    /**
     * 将某个用户某个邮件箱中的未读邮件全部标记为已读取
     * @param userId 用户ID
     * @param emailBoxId 邮件箱ID
     * @return 更新的条数
     */
    public int updateEmailToAllReaded(int userId,int emailBoxId);

    /**
     * 删除一封收件箱邮件，可恢复
     *
     * @param emailToId 收件箱邮件ID
     */
    public void deleteEmailTo(int emailToId);

    /**
     * 删除某个邮件箱所有已经读取的邮件
     *
     * @param userId 用户ID
     * @param boxId  邮件箱ID
     */
    public int deleteEmailToReaded(int userId,int boxId);

    /**
     * 销毁一封收件箱邮件
     *
     * @param emailToId 收件箱邮件ID
     */
    public void destroyEmailTo(int emailToId);

    /**
     * 销毁某个用户某个邮件箱中的所有邮件
     *
     * @param userId     用户Id
     * @param emailBoxId 邮件箱ID
     */
    public void destroyAllEmailTo(int userId, int emailBoxId);

    /**
     * 恢复一封废纸篓邮件到默认收件箱
     *
     * @param emailToId 邮件id
     */
    public void recoveryEmailTo(int emailToId);

    /**
     * 移动一封邮件到其他邮件箱
     *
     * @param emailToId   邮件
     * @param targetBoxId 目标邮件箱id
     */
    public void moveEmailTo(int emailToId, int targetBoxId);

    /**
     * 批量移动邮件，从某个邮件箱移动到某个邮件箱
     *
     * @param userId      用户ID
     * @param fromBoxId   从哪个邮件箱id
     * @param targetBoxId 移动到哪个邮件箱id
     */
    public void moveAllEmailTo(int userId, int fromBoxId, int targetBoxId);

    /**
     * 标记一封邮件为已读取
     *
     * @param emailToId 邮件
     */
    public void readedEmailTo(int emailToId);


    /**
     * 获取某个用户的某个邮件箱的所有邮件
     *
     * @param page       翻页信息
     * @param userId     用户ID
     * @param emailBoxId 邮件箱ID
     * @return 查询结果
     */
    public Page<EmailTo> findAllReceiveEmailByUserId(Pageable page, int userId, int emailBoxId);

    /**
     * 搜索某个用户的某个邮件箱邮件
     *
     * @param page       翻页信息
     * @param userId     用户Id
     * @param emailBoxId 邮件箱Id
     * @param searchWord 搜索关键字
     * @return 查询结果
     */
    public Page<EmailTo> findAllReceiveEmailBySearchWord(Pageable page, int userId,
                                                         int emailBoxId, String searchWord);

    /**
     * 查找某个邮件箱下的所有邮件
     * @param emailBoxId 邮件箱ID
     * @return 所有邮件
     */
    public List<EmailTo> findAllReceiveEmailByBoxId(int emailBoxId);


    /**
     * 查询某个用户未读取的邮件
     *
     * @param page   翻页信心
     * @param userId 用户ID
     * @return 查询结果
     */
    public Page<EmailTo> findAllNotReadEmail(Pageable page, int userId);

    /**
     * 获取未读取邮件的个数
     *
     * @param userId 用户Id
     * @return 未读邮件数
     */
    public int countOfNotReadEmail(int userId);

    /**
     * 获取未读取邮件的个数 收件箱
     * 2014.8.4
     * @param userId 用户Id
     * @return 未读邮件数
     */
    public int countOfNotReadInBox(int userId,int emailBoxId);
    
    /**
     * 查询某个用户未读取的邮件，可以搜索
     *
     * @param page       翻页信心
     * @param userId     用户ID
     * @param searchWord 搜索关键字
     * @return 查询结果
     */
    public Page<EmailTo> findAllNotReadEmailBySearchWord(Pageable page, int userId, String searchWord);

    /**
     * 查询一封收取的邮件
     *
     * @param emailToId 邮件id
     * @return 收件箱邮件
     */
    public EmailTo findEmailToById(int emailToId);

    /**
     * 更新收件箱邮件的星级
     *
     * @param emailToId 收件箱邮件ID
     * @param markLevel 要设置的级别标识
     */
    public void updateEmailToMarkLevel(int emailToId, int markLevel);

    /**
     * 查询某个用户已发送邮件
     *
     * @param page   翻页信息
     * @param userId 用户Id
     * @return 查询结果
     */
    public Page<EmailBody> findAllSendedEmailBodyByUserId(Pageable page, int userId,String searchWord);

    /**
     * 查询某个用户已发送邮件
     *
     * @param userId 用户Id
     * @return 查询结果
     */
    public List<EmailBody> findAllSendedEmailBodyByUserId(int userId);

    /**
     * 根据高级搜索条件查询某个用户的已发送邮件
     *
     * @param page          翻页信息
     * @param userId        用户ID
     * @param emailSearchVo 高级搜索信息
     * @return 结果集-翻页
     * @throws Exception 日期转换异常等等
     */
    public Page<EmailBody> findAllSendedEmailBodyBySearchVo(Pageable page, int userId, EmailSearchVo emailSearchVo) throws Exception;

    /**
     * 查询某个用户发件箱中的草稿
     *
     * @param page   翻页信息
     * @param userId 用户Id
     * @return 查询结果
     */
    public Page<EmailBody> findAllDraftEmailBodyByUserId(Pageable page, int userId,String searchWord);

    /**
     * 查询某个用户发件箱中的草稿
     *
     * @param userId 用户Id
     * @return 查询结果
     */
    public List<EmailBody> findAllDraftEmailBodyByUserId(int userId);

    /**
     * 查询某个用户发件箱中的草稿
     *
     * @param page          翻页信息
     * @param userId        用户Id
     * @param emailSearchVo 高级搜索条件
     * @return 查询结果
     */
    public Page<EmailBody> findAllDraftEmailBodyBySearchVo(Pageable page, int userId, EmailSearchVo emailSearchVo) throws Exception;


    /**
     * 查询一个邮件体-未删除
     *
     * @param emailBodyId 邮件体Id
     * @return 邮件体
     */
    public EmailBody findEmailBodyByIdNotDelete(int emailBodyId);

    /**
     * 根据邮件体查询所有发送的邮件信息-排除已取消发送的
     *
     * @param emailBodyId 邮件体ID
     * @return 未删除的发送的邮件信息
     */
    public List<EmailTo> findAllEmailToByEmailBodyId(int emailBodyId);

    /**
     * 根据邮件体查询所有发送的邮件信息-不排除已取消发送的
     *
     * @param emailBodyId 邮件体ID
     * @return 未删除的发送的邮件信息
     */
    public List<EmailTo> findAllEmailToHasCancelSendByEmailBodyId(int emailBodyId);

    /**
     * 根据用户ID和邮件箱ID查询某用户某个邮件箱中的接收邮件份数
     *
     * @param userId 用户ID
     * @param boxId  邮件箱ID
     * @return 邮件数量
     */
    public int countOfEmailToByBoxId(int userId, int boxId);

    /**
     * 根据用户ID和发送状态查询某用户某个发送状态的发送邮件份数
     * 0:查询草稿箱邮件份数，1：查询已发送邮件箱份数
     *
     * @param userId     用户ID
     * @param sendStatus 发送状态
     * @return 邮件数量
     */
    public int countOfEmailBodyByBoxId(int userId, int sendStatus);

    /**
     * 根据邮件体ID和用户ID查询用户读取状态
     *
     * @param emailBodyId 邮件体ID
     * @param userId      用户ID
     * @return 是否已读取，已读取返回1，未读取0
     */
    public int getReadStatusByEmailBodyIdAndUserId(int emailBodyId, int userId);

    /**
     * 删除所有接收人已经删除的邮件体
     *
     * @param userId 用户ID
     * @return 删除的份数
     */
    public int deleteEmailBodyIfToUserDeleted(int userId);

    /**
     * 删除所有接收人已经读取的邮件体
     *
     * @param userId 用户ID
     * @return 删除的份数
     */
    public int deleteEmailBodyIfToUserReaded(int userId);

    /**
     * 删除所有接收人未读取的邮件体
     *
     * @param userId 用户ID
     * @return 删除的份数
     */
    public int deleteEmailBodyIfToUserNotRead(int userId);

    /**
     * 删除所有废纸篓中的邮件
     *
     * @param userId 用户ID
     * @return 删除的份数
     */
    public int deleteEmailToWastebasket(int userId);

    /**
     * 获取某个用户的所有邮件体，不带翻页
     *
     * @param userId 用户ID
     * @return 用户的所有邮件
     */
    public List<EmailBody> findAllEmailBodyByUserId(int userId);

    /**
     * 获取某个用户的最近邮件联系人
     *
     * @param userId 用户ID
     * @param count  获取的最近联系人最多人数
     * @return 最近联系人列表
     */
    public List<UserInfo> findRecentContacts(int userId, int count);

    /**
     * 根据高级搜索信息搜索收件信息
     *
     * @param page          翻页信息
     * @param userId        用户ID
     * @param emailSearchVo 搜索VO
     * @return 数据信息带翻页
     */
    public Page<EmailTo> findAllReceiveEmailBySearchVO(Pageable page, int userId, EmailSearchVo emailSearchVo) throws Exception;

    /**
     * 收回一封邮件
     *
     * @param emailToId 邮件ID
     * @return 是否收回成功，失败原因：收件人已经读取
     */
    public boolean updateEmailToForTakeBack(int emailToId);

    /**
     * 发送事物提醒
     *
     * @param emailTo 邮件体
     */
    public void checkAndSendAffairsForSend(EmailTo emailTo);

    /**
     * 检查并发送邮件阅读回执
     *
     * @param emailTo 邮件接收信息
     */
    public void checkAndSendAffairsForRead(EmailTo emailTo);

    /**
     * 获取所有收取的邮件，不区分邮件箱，为移动设备使用
     * @param page 分页信息
     * @param userId 用户ID
     * @return 分页信息
     */
    public Page<EmailTo> findAllInBoxEmailsForMobile(Pageable page,int userId);
}
