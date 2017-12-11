# Room Persistence Library
Building database with Room Persistence Library and test


![@developer.android.com](https://cdn-images-1.medium.com/max/800/1*z1YJWQ45ADa5xAV9IdkOkw.png)

-------------------

[TOC]


## Entity

#### @Entity
- 使用注解之外的另一种定义主键的方式

```java
​@Entity(primaryKeys = {"firstName", "lastName"})
```
- 为列添加索引

```java
@Entity(indices = {@Index("name"),@Index(value = {"last_name", "address"})})
```
- 定义外键

```java
​@Entity(foreignKeys = @ForeignKey(entity = User.class,
                                  parentColumns = "id",
                                  childColumns = "user_id",
                                  onDelete = CASCADE))
```

- 自定义表名，默认使用类名为表名

```java
@Entity(tableName = "users")
```

####  @PrimaryKey
- 定义主键，并设置是否自动增长

```java
​@PrimaryKey(autoGenerate = true)
```

#### @ColumnInfo

- 自定义数据库表结构中该字段的列名

```java
@ColumnInfo(name = "library_name")
```
####  @Ignore
- 用来标记不需要持久化的字段​

#### @Embedded
- 用来处理model嵌套的情况，如Library 中包含 Address​​

#### @ForeignKey

- 为model添加外键，建立对象之间的所属关系，也可以通过`@Relation`来实现添加`onDelete = CASCADE`可以在进行级联删除，简单讲就是，如果删除了某条library数据，那么与之关联的category数据和与category数据关联的book数据，都会被删除

## Dao

#### @Dao
- 标注Entity对应的Dao类（接口），Room会为它生成实现类

#### @Insert

- 被标注的方法只能返回 `void，long，Long，long[]，Long[]或者List<Long>`

```java
@Insert(OnConflict=REPLACE)
```
#### @Update
- 被标注的方法只能返回void，int

#### @Delete
- 被标注的方法只能返回void，int

#### @Query
@Query注解是DAO类中最主要的注解，被用来执行数据库的读写操作。每一个被标注的方法都会在`编译时`被检查，如果查询方法存在语法错误或者数据库不存在该表，Room会给出对应的编译错误。​
- 简单查询

```java
@Dao
public interface LibraryDao {
    @Query("SELECT * FROM library")
    List<Library> query();
}
```

- 带参数查询

```java
​@Dao
public interface LibraryDao {
    @Query("SELECT * FROM library WHERE library_name = :name")
    Library query(String name);
}
```

- 只返回某些列
​

```java
public class LibraryAddressName {
    @ColumnInfo(name = "library_name")
    public String libraryName;
    @ColumnInfo(name = "city")
    public String city;
}
@Dao
public interface LibraryDao {
    @Query("SELECT library_name,city FROM library")
    List<LibraryAddressName> queryLibraryAddressName();
}
```

- 带一组参数

```java
@Dao
public interface LibraryDao {
    @Query("SELECT * FROM library WHERE city IN (:cityList)")
    List<Library> queryByCityName(List<String> cityList);
}
```

- 返回LiveData形式的结果

```java
@Dao
public interface LibraryDao {
    @Query("SELECT * FROM library")
    LiveData<List<Library>> queryReturnLiveData();
}
```

- 返回Flowable形式的结果

```java
@Dao
public interface LibraryDao {
    @Query("SELECT * FROM library")
    Flowable<List<Library>> queryReturnFlowable();
}
```

- 返回Cursor（不推荐）

```java
@Dao
public interface LibraryDao {
    @Query("SELECT * FROM library")
    Cursor queryReturnCursor();
}
```

- 多表查询

```java
@Dao
public interface LibraryDao {
    @Query("SELECT * FROM library "
            + "INNER JOIN category ON category.library_id = library.id "
            + "INNER JOIN book ON book.category_id = category.id "
            + "WHERE book.name LIKE :bookName")
    Library findLibraryByBookName(String bookName);
}
```

## Database
#### @Database

- 定义数据库中包含的表，数据库版本

```java
@Database(entities = {User.java}, version = 1)​
```

#### @TypeConverter
将Entity中字段类型进行转换后再持久化，可以选择范围，文档说明如下：
- If you put it on a Database, all Daos and Entities in that database will be able to use it.
- If you put it on a Dao, all methods in the Dao will be able to use it.
- If you put it on an Entity, all fields of the Entity will be able to use it.
- If you put it on a POJO, all fields of the POJO will be able to use it.
- If you put it on an Entity field, only that field will be able to use it.
- If you put it on a Dao method, all parameters of the method will be able to use it.
- If you put it on a Dao method parameter, just that field will be able to use it.
​
## POJO

#### @Relation
用于多表联查，Room会将查询结果中的数据对应到Pojo实例。

```java
@Dao
public interface LibraryDao {
    @Query("SELECT * FROM library")
    List<LibraryCategoryBook> queryByRelation();
}
public class LibraryCategoryBook {
    @Embedded
    public Library library;
    @Relation(parentColumn = "id", entityColumn = "library_id", entity = Category.class)
    public List<CategoryBook> categoryList;
    public static class CategoryBook {
        @Embedded
        public Category category;
        @Relation(parentColumn = "id", entityColumn = "category_id")
        public List<Book> bookList;
    }
}
```

## 
