package com.ruan.yuanyuan.spring.beanFactory;

import com.ruan.yuanyuan.annoation.RAutoWried;
import com.ruan.yuanyuan.annoation.RController;
import com.ruan.yuanyuan.annoation.RService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: ruanyuanyuan
 * @Date: 2019-09-27
 * @Time: 14:54
 * @version:1.0
 * @Description: 默认BeanFactory
 * spring 解决依赖 1、看属性上是否有AutoWare注解如果有拿到属性名调用doGetBean创建依赖Bean
 */
public class RDefaultListableBeanFactory implements RBeanFactory {

    private String serializationId;

    public final Map<String, RBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
    private volatile List<String> beanDefinitionNames = new ArrayList<>(256);
    private final Set<RBeanDefinition> rBeanDefinitions = new HashSet<>(256);
    private Map<String, Object> singletonObjects = new HashMap<>(256);

    /**
     * 注册Bean的定义信息
     *
     * @param beanName
     * @param beanDefinition
     */
    public void registerBeanDefinition(String beanName, RBeanDefinition beanDefinition) {
        if (!StringUtils.isEmpty(beanName)) {
            RBeanDefinition extBeanDefinition = beanDefinitionMap.get(beanName);
            if (null == extBeanDefinition) {
                beanDefinitionMap.put(beanName, beanDefinition);
                beanDefinitionNames.add(beanName);
            }
        }
    }

    /**
     * 扫描ComponentScan设置目录下的所有Bean(Spring 扫描的是所有的单实例Bean,非单例Bean是直接new)
     */
    public void preInstantiateSingletons() {
        for (String beanName : beanDefinitionNames) {
            RBeanDefinition rBeanDefinition = beanDefinitionMap.get(beanName);
            Class clazz = rBeanDefinition.getBeanClass();
            if (clazz.isAnnotationPresent(ComponentScan.class)) {
                ComponentScan componentScan = (ComponentScan) clazz.getAnnotation(ComponentScan.class);
                String[] component = componentScan.basePackages();
                String[] value = componentScan.value();
                List<String> basePackage = new ArrayList<>();
                basePackage.addAll(Arrays.asList(component));
                basePackage.addAll(Arrays.asList(value));
                //获取配置文件上@ComponentScan注解的basePackages和value的扫描路径值
                for (String packageName : basePackage) {
                    //根据文件路径扫描下面的类，并进行反射封装为BeanDefinition
                    Set<RBeanDefinition> rBeanDefinitions = readClass(packageName);
                    for (RBeanDefinition rBeanDefinition1 : rBeanDefinitions) {
                        String name = rBeanDefinition1.getName();
                        RBeanDefinition exBeanDefinition = beanDefinitionMap.get(name);
                        if (null == exBeanDefinition) {
                            beanDefinitionMap.put(name, rBeanDefinition1);
                        }
                    }
                }
            }
        }

        //属性注入
        try {
            populateBean();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化的时候不能再BeanDefiniton容器中，因为后面需要获取Bean的示例
     *
     * @param packageName
     * @return
     */
    private Set<RBeanDefinition> readClass(String packageName) {
        //将包名转换为文件名
        URL url = this.getClass().getClassLoader().getResource(packageName.replaceAll("\\.", "/") + "/");
        File dir = new File(url.getFile());
        RBeanDefinition rBeanDefinition = null;
        for (File file : dir.listFiles()) {
            /**
             * 如果当前是文件夹则递归继续查询文件
             * 否则将查找的文件保存到list中
             */
            if (file.isDirectory()) {
                readClass(packageName + "." + file.getName());
            } else {
                try {
                    String className = packageName + "." + file.getName().replace(".class", "");
                    rBeanDefinition = new RBeanDefinition();
                    Class clazz = Class.forName(className);
                    if (clazz.isAnnotationPresent(RService.class)) {
                        rBeanDefinition.setBeanClass(clazz);
                        rBeanDefinition.setName(com.ruan.yuanyuan.util.StringUtils.loverFirstChar(clazz.getSimpleName()));
                        rBeanDefinitions.add(rBeanDefinition);

                        Class[] interFaces = clazz.getInterfaces();
                        for (Class c : interFaces) {
                            RBeanDefinition rBeanDefinition1 = new RBeanDefinition();
                            rBeanDefinition1.setBeanClass(clazz);
                            rBeanDefinition1.setName(com.ruan.yuanyuan.util.StringUtils.loverFirstChar(c.getSimpleName()));
                            rBeanDefinitions.add(rBeanDefinition1);

                        }
                    }
                    if (clazz.isAnnotationPresent(Configuration.class)) {
                        rBeanDefinition.setBeanClass(clazz);
                        rBeanDefinition.setName(com.ruan.yuanyuan.util.StringUtils.loverFirstChar(clazz.getSimpleName()));
                        rBeanDefinitions.add(rBeanDefinition);
                    }
                    if (clazz.isAnnotationPresent(RController.class)) {
                        rBeanDefinition.setBeanClass(clazz);
                        rBeanDefinition.setName(com.ruan.yuanyuan.util.StringUtils.loverFirstChar(clazz.getSimpleName()));
                        rBeanDefinitions.add(rBeanDefinition);
                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
        return rBeanDefinitions;
    }

    /**
     * 属性注入
     */
    private void populateBean() throws IllegalAccessException, InstantiationException {
        if (!beanDefinitionMap.isEmpty()) {
            for (Map.Entry<String, RBeanDefinition> bean : beanDefinitionMap.entrySet()) {
                //取出所有的属性
                Field[] fields = bean.getValue().getBeanClass().getDeclaredFields();
                Object obj = bean.getValue().getBeanClass().newInstance();
                for (Field field : fields) {
                    if (!field.isAnnotationPresent(RAutoWried.class)) {//如果没有这个属性则不进行属性注入
                        continue;
                    }
                    String name = field.getName();
                    field.setAccessible(true);
                    field.set(obj, beanDefinitionMap.get(name).getBeanClass().newInstance());
                }
                singletonObjects.put(bean.getKey(), obj);
            }
        }
    }

    public String getSerializationId() {
        return serializationId;
    }

    public void setSerializationId(String serializationId) {
        this.serializationId = serializationId;
    }


    @Override
    public Object getBean(String name) {
        Object obj = singletonObjects.get(name);
        return obj;
    }


    @Override
    public <T> T getBean(Class<T> clazz) {
        String name = com.ruan.yuanyuan.util.StringUtils.loverFirstChar(clazz.getSimpleName());
        T obj = (T) singletonObjects.get(name);
        return obj;
    }
}
