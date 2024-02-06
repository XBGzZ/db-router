package icu.xbg.dbrouter.utils;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2024-02-06 14:15
 */
public class ClassLoadUtil {
    public static List<Class<?>> getClasses(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace('.', '/');
        try {
            // 获取类加载器
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            // 使用类加载器获取指定包及其子包下的所有资源
            Enumeration<URL> resources = classLoader.getResources(path);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                if (resource.getProtocol().equals("file")) {
                    // 如果是文件类型的资源，则调用 findClasses 方法进行类文件的查找
                    File file = new File(resource.toURI());
                    classes.addAll(findClasses(file, packageName));
                } else if (resource.getProtocol().equals("jar")) {
                    // 如果是 Jar 包类型的资源，则调用 findClassesFromJar 方法进行类文件的查找
                    classes.addAll(findClassesFromJar(resource, packageName));
                }
            }
        } catch (IOException | URISyntaxException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return classes;
    }

    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();

        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                // 如果是目录，则递归调用自身进行查找
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                // 如果是以 .class 结尾的文件，则加载类并添加到列表
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                classes.add(Class.forName(className));
            }
        }

        return classes;
    }

    private static List<Class<?>> findClassesFromJar(URL url, String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        JarURLConnection jarConnection = (JarURLConnection) url.openConnection();
        if (jarConnection != null) {
            JarFile jarFile = jarConnection.getJarFile();
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String entryName = jarEntry.getName();
                if (entryName.endsWith(".class") && entryName.startsWith(packageName.replace('.', '/'))) {
                    // 如果是以 .class 结尾的文件，并且在指定包及其子包下，则加载类并添加到列表
                    String className = entryName.substring(0, entryName.length() - 6).replace('/', '.');
                    classes.add(Class.forName(className));
                }
            }
        }
        return classes;
    }
}
