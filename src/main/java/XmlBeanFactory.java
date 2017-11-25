import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 从XML获取BeanDefinition配置，将其注册到Factory中，通过getBean方法供外部使用
 */
public class XmlBeanFactory {

    // 用来存储注册的BeanDefinition
    private Map<String, BeanDefinition> registry = new HashMap<String, BeanDefinition>();

    public XmlBeanFactory(String path) throws Exception {

        // 使用当前类的类加载器获取对应资源
        URL url = this.getClass().getClassLoader().getResource(path);

        // 连接当前资源获取输入流进行读取数据
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        InputStream inputStream = urlConnection.getInputStream();

        // 使用输入流读取xml数据，并存入到Document中
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc = docBuilder.parse(inputStream);

        // 从Document中解析元素组装bean
        Element root = doc.getDocumentElement();
        NodeList nl = root.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                processBeanDefinition(ele);
            }
        }

        inputStream.close();
    }

    // 根据xml元素组装BeanDefinition
    protected void processBeanDefinition(Element ele) {
        String name = ele.getAttribute("id");
        String className = ele.getAttribute("class");
        // 创建注册到factory中的BeanDefinition信息
        BeanDefinition beanDefinition = new BeanDefinition();
        // 处理BeanDefinition的属性
        processProperty(ele, beanDefinition);
        beanDefinition.setBeanClassName(className);
        // factory通过map存放BeanDefinition
        registry.put(name, beanDefinition);
    }

    // 通过beanname 获取bean 实例
    public Object getBean(String name) throws Exception {
        BeanDefinition beanDefinition = registry.get(name);
        if (beanDefinition == null) {
            throw new IllegalArgumentException("No bean named " + name + " is defined");
        }
        // 通过类名获取实例
        Object bean = Class.forName(beanDefinition.getBeanClassName()).newInstance();
        applyPropertyValues(bean, beanDefinition);
        return bean;
    }

    // 获取Property并记录到对应BeanDefinition中
    private void processProperty(Element ele, BeanDefinition beanDefinition) {
        NodeList propertyNode = ele.getElementsByTagName("property");
        for (int i = 0; i < propertyNode.getLength(); i++) {
            Node node = propertyNode.item(i);
            if (node instanceof Element) {
                Element propertyEle = (Element) node;
                String name = propertyEle.getAttribute("name");
                String value = propertyEle.getAttribute("value");
                if (value != null && value.length() > 0) {
                    beanDefinition.getPropertyValues().add(new PropertyValue(name, value));
                }
                else {
                    // 处理ref的情况
                    String ref = propertyEle.getAttribute("ref");
                    if (ref == null || ref.length() == 0) {
                        throw new IllegalArgumentException(
                                "Configuration problem: <property> element for property '"
                                        + name + "' must specify a ref or value");
                    }
                    // 先只是记录ref 引用的名字，后续需要的时候才进行解析
                    BeanReference beanReference = new BeanReference(ref);
                    beanDefinition.getPropertyValues().add(new PropertyValue(name, beanReference));
                }
            }
        }
    }

    // 初始化bean时，将属性注入进去
    private void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception {
        Method[] methods = bean.getClass().getMethods();
        Map<String, Method> methodMap = new HashMap<String, Method>();
        for (Method method : methods) {
            methodMap.put(method.getName(), method);
        }

        for (PropertyValue propertyValue : beanDefinition.getPropertyValues()) {
            Object value = propertyValue.getValue();

            // ref 引用其他bean的情况，则通过getBean再从容器中获取对应的bean
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getName());
            }

            // 使用反射将属性注入
            String methodName = "set" + propertyValue.getName().substring(0, 1).toUpperCase()
                    + propertyValue.getName().substring(1);

            Method method = methodMap.get(methodName);

            // 根据反射获取方法的参数类型，然后将value转换成对应类型
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes[0] == long.class) {
                value = Long.valueOf(value.toString());
            }

            method.invoke(bean, value);
        }
    }
}
