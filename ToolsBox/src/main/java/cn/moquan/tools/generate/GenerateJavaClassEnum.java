package cn.moquan.tools.generate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/9/29 11:36 </b><br />
 */
public class GenerateJavaClassEnum {

    private static final String packageName = "cn.moquan.tools.event.impl";

    private static final String headerNote = "" +
            "/**\n" +
            " * describe\n" +
            " * <br />\n" +
            " *\n" +
            " * @author :<b> wyh </b><br />\n" +
            " * @date :<b> 2024/9/29 11:36 </b><br />\n" +
            " */";

    private static final String className = "BaseEnumTest";

    private static final List<String> interfaces = Arrays.asList("java.util.stream.Collectors");

    private static final String saveFolder = "F:\\generateFileFolder\\";

    public static void generate() throws IOException {

        File file = new File(saveFolder);
        if (!file.exists()) {
            file.mkdir();
        }

        file = new File(saveFolder + className + ".java");

        FileWriter writer = new FileWriter(file);

        writer.write("package " + packageName + ";");
        lineFeed(writer);

        interfaces.forEach(importClassName -> {
            try {
                importClass(writer, importClassName);
            } catch (IOException e) {
                //
            }
        });

        writer.write(headerNote);

        writeClassName(writer, className, interfaces);

        lineFeed(writer);
        lineFeed(writer);
        lineFeed(writer);
        lineFeed(writer);

        writer.write("  ;");

        writer.write("}");

        writer.flush();

        writer.close();
    }

    private static void lineFeed(FileWriter writer) throws IOException {
        writer.write("\r\n");
    }

    private static void importClass(FileWriter writer, String className) throws IOException {
        writer.write("import " + className + ";");
        lineFeed(writer);
    }

    private static void writeClassName(FileWriter writer, String className, List<String> interfaces) throws IOException {

        String interfacesName = interfaces.stream()
                .map(interfaceName -> interfaceName.substring(interfaceName.lastIndexOf(".") + 1))
                .collect(Collectors.joining(", "));

        if (!interfacesName.isEmpty()){
            interfacesName = " implements " + interfacesName;
        }else {
            interfacesName = " ";
        }

        writer.write("public enum " + className + interfacesName + "{ ");
        lineFeed(writer);
    }

    public static void main(String[] args) throws IOException {
        generate();
    }

}
