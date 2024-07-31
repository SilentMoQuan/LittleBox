package cn.moquan.miot.log;

import org.slf4j.Logger;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2024/3/28 17:37 </b><br />
 */
public interface SimpleLog {

    String getPrefix();

    Logger getLog();

    default void error(String format, Object... args) {
        String newFormat = getPrefix() + format;
        getLog().error(newFormat, args);
    }

    default void warn(String format, Object... args) {
        String newFormat = getPrefix() + format;
        getLog().warn(newFormat, args);
    }

    default void info(String format, Object... args) {
        String newFormat = getPrefix() + format;
        getLog().info(newFormat, args);
    }

    default void debug(String format, Object... args) {
        String newFormat = getPrefix() + format;
        getLog().debug(newFormat, args);
    }

}
