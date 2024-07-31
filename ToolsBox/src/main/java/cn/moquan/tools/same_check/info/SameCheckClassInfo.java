package cn.moquan.tools.same_check.info;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * description
 *
 * @author MoQuan
 */
public class SameCheckClassInfo {

    private int fieldCount;

    private final Map<String, SameCheckFieldInfo> filedMap = new HashMap<>();

    public SameCheckClassInfo(TypeElement element) {

        for (Element enclosedElement : element.getEnclosedElements()) {
            if (enclosedElement.getKind().isField()) {
                this.fieldCount++;
                String key = enclosedElement.getSimpleName().toString();
                SameCheckFieldInfo value = new SameCheckFieldInfo((VariableElement) enclosedElement);
                filedMap.put(key, value);
            }
        }

    }

    public boolean equalsClass(SameCheckClassInfo info) {
        // 整体比较
        if (!Objects.equals(this.fieldCount, info.fieldCount)) {
            return false;
        }

        // 每个字段比较
        for (Map.Entry<String, SameCheckFieldInfo> infoEntry : this.filedMap.entrySet()) {
            String key = infoEntry.getKey();
            SameCheckFieldInfo fieldInfo = info.filedMap.getOrDefault(key, null);
            if (!Objects.nonNull(fieldInfo)) {
                return false;
            }
            if (!infoEntry.getValue().equalsFiled(fieldInfo)) {
                return false;
            }
        }

        return true;
    }

    public boolean equalsClass(TypeElement element) {
        return this.equalsClass(new SameCheckClassInfo(element));
    }
}
