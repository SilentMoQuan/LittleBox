package cn.moquan.tools.same_check.info;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import java.util.Objects;
import java.util.Set;

/**
 * description
 *
 * @author MoQuan
 */
public class SameCheckFieldInfo {

    private final int modifiers;

    private final String typeName;

    private final String fieldName;

    private final Object constantValue;

    public SameCheckFieldInfo(VariableElement element) {
        this.modifiers = calculateModifiers(element);
        this.typeName = element.asType().toString();
        this.fieldName = element.getSimpleName().toString();
        this.constantValue = element.getConstantValue();
    }

    public int calculateModifiers(VariableElement element) {
        int fieldModifiers = 0;
        Set<Modifier> modifierSet = element.getModifiers();
        for (Modifier modifier : modifierSet) {
            fieldModifiers += (int) Math.pow(2, modifier.ordinal());
        }

        return fieldModifiers;
    }

    public boolean equalsFiled(SameCheckFieldInfo info) {
        return this.modifiers == info.modifiers &&
                Objects.equals(this.typeName, info.typeName) &&
                Objects.equals(this.fieldName, info.fieldName) &&
                Objects.equals(this.constantValue, info.constantValue);
    }

}
