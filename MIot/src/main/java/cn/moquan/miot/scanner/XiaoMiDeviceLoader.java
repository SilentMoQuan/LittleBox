package cn.moquan.miot.scanner;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Constructor;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/25 16:14 </b><br />
 */
public class XiaoMiDeviceLoader {

    private static final Logger logger = LoggerFactory.getLogger(XiaoMiDeviceLoader.class);

    private static final ClassLoader CLASS_LOADER = XiaoMiDeviceLoader.class.getClassLoader();

    public static void loadXiaoMiDevice(String packagePath) {

        if (StrUtil.isEmpty(packagePath)){
            logger.error("加载小米设备类失败, packagePath 为空.");
            return;
        }

        File file = new File(packagePath);
        if (!file.exists() || !file.isDirectory()){
            logger.error("加载小米设备类失败, packagePath 不存在或不是一个包地址.");
            return;
        }

        File[] files = file.listFiles();

        searchAndLoadClass(files);
    }

    private static void searchAndLoadClass(File[] files){

        if (null == files || 0 == files.length){
            return;
        }

        for (File file : files) {

            if (file.exists() && file.isDirectory()){
                searchAndLoadClass(file.listFiles());
                continue;
            }

            if (!isClass(file)){
                continue;
            }

            String classPath = file.getAbsolutePath();
            classPath = classPath.substring(classPath.lastIndexOf(File.separatorChar + "java" + File.separatorChar) + 6, classPath.lastIndexOf('.'));
            String className = classPath.replace(File.separatorChar, '.');

            try {
                Class<?> clazz = CLASS_LOADER.loadClass(className);
                Constructor<?> constructor = clazz.getDeclaredConstructor(Object.class);
                Object o = constructor.newInstance(new Object());
                System.out.println(o.getClass());
            } catch (Exception e) {
                // nothing to do....
                System.out.println(e.getMessage());
            }

        }

    }

    private static boolean isClass(File file) {
        return null != file && file.exists() && !file.isDirectory() && (file.getName().endsWith(".class") || file.getName().endsWith(".java"));
    }

}
