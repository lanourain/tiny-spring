/**
 * 记录bean的属性注入信息
 */
public class PropertyValue {

    private String name;

    /**
     * value = BeanReference类型时，表示是ref的属性，引用别的bean
     * 其他类型则是非bean的value
     */
    private Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
