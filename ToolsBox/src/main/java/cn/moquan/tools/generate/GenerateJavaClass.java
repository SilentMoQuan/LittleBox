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
public class GenerateJavaClass {

    private static final String content = "" +
            "package com.xkai.user.feature.growth_track.generator;\r\n" +
            "\r\n" +
            "import com.xkai.user.feature.growth_track.StudentGrowthTrackMessageGenerator;\r\n" +
            "import org.slf4j.Logger;\r\n" +
            "import org.slf4j.LoggerFactory;\r\n" +
            "\r\n" +
            "/**\r\n" +
            " * describe\r\n" +
            " * <br />\r\n" +
            " *\r\n" +
            " * @author :<b> wyh </b><br />\r\n" +
            " * @date :<b> 2024/10/24 15:46 </b><br />\r\n" +
            " */\r\n" +
            "public class %className% implements StudentGrowthTrackMessageGenerator {\r\n" +
            "\r\n" +
            "    private static final Logger logger = LoggerFactory.getLogger(%className%.class);\r\n" +
            "\r\n" +
            "    @Override\r\n" +
            "    public String generate(Object... args) {\r\n" +
            "        return null;\r\n" +
            "    }\r\n" +
            "\r\n" +
            "}\r\n"
          ;

    private static final String saveFolder = "F:\\generateFileFolder\\";

    public static void generate(String className) throws IOException {

        File file = new File(saveFolder);
        if (!file.exists()) {
            file.mkdir();
        }

        file = new File(saveFolder + className + ".java");

        FileWriter writer = new FileWriter(file);

        writer.write(content.replaceAll("%className%", className));

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

        if (!interfacesName.isEmpty()) {
            interfacesName = " implements " + interfacesName;
        } else {
            interfacesName = " ";
        }

        writer.write("public enum " + className + interfacesName + "{ ");
        lineFeed(writer);
    }

    public static void main(String[] args) throws IOException {

        List<String> list = Arrays.asList(
                "CourseStartMessageGenerator",
                "CourseEndMessageGenerator",
                "CollectCourseMessageGenerator",
                "CollectResourceMessageGenerator",
                "LikeCourseMessageGenerator",
                "LikeResourceMessageGenerator",
                "FollowTeacherMessageGenerator",
                "JoinQAMessageGenerator",
                "CommentCourseMessageGenerator",
                "CommentResourceMessageGenerator",
                "LearnResourceMessageGenerator",
                "LearnVideoResourceMessageGenerator",
                "PracticeObjectMessageGenerator",
                "ReservationDeviceMessageGenerator",
                "JoinPracticeExamMessageGenerator"
        );

        list.forEach(name -> {
            try {
                generate(name);
            } catch (IOException e) {
                //
            }
        });

    }


}
