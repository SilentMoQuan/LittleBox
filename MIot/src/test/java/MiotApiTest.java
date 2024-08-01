import cn.moquan.miot.tool.StringUtil;
import tool.MiotUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/31 14:39 </b><br />
 */
public class MiotApiTest {

    public static void main(String[] args) {

        List<Supplier<String>> executionList = new ArrayList<>();
//        executionList.add(() -> MiotUtil.getAPI().getDeviceAuth("731823722"));
//        executionList.add(() -> MiotUtil.getAPI().getDeviceAuth("1077180363"));
//        executionList.add(() -> MiotUtil.getAPI().deviceInfo("731823722"));
        executionList.add(() -> MiotUtil.getAPI().deviceList());

        String format = StringUtil.toStringFormat(executionList.parallelStream()
                .map(Supplier::get)
                .collect(Collectors.toList()));

        System.out.println("\n\n\n\n\n\n\n");
        System.out.println(format);

    }


}
