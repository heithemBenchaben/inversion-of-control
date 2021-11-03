package com.hch.ioc.core.utils;

import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.exceptions.SimpleIocException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class ContainerUtils {

    public static final String CLASS = ".class";


    public static List<URL> getResources(String resourceName) {
        try {
            return Collections.list(Thread.currentThread().getContextClassLoader().getResources(resourceName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new SimpleIocException(e);
        }
    }

    public static void fillPropertiesMap(URL url, Map<String, String> map) {
        Properties properties = loadPropertiesFromURL(url);
        properties
                .stringPropertyNames()
                .stream()
                .forEach(name -> map.put(name, properties.getProperty(name)));
    }

    public static Properties loadPropertiesFromURL(URL url) {
        try {
            Properties props = new Properties();
            props.load(getInputStreamFromUrl(url));
            return props;
        } catch (IOException e) {
            e.printStackTrace();
            throw new SimpleIocException(e);
        }
    }

    public static InputStream getInputStreamFromUrl(URL url) {
        try {
            return Files.newInputStream(Path.of(url.toURI()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new SimpleIocException(e);
        }
    }

    public static String readFileAsString(URL url) {
        try {
            return Files.readString(Path.of(url.toURI()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new SimpleIocException(e);
        }
    }

    public static Class<?> loadClazzByFileName(String fileName) {
        try {
            return Class
                    .forName(
                            fileName
                    );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SimpleIocException(e);
        }
    }

    public static Optional<Annotation> findAnnotation(Class<?> clazz, Class<?> targetAnnotationClazz) {
        return Arrays
                .stream(
                        clazz.getDeclaredAnnotations()
                )
                .filter(annotation -> annotation.annotationType() == targetAnnotationClazz)
                .findFirst();
    }

    // find all clazz annotated by a target annotation under path
    public static List<Class<?>> findAllClazzAnnotatedBy(String path, Class<?> targetAnnotationClazz) {
        return
                // find all clazz under path
                getAllClasses(path)
                        .stream()
                        // filter only clazz annotated by the target annotation
                        .filter(
                                item -> Arrays.asList(item.getDeclaredAnnotations())
                                        .stream()
                                        .filter(annotation -> annotation.annotationType() == targetAnnotationClazz)
                                        .findFirst()
                                        .isPresent()
                        )
                        .collect(Collectors.toList());
    }

    public static List<Class<?>> getAllClasses(String path) {
        try {
            return
                    // get all resources under path
                    getResources(path.replace(".", "/"))
                            // loop over resources and build new list containing Files
                            .stream()
                            .map(
                                    resource ->
                                            findClasses(new File(resource.getFile()), path)

                            )
                            .flatMap(List::stream)
                            .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new SimpleIocException(e);
        }
    }

    // find all classes under package and sub-package
    public static List<Class<?>> findClasses(File parentFile, String packageName) {
        List<Class<?>> classes = new ArrayList();
        if (!parentFile.exists()) {
            return classes;
        }
        File[] files = parentFile.listFiles();
        for (File fileItem : files) {
            if (fileItem.isDirectory()) {
                classes.addAll(findClasses(fileItem, packageName + "." + fileItem.getName()));
            } else if (fileItem.getName().endsWith(".class")) {
                try {
                    classes.add(Class.forName(packageName + '.' + fileItem.getName().substring(0, fileItem.getName().length() - 6)));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    throw new SimpleIocException(e);
                }
            }
        }
        return classes;
    }

    public static List<Field> findFieldsAnnotatedBy(List<Field> fields, Class<? extends Annotation> annotation) {
        return fields
                .stream()
                .filter(field ->
                        Arrays
                                .asList(field.getDeclaredAnnotations())
                                .stream()
                                .filter(declaredAnnotation -> declaredAnnotation.annotationType() == annotation)
                                .findFirst().isPresent()
                )
                .collect(Collectors.toList())
                ;
    }

    public static List<Method> findMethodsAnnotatedBy(List<Method> methods, Class<? extends Annotation> annotation) {
        return methods
                .stream()
                .filter(method ->
                        Arrays
                                .asList(method.getDeclaredAnnotations())
                                .stream()
                                .filter(declaredAnnotation -> declaredAnnotation.annotationType() == annotation)
                                .findFirst().isPresent()
                ).collect(Collectors.toList())
                ;
    }

    public static IocScanDefinition findIocScanDefinitionByType(String name, Map<String, IocScanDefinition> iocScanDefinitionMap) {
        return iocScanDefinitionMap
                .values()
                .stream()
                .filter(iocScanDefinition ->
                        iocScanDefinition.getTypes().stream().filter(type ->
                                type.getName().equals(name)
                        ).findFirst().isPresent()
                )
                .findFirst()
                .orElseThrow(() -> new SimpleIocException(String.format("No Bean match %s", name)));
    }
}
