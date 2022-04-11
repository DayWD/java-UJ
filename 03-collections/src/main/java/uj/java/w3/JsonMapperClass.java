package uj.java.w3;

import java.util.Map;
import java.util.List;

public class JsonMapperClass implements JsonMapper {

    @Override
    public String toJson(Map<String, ?> map) {
        StringBuilder jsonString = new StringBuilder();

        if (map == null)
            return "{}";

        unwrapMapToStringRecursively(map, jsonString);

        return jsonString.toString().replace("\"", "\\\"").replace("§", "\"");
    }

    public void unwrapMapToStringRecursively(Map<String, ?> map, StringBuilder string) {
        string.append("{");

        for (var element : map.entrySet()) {
            string.append("§").append(element.getKey()).append("§:");
            objectType(element.getValue(), string);
            string.append(",");
        }
        if (!map.isEmpty())
            string.deleteCharAt(string.length() - 1);

        string.append("}");
    }

    public void unwrapListToStringRecursively(List<?> list, StringBuilder string) {
        string.append("[");

        for (var element : list) {
            objectType(element, string);
            string.append(",");
        }
        if (!list.isEmpty())
            string.deleteCharAt(string.length() - 1);

        string.append("]");
    }

    @SuppressWarnings("unchecked")
    public void objectType(Object element, StringBuilder string) {
        if (element instanceof Map)
            unwrapMapToStringRecursively((Map<String, ?>) element, string);
        else if (element instanceof List)
            unwrapListToStringRecursively((List<?>) element, string);
        else if (element instanceof String)
            string.append("§").append(element).append("§");
        else if (element instanceof Boolean)
            string.append(element);
        else if (element instanceof Number)
            string.append(element);
    }
}