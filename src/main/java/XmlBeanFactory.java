import java.io.InputStream;
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
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClassName(className);
        registry.put(name, beanDefinition);
    }

    // 通过beanname 获取bean 实例
    public Object getBean(String name) throws Exception {
        BeanDefinition beanDefinition = registry.get(name);
        if (beanDefinition == null) {
            throw new IllegalArgumentException("No bean named " + name + " is defined");
        }
        return Class.forName(beanDefinition.getBeanClassName()).newInstance();
    }
}
