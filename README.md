## hog

Java JDBC library to return json style data from Database, compatible with Java 5 and upward.

## Usage

_This library is in Alpha. Expect breaking changes._

### Maven Coordinates

hog is not on any public Maven repo yet, so you should run `mvn clean install` to install in the local repo.

Use the following Maven coordinates to include in your project.

```xml
  <dependencies>
    <dependency>
      <groupId>com.sougata</groupId>
      <artifactId>hog</artifactId>
      <version>0.2.0-SNAPSHOT</version>
    </dependency>
  </dependencies>
```

## Example
### Interface-based API

The following example shows the usage of interface based API.

```java
public class HogExample{
    private DataSource ds;

    public HogExample() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUsername("root");
        ds.setPassword("");
        ds.setUrl("jdbc:mysql://localhost:3306/mydb");
        this.ds = ds;
    }

    public void executeSQL() {

        DBHandler _handler = DBHandlerFactory.open(ds);
        _handler.execute("INSERT INTO emp(id,name,salary) values(4,'Sougata',300)");

    }
    public void executeCreateTable() {
        DBHandler _handler = DBHandlerFactory.open(ds);
        _handler.execute("CREATE TABLE dept(name varchar(20));");
    }

    public void executeSQLWithParameter() {

        DBHandler _handler = DBHandlerFactory.open(ds);
        _handler.execute("INSERT INTO emp(id,name,salary) values(?,?,?)", new Integer(5), "Sougata", new Integer(340));

    }

    public void retrieve() {

        DBHandler _handler = DBHandlerFactory.open(ds);
        String jsonValue = _handler.retrieve("Select id,name,salary from emp");
        

    }

    public void retrieveWithParameter() {

        DBHandler _handler = DBHandlerFactory.open(ds);
        String jsonValue = _handler.retrieve("Select * from emp where id=?", new Integer(1));
        
    }

    public void updateWithParameter() {
        DBHandler _handler = DBHandlerFactory.open(ds);
        _handler.execute("Update emp set dob=? where id=?", new java.util.Date(), new Integer(1));

    }

    

    public void insert() {
        DBHandler _handler = DBHandlerFactory.open(ds);
        _handler.insert("INSERT INTO dept(name) values(?)", "IT");
    }

    public void update() {
        DBHandler _handler = DBHandlerFactory.open(ds);
        _handler.insert("update emp set dob=? where id=?", new java.util.Date(), new Integer(2));
    }


}


```

## License

Copyright © 2014 Sougata Bhattacharya

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
