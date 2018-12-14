package cn.com.qytx.cbb.org.util;

import java.util.List;
import java.util.Map;

import cn.com.qytx.platform.org.domain.GroupInfo;

/**
 * 计算部门下人员数
 */
public class CalculateUtil {
    private int total = 0;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * 获取部门/群组对应的人员数
     * @param groupList
     */
    public void getGroupUserCount(int groupId, List<GroupInfo> groupList,
                                   Map<Integer, String> groupUserMap)
    {
        if (null != groupList && !groupList.isEmpty())
        {
            // 退出条件 不包含子部门时退出
            boolean isParent = false;
            for (GroupInfo temp : groupList)
            {
                if (temp.getParentId() == groupId)
                {
                    isParent = true;
                    break;
                }
            }

            if (!isParent)
            {
                if (null != groupUserMap.get(groupId))
                    total = total + groupUserMap.get(groupId).split(",").length;
                return;
            }

            for (GroupInfo temp1 : groupList)
            {
                // 如果部门为本身则相加
                if (temp1.getGroupId() == groupId)
                {
                    if (null != groupUserMap.get(groupId))
                        total = total + groupUserMap.get(temp1.getGroupId()).split(",").length;

                }
                // 递归处理子部门
                else if (temp1.getParentId() == groupId)
                {
                    getGroupUserCount(temp1.getGroupId(), groupList, groupUserMap);
                }
            }
        }
    }
}
