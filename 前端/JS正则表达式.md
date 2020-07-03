[JS正则表达式](https://www.cnblogs.com/lnlvinso/p/10467941.html)
### 正则表达式的创建方式
- 字面量创建方式
- 实例创建方式
```javascript
var reg = /pattern/flags
    // 字面量创建方式
    var reg = new RegExp(pattern,flags);
    //实例创建方式
    
    pattern:正则表达式  
    flags:标识(修饰符)
    标识主要包括：
    1. i 忽略大小写匹配
    2. m 多行匹配，即在到达一行文本末尾时还会继续寻常下一行中是否与正则匹配的项
    3. g 全局匹配 模式应用于所有字符串，而非在找到第一个匹配项时停止
```
    
###### 字面量创建方式和构造函数创建方式的区别
- 字面量创建方式不能进行字符串拼接，实例创建方式可以
```javascript
    var regParam = 'cm';
    var reg1 = new RegExp(regParam+'1');
    var reg2 = /regParam/;
    console.log(reg1);  //   /cm1/
    console.log(reg2);  //  /regParam/
```
- 字面量创建方式特殊含义的字符不需要转义，实例创建方式需要转义
```
    var reg1 = new RegExp('\d');  //    /d/ 
    var reg2 = new RegExp('\\d')  //   /\d/
    var reg3 = /\d/;              //  /\d/
```
#### 元字符
- 代表特殊含义的元字符
```javascript
    \d : 0-9之间的任意一个数字  \d只占一个位置
    \w : 数字，字母 ，下划线 0-9 a-z A-Z _
    \s : 空格或者空白等
    \D : 除了\d
    \W : 除了\w
    \S : 除了\s
     . : 除了\n之外的任意一个字符
     \ : 转义字符
     | : 或者
    () : 分组
    \n : 匹配换行符
    \b : 匹配边界 字符串的开头和结尾 空格的两边都是边界 => 不占用字符串位数
     ^ : 限定开始位置 => 本身不占位置
     $ : 限定结束位置 => 本身不占位置
    [a-z] : 任意字母 []中的表示任意一个都可以
    [^a-z] : 非字母 []中^代表除了
    [abc] : abc三个字母中的任何一个 [^abc]除了这三个字母中的任何一个字符
```
- 代表次数的量词元字符
```
    * : 0到多个
    + : 1到多个
    ? : 0次或1次 可有可无
    {n} : 正好n次；
    {n,} : n到多次
    {n,m} : n次到m次
```

> 量词出现在元字符后面 如\d+，限定出现在前面的元字符的次数
```
    var str = '1223334444';
    var reg = /\d{2}/g;
    var res = str.match(reg);
    console.log(res)  //["12", "23", "33", "44", "44"]
    
    var str ='  我是空格君  ';
    var reg = /^\s+|\s+$/g; //匹配开头结尾空格
    var res = str.replace(reg,'');
    console.log('('+res+')')  //(我是空格君)
```

