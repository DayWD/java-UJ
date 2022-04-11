package uj.java.annotations;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes("uj.java.annotations.MyComparable")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class MyProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            annotatedElements.forEach(this::processElement);
        }
        return true;
    }

    private void processElement(Element e) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing " + e);
        TypeElement clazz = (TypeElement) e;
        String className = clazz.getQualifiedName().toString();
        try {
            JavaFileObject file = processingEnv.getFiler().createSourceFile(className + "Comparator");
            String packageName = packageName(className);
            try (PrintWriter out = new PrintWriter(file.openWriter())) {
                if (packageName != null) {
                    out.write("package " + packageName + ";\n");
                }
                out.write("public class " + clazz.getSimpleName() + "Comparator {\n");

                buildCompareFunction(clazz, out);
                out.write("}");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void buildCompareFunction(TypeElement clazz, PrintWriter out) {
        List<? extends Element> elements = getSortedElements(clazz);

        out.write("public int compare(" + clazz.getSimpleName() + " data1, " + clazz.getSimpleName() + " data2) {\n");
        out.write("int result;\n");
        for (Element element : elements) {
            String type = processingEnv.getTypeUtils().boxedClass(processingEnv.getTypeUtils().getPrimitiveType(element.asType().getKind())).getSimpleName().toString();
            String typeField = element.getSimpleName().toString();

            out.write("result = " + type + ".compare(data1." + typeField + ", data2." + typeField + ");\n");
            out.write("if (result != 0) return result;\n");

        }
        out.write("return result;\n");
        out.write("}\n");
    }


    private List<? extends Element> getSortedElements(TypeElement type) {
        return type.getEnclosedElements().stream()
                .filter(x -> x.asType().getKind().isPrimitive() && !x.getModifiers().contains(Modifier.PRIVATE))
                .sorted(MyProcessor::priorityComparator).toList();
    }

    private static int priorityComparator(Element x, Element y) {
        ComparePriority anno1 = x.getAnnotation(ComparePriority.class);
        ComparePriority anno2 = y.getAnnotation(ComparePriority.class);

        if (anno1 == null && anno2 == null) return 0;
        else if (anno1 == null) return 1;
        else if (anno2 == null) return -1;
        else return anno1.value() - anno2.value();
    }

    private String packageName(String className) {
        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }
        return packageName;
    }
}
