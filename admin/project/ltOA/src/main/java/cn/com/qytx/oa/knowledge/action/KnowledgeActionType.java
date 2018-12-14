package cn.com.qytx.oa.knowledge.action;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.org.util.TreeNode;
import cn.com.qytx.oa.knowledge.domain.KnowledgeType;
import cn.com.qytx.oa.knowledge.service.IKnowledgeType;
import cn.com.qytx.oa.util.PropertiesUtil;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能:知识库类型处理类
 * 版本: 1.0
 * 开发人员: 彭小东
 * 创建日期: 2015-2-10
 * 修改日期: 2015-2-10
 * 修改列表:
 */
public class KnowledgeActionType extends BaseActionSupport
{
    private static final long serialVersionUID = 7904041551298696632L;
    /**
     * 知识库类型逻辑接口
     */
    @Autowired
    private transient IKnowledgeType knowledgeTypeService;

    /**
     * 是否全部获得
     */
    private boolean containAll;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 上级分类Id 顶级分类时为0
     */
    private Integer parentId;

    /**
     * id
     */
    private Integer vid;
    /**
     * 排序
     */
    private Integer orderIndex;
    /**
     * 知识库类型实体类
     */
    private KnowledgeType knowledgeType;
    /**
     * isPrivate 0是公工的 1是个人的
     */
    private Integer isPrivate;

    /**
     * isOnly 0是显示顶级和本部门的 1是仅显示本部门的
     */
    private Integer isOnly;
    
    /**
     * 模块id
     */
    private Integer columnId;

    /**
     * 获得所有节点
     */
    public String selectTreeKnowledge()
    {
        UserInfo user = this.getLoginUser();
        if (isPrivate != null && isPrivate > 0)
        {
            isPrivate = user.getUserId();
        }
        else
        {
            isPrivate = 0;
        }
        if (isOnly == null)
        {
            isOnly = Integer.valueOf(1);
        }
        List<TreeNode> allNodes = knowledgeTypeService.findAllKnowledgeType(containAll, user, isPrivate, isOnly,columnId);

        ajax(allNodes);

        return null;
    }

    /**
     * 修改知识类
     * 0 成功
     * 1 失败
     * 2 无权限
     * 3 顶级类别不能修改
     * @return
     */
    public String updateType()
    {
        UserInfo user = getLoginUser();
        if (user != null)
        {
            if (vid != null && vid > 0)
            {
                knowledgeType = knowledgeTypeService.findById(vid);
                KnowledgeType ktype;
                if (knowledgeType.getIsPrivate() == 0)// 公共知识库类型
                {
                    ktype = knowledgeTypeService.findOne("isDelete = 0 and parentId=? and name=? ", new Object[]
                    { parentId, name });
                }
                else
                // 私有知识库类型
                {
                    ktype = knowledgeTypeService.findByNameForPrivate(name, parentId, knowledgeType.getIsPrivate());
                }
                if (ktype == null || vid.equals(ktype.getVid()))
                {
                    int a = 0;
//                    if (knowledgeType.getIsForkGroup() != user.getIsForkGroup())
//                    {
//                        a = 2;// 无权限
//                    }
                   if (knowledgeType.getParentId() == 0 && knowledgeType.getIsPrivate() == 0)
                    {
                        a = 3;// 顶级不能修改
                    }
                    else
                    {
                        knowledgeType.setOrderIndex(orderIndex);
                        knowledgeType.setName(name);
                        knowledgeType.setCompanyId(user.getCompanyId());
                        a = knowledgeTypeService.saveOrUpdateKnowledgeType(knowledgeType);
                    }
                    ajax(a);

                }
                else
                {
                    ajax(1);
                }
            }
            else
            {
                knowledgeType = new KnowledgeType();
                knowledgeType.setName(name);
                knowledgeType.setParentId(parentId);
                knowledgeType.setOrderIndex(orderIndex);
                knowledgeType.setCreateUserInfo(user);
                knowledgeType.setIsForkGroup(user.getIsForkGroup());
                knowledgeType.setColumnId(columnId);
                if (isPrivate != null && isPrivate > 0)
                {
                    knowledgeType.setIsPrivate(user.getUserId());
                }
                else
                {
                    knowledgeType.setIsPrivate(0);
                }
                knowledgeType.setCreateTime(new Timestamp(System.currentTimeMillis()));
                knowledgeType.setIsDelete(0);
                knowledgeType.setCompanyId(user.getCompanyId());
                int a = knowledgeTypeService.saveOrUpdateKnowledgeType(knowledgeType);
                ajax(a);
            }

        }

        return null;
    }

    /**
     * 删除
     */
    public String deleteType()
    {
        UserInfo user = getLoginUser();
        if (user != null)
        {
            KnowledgeType ktype = knowledgeTypeService.findById(vid);
            int a = 0;
             if (ktype.getParentId() == 0 && ktype.getIsPrivate() == 0)
            {
                a = 4;
            }
            else
            {
                a = knowledgeTypeService.deleteById(vid,columnId);
            }
            ajax(a);
        }
        return null;
    }

    /**
     * 跳转到修改type页面
     * @return
     */
    public String toUpdateKnowledgeType()
    {
        knowledgeType = knowledgeTypeService.findById(knowledgeType.getVid());
        return "success";
    }

    /**
     * @return the isPrivate
     */
    public Integer getIsPrivate()
    {
        return isPrivate;
    }

    /**
     * @param isPrivate the isPrivate to set
     */
    public void setIsPrivate(Integer isPrivate)
    {
        this.isPrivate = isPrivate;
    }

    public KnowledgeType getKnowledgeType()
    {
        return knowledgeType;
    }

    public void setKnowledgeType(KnowledgeType knowledgeType)
    {
        this.knowledgeType = knowledgeType;
    }

    public Integer getOrderIndex()
    {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex)
    {
        this.orderIndex = orderIndex;
    }

    public Integer getVid()
    {
        return vid;
    }

    public void setVid(Integer vid)
    {
        this.vid = vid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the isOnly
     */
    public Integer getIsOnly()
    {
        return isOnly;
    }

    /**
     * @param isOnly the isOnly to set
     */
    public void setIsOnly(Integer isOnly)
    {
        this.isOnly = isOnly;
    }

    public Integer getParentId()
    {
        return parentId;
    }

    public void setParentId(Integer parentId)
    {
        this.parentId = parentId;
    }

    public boolean isContainAll()
    {
        return containAll;
    }

    public void setContainAll(boolean containAll)
    {
        this.containAll = containAll;
    }

	public Integer getColumnId() {
		return columnId;
	}

	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}

}
