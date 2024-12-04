package tansun.creditx.bts.tools;

import com.alibaba.nacos.api.config.ConfigType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 动态修改nacos配置中心文件时，无法识别文件类型，以此类检验判断
 *
 * @author Zhazha
 * Creation date 2024/11/29
 */
public class NacosFileTool {

    // 根据配置内容判断文件类型
    public static ConfigType configType(String content) {
        if (isProperties(content)) {
            return ConfigType.PROPERTIES;
        } else if (isYaml(content)) {
            return ConfigType.YAML;
        } else {
            return ConfigType.TEXT;  // 默认类型是 TEXT
        }

        // TODO: 2024/11/29 unrealized JSON、XML、HTML、TOML type
    }

    /**
     * 判断是否为 Properties 格式
     * key=value 或 key : value。
     * 如果文件包含简单的 = 或 :，且没有复杂的嵌套结构，则为 Properties 格式。
     *
     * @param content
     * @return Boolean
     */
    private static boolean isProperties(String content) {
        if (content == null) {
            return false;
        }
        // Properties 文件通常是 key=value 格式
        // 检查文件内容是否包含 key=value 格式的键值对
        String regex = "^[\\w.]+=[^\\n]+$";
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(content);

        // 如果内容的每一行都匹配 key=value 格式，认为是 Properties 格式
        int count = 0;
        while (matcher.find()) {
            count++;
        }

        // 如果至少有一行符合该格式，并且没有复杂的 YAML 特性（如 - 或 : 后面有多个值），认为是 Properties 格式
        return count > 0 && !isYamlContent(content);
    }

    /**
     * 判断是否为 YAML 格式
     * 必须检查文件是否存在多层嵌套、列表（如 -）和结构（如缩进）。YAML 通常不使用 = 或 : 来表示键值对
     *
     * @param content
     * @return
     */
    private static boolean isYaml(String content) {
        try {
            // 检查是否包含 YAML 特有结构，如列表、缩进等
            return isYamlContent(content);
        } catch (Exception e) {
            return false; // 解析失败说明不是 YAML 格式
        }
    }

    // 判断内容是否符合 YAML 特征（例如：缩进、列表、键值对结构等）
    private static boolean isYamlContent(String content) {
        // YAML 特征包括：缩进（空格）、列表（-）、嵌套（层级结构）
        String[] lines = content.split("\n");

        boolean hasListItem = false;
        boolean hasIndentation = false;

        for (String line : lines) {
            // 检查是否包含列表项标识（-）
            if (line.trim().startsWith("-")) {
                hasListItem = true;
            }
            // 检查是否有缩进（至少两个空格）
            if (line.startsWith("  ")) {
                hasIndentation = true;
            }
            // 如果包含列表项或者缩进，就可以认为它是 YAML 格式
            if (hasListItem || hasIndentation) {
                return true;
            }
        }
        return false; // 如果没有 YAML 特有结构，则返回 false
    }
}
