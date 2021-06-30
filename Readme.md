# BLOG系统
实训项目

## 用到的数据库
利用MySql建立名为`blog_db`的数据库。

### 数据库中的表
目前有五张表，没有下列的表无法使用该系统

#### `user_authority`
该表的作用在于定义用户类型。
|属性|类型|描述|
|---|---|---|
|`id`|`int`|主键，自增|
|`authority`|`varchar(20)`|非空，类型名|

#### `userInfo`
该表的作用在于储存用户信息
|属性|类型|描述|
|---|---|---|
|`id`|`int`|主键，自增|
|`username`|`varchar(20)`|非空，账户名|
|`pwd`|`varchar(20)`|非空，密码|
|`name`|`varchar(20)`|非空，昵称/显示名称|
|`authority`|`int`|外键，参照`user_authority.id`，级联更新|

#### `blog`
该表的作用在于储存博客文章的基本信息
|属性|类型|描述|
|---|---|---|
|`id`|`int`|主键，自增|
|`title`|`tinytext`|非空，博客标题|
|`summary`|`tinytext`|博客大概|
|`publish_date`|`date`|非空，博客发布时间|

#### `blog_content`
该表的作用在于储存博客文章的内容
|属性|类型|描述|
|---|---|---|
|`blog_id`|`int`|外键，参照`blog.id`，级联更新|
|`content`|`text`|博客内容|

#### `comments`
该表作用在于储存评论信息。
|属性|类型|描述|
|---|---|---|
|`id`|`int`|主键，自增，用于标识评论|
|`blog_id`|`int`|外键，参照`blog.id`，级联更新|
|`user_id`|`int`|外键，参照`userInfo.id`，级联更新|
|`content`|`tinytext`|评论内容非空|

#### `user_state`
该表作用在于储存评论信息。
|属性|类型|描述|
|---|---|---|
|`user_id`|`int`|外键，不可重复，参照`userInfo.id`，级联更新|
|`state`|`int`|用户状态，非空|

## 使用到的项目
- [marked](https://github.com/markedjs/marked)
- [highlight.js](https://highlightjs.org/)
- [Tomcat](https://tomcat.apache.org/)
- [maven](http://maven.apache.org/)
- [mysql](https://www.jetbrains.com/idea/)

使用的IDE/编辑器
- [IDEA](https://www.jetbrains.com/idea/)
- [DBeaver CE](https://dbeaver.io/)
- [vscode](https://code.visualstudio.com/)