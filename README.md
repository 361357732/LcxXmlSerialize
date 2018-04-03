# LcxXmlSerialize

## Description
This is a project similar to gson and Jackson parsing JSON, aiming at serialization and deserialization of XML.

## How To Use

### * Object

``` java
public static void main(String[] args) throws Exception {
	User record = new User();
	record.setId(10);
	record.setName("lcx");
	record.setSex(true);
	record.setTime(new Date());

	String xml = XmlUtil.toXml(record);
	System.out.println(xml); // 1、print
	User user = XmlUtil.from(xml, User.class);
	System.out.println(user.toString()); // 2、print
}
```

result:<br>

1、print

``` xml
<User id="10"><name>lcx</name><sex>true</sex><time>2018-04-02 17:04:35</time></User>
```
xml format:

``` xml
<User id="10">
  <name>lcx</name>
  <sex>true</sex>
  <time>2018-04-02 17:04:35</time>
</User>
```
2、print

``` xml
User [id=10, code=null, name=lcx, sex=true, time=Mon Apr 02 17:04:35 CST 2018]
```


### List

``` java
public static void main(String[] args) throws Exception {
	User record = new User();
	record.setId(10);
	record.setName("lcx");
	record.setSex(true);
	record.setTime(new Date());

	List<User> list = new ArrayList<User>();
	list.add(record);
	list.add(record);

	String xml = XmlUtil.toXml(list);
	System.out.println(xml); // 1、print
	List<User> userList = XmlUtil.from(xml, new TypeReference<List<User>>() {
	});
	System.out.println(userList.toString()); // 2、print
}
```

result:<br>

1、print

``` xml
<ArrayList><User id="10"><name>lcx</name><sex>true</sex><time>2018-04-02 17:06:32</time></User><User id="10"><name>lcx</name><sex>true</sex><time>2018-04-02 17:06:32</time></User></ArrayList>
```
xml format:

``` xml
<ArrayList>
  <User id="10">
    <name>lcx</name>
    <sex>true</sex>
    <time>2018-04-02 17:06:32</time>
  </User>
  <User id="10">
    <name>lcx</name>
    <sex>true</sex>
    <time>2018-04-02 17:06:32</time>
  </User>
</ArrayList>
```
2、print

``` xml
[User [id=10, code=null, name=lcx, sex=true, time=Mon Apr 02 17:06:32 CST 2018], User [id=10, code=null, name=lcx, sex=true, time=Mon Apr 02 17:06:32 CST 2018]]
```


### XmlConfig

``` java
public static void main(String[] args) throws Exception {
	User record = new User();
	record.setId(10);
	record.setName("lcx");
	record.setSex(true);
	record.setTime(new Date());

	// xml config
	XmlConfig.setShowNull(true);
	XmlConfig.setTimeFormat("yyyy-MM-dd'T'HH:mm:ss");

	String xml = XmlUtil.toXml(record);
	System.out.println(xml); // 1、print
	User user = XmlUtil.from(xml, User.class);
	System.out.println(user.toString()); // 2、print
}
```

result:<br>

1、print

``` xml
<User id="10"><code/><name>lcx</name><sex>true</sex><time>2018-04-02T17:10:46</time></User>
```
xml format:

``` xml
<User id="10">
  <code/>
  <name>lcx</name>
  <sex>true</sex>
  <time>2018-04-02T17:10:46</time>
</User>
```
2、print

``` xml
User [id=10, code=, name=lcx, sex=true, time=Mon Apr 02 17:10:46 CST 2018]
```


#### User.java

``` java
public class User {

    @XmlAttribute
    private int id;

    private String code;

    private String name;

    private boolean sex;

    private Date time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", code=" + code + ", name=" + name + ", sex=" + sex + ", time=" + time + "]";
    }
}
```
