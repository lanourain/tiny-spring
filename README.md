自己实现的一个tiny-spring，尝试实现spring的部分功能。

因为直接上手spring的源码有点头疼，且对于其继承结构、抽象方式也无法很好的理解，所以计划自己从0开始实现，然后一点点添加功能，在添加功能的过程中对框架的调整。
对应版本记录实现的功能或者是框架上的调整，会对对应版本打相应的tag。

##### v0.1
###### 功能：
1. 实现XMLBeanFactory，支持如下功能:
    1. 从XML中读取BeanDefinition配置（id，class）
        1. 解析xml元素，获取配置信息
        2. 定义BeanDefinition类，存放Definition信息
        3. 使用hashmap存放BeanDefinition信息
    2. 提供getBean方法供外部根据className获取实例
        1. 根据className从map中获取BeanDefinition，根据BeanDefinition中的信息获取bean实例
   
##### v0.2
###### 功能：
1. 通过XML property元素支持bean的string属性注入
    1. BeanDefinition中新增list propertyValues，记录需要注入的属性（name，value，暂时只支持string类型）
    2. 解析xml获取配置信息存入propertyValues
    3. 通过BeanDefinition获取bean实例的时候，通过反射将属性set进去
2. property元素支持引用其他bean的属性注入
    1. 使用BeanReference记录引用的beanName，和value做区分
    2. 在实例化bean时，使用记录的beanName通过getBean从容器中获取引用的bean实例
3. property元素支持long类型的属性支持
    1. 通过反射根据set method的参数，将propertyValue中的value转换成对应类型注入属性

##### v0.3
1. BeanFactory获取bean保证每个bean都是单例
    1. BeanDefinition中新增bean属性，用于记录对应的实例化的bean。
2. bean之间相互依赖，出现循环依赖问题解决
    1. 构造器参数的循环依赖无法解决（构造器参数还未实现）
    2. property注入（setter方式）产生的循环依赖，通过先将bean实例存入BeanDefinition中，然后再去填充bean
    属性实现。（填充属性的时候，从BeanFactory中的获取对应bean实例的引用，如果对应bean实例不存在，则创建该类实例，以此类推。依赖了单例的实现，如果非单例情况下的setter
    方式循环依赖，则无法解决）


##### 参考
[tiny-spring](https://github.com/code4craft/tiny-spring)