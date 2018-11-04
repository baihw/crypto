# crypto

[![Build Status](https://travis-ci.org/baihw/crypto.svg?branch=master)](https://travis-ci.org/baihw/crypto)
[![Maven Central](https://img.shields.io/maven-central/v/com.wee0/crypto.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.wee0%22%20AND%20a:%22crypto%22)
[![JDK](https://img.shields.io/badge/JDK-1.8+-green.svg)](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

加解密工具包。

## 快速上手

- **maven引用** 
  ```xml
    <dependency>
            <groupId>com.wee0</groupId>
            <artifactId>crypto</artifactId>
            <version>0.1.0</version>
    </dependency>
  ```
- **手动编译**  
   下载源码,执行`mvn clean package`。   

### 在命令行下查看加解密结果 

```sh
# 使用密钥123456加密明文hello，得到密文。
java -Dwee0.crypto.key=123456 -jar crypto-0.1.0.jar  hello

# 使用密钥123456解密密文，得到明文hello。
java -Dwee0.crypto.key=123456 -jar crypto-0.1.0.jar decrypt 05e4c7522818bcb81117f90ff8007970
```


### 编写代码

对字符串进行加解密操作。<br/>
特点：数据可进行解密还原，得到的密文长度不固定，随明文数据长度增长而增长。<br/>
适用场景：配置文件敏感属性加密，网络交互敏感数据加密。
```java
IEncryptor<String> stringEncryptor = new SimpleStringEncryptor( "123456" );
String result = stringEncryptor.encrypt( "hello" ) ;
System.out.println( "equals:" + "hello".equals( stringEncryptor.decrypt( result ) ) );
```

对字符串进行签名计算。<br/>
特点：仅校验数据是否被篡改，无法还原数据，得到的密文长度固定，由使用的算法决定，通常为32位或64位。<br/>
适用场景：随原始数据一起发放，用于校验原始数据传输过程中是否被非法篡改。<br/>
注意：不是所有的算法对同样的明文都会得到相同的签名结果，所以请尽可能使用**matches**方法进行签名校验。
```java
IDigester<String> stringDigester = new SimpleStringDigester() ;
String result = stringDigester.digest( "hello" ) ;
System.out.println( "matches:" + stringDigester.matches( "hello", result ) );
```

对键值集合进行签名计算。<br/>
特点：可选的混合密钥签名，防止网络传输中数据被恶意拦截修改后，攻击者使用相同的签名方法签名后重新发送，支持ServletRequest对象。<br/>
适用场景：数据交换，跨站点安全通信
```java
Map<String, String> map = new HashMap<>() ;
map.put( "appId", "app1" ) ;
String result = mapDigester.digest( map )
System.out.println( "matches:" + mapDigester.matches( map, result ) );
```


## 进阶操作

### 简单实施跨站点安全通信

使用本工具包内置的安全策略进行跨站点安全通信，只需少量代码即可完成。大致流程如下：<br/>
1. 为授权通信站点颁发通信密钥，如：123456。
1. 使用通信密钥对敏感数据进行加密传输。
1. 使用通信密钥对请求数据进行数字签名。     

示例代码  
```java
String secretKey = "123456" ;
IEncryptor<String> stringEncryptor = new SimpleStringEncryptor( secretKey ) ;
IDigester<Map<String, String>> mapDigester = new SimpleMapDigester() ;

// 请求发送方主要代码
Map<String, String> map = new HashMap<>() ;
map.put( "appId", "app1" ) ;
map.put( "phone", stringEncryptor.encrypt( "13xxxxxxxxx" ) ) ;
// 附加签名信息，并且使用通信密钥增强签名安全性。
map.put( "sign", mapDigester.digestWithKey( map, secretKey ) ) ;
// 其它不参与签名的正常参数
map.put( "par1", "par1Value" );
// 下边这行代码仅为演示，无法直接复制使用，请使用自己的网络请求方法替代。
sendPost( "http://www.xxx.com/xxx", map ) ;

// 请求接收方主要代码
// 过滤不参与签名计算的请求参数
Set<String> filterNames = new HashSet<>() ;
filterNames.add( "par1" ) ;
// 将request对象参数转换为基本Map对象，用于进行签名校验。
Map<String, String> data = ServletSupport.servletRequestToMap( request, filterNames, false ) ;
// 如果简单的过滤操作不满足需求，也可以使用自定义的过滤函数来灵活控制
data = ServletSupport.servletRequestToMap( request, ( name ) ->{
	// 返回true表示接受参数，返回false表示不接受参数。
	// 下边的自定义策略与上边演示的方法一样的效果。
	return return "par1".equals( name ) ? false : true ;
});
if( !mapDigester.matchesWithKey( data, data.get( "sign" ).toString(), secretKey ) )
	throw new RuntimeException( "签名校验失败！" );
// 签名校验通过，执行正常处理逻辑。
// 解密手机号码
System.out.println( "phone:" + stringEncryptor.decrypt( data.get( "phone" ).toString() ) );
```


### 定制签名策略，应用于第三方站点，如微信，支付宝... 
待补充


## 常见问题
待补充



## TODO

1. 增加其它算法支持
1. 支持选择算法实现



## 文档翻译

* [English](https://github.com/baihw/crypto/blob/master/README_en.md)



## License

[Apache2.0](https://www.apache.org/licenses/LICENSE-2.0.html)

Copyright (c) 2016-present, wee0.com
