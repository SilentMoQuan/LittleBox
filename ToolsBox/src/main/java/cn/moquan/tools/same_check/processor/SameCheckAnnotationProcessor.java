package cn.moquan.tools.same_check.processor;

import cn.moquan.tools.same_check.annotation.SameCheck;
import cn.moquan.tools.same_check.info.SameCheckClassInfo;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * description
 *
 * @author MoQuan
 */
public class SameCheckAnnotationProcessor extends AbstractProcessor {

    private final HashMap<String, SameCheckClassInfo> GROUP_MODEL_MAP = new HashMap<>();

    Messager messager;
    Elements elementUtils;
    Types typeUtils;

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>(1);
        set.add(SameCheck.class.getCanonicalName());
        return set;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
        typeUtils = processingEnv.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (TypeElement annotation : annotations) {

            Set<? extends Element> annotatedElementSet = roundEnv.getElementsAnnotatedWith(annotation);

            for (Element element : annotatedElementSet) {

                if (Objects.requireNonNull(element.getKind()) == ElementKind.CLASS) {
                    processClass((TypeElement) element);
                }

            }

        }

        return false;
    }

    private void processClass(TypeElement element) {

        SameCheck annotation = element.getAnnotation(SameCheck.class);
        if (Objects.isNull(annotation)) {
            return;
        }

        String groupKey = annotation.value();
        SameCheckClassInfo model = GROUP_MODEL_MAP.getOrDefault(groupKey, null);
        if (Objects.isNull(model)) {
            GROUP_MODEL_MAP.put(groupKey, new SameCheckClassInfo(element));
            return;
        }

        if (!model.equalsClass(element)) {
            messager.printMessage(Diagnostic.Kind.ERROR, "[ " + groupKey + " ] 校验组出现类属性不一致");
        }

    }

}
