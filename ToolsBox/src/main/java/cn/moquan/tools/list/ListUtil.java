package cn.moquan.tools.list;

import java.util.Collection;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2023/7/4 16:15 </b><br />
 */
public class ListUtil {

    public static boolean haveEmptyList(Collection<?> ...listArray){

        for (Collection<?> list : listArray) {

            if (null == list || list.isEmpty()){
                return true;
            }

        }

        return false;
    }

    public static boolean allEmptyList(Collection<?> ...listArray){

        for (Collection<?> list : listArray) {

            if (null != list && !list.isEmpty()){
                return false;
            }

        }

        return true;
    }

}
