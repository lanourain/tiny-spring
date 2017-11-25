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
    
    


##### 参考
[tiny-spring](https://github.com/code4craft/tiny-spring)