/**
 * 用于记录Property中ref的属性注入，引用别的bean信息
 */
public class BeanReference {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BeanReference(String name) {
        this.name = name;
    }
}
