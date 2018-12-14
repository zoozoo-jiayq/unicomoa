package cn.com.qytx.cbb.jbpmApp.domain;

import java.util.Comparator;

/**
 * User:黄普友
 * Date: 13-7-5
 * Time: 上午10:04
 */
public class ComparatorAdviceHistory  implements Comparator {
    public int compare(Object arg0, Object arg1)
    {
        AdviceHistory history1=(AdviceHistory)arg0;
        AdviceHistory history2=(AdviceHistory)arg1;
        int flag=history1.getCreateTime().compareTo(history2.getCreateTime());
        return flag;
    }
}
