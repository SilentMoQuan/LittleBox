package cn.moquan.tools.date;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2023/7/4 11:44 </b><br />
 */
public class DateUtil {

    public static Date localDateTimeToDate(LocalDateTime localDateTime){

        if (null == localDateTime){
            return null;
        }

        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static LocalDateTime dateToLocalDateTime(Date date){

        if (null == date){
            return null;
        }

        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
