# SpringUtil

Provide some basic methods and functions in Spring Boot.

## Setup

#### Use github

- Add it in your root build.gradle at the end of repositories

```gradle
allprojects {
    repositories {
        ...
        maven {
        url = uri("https://maven.pkg.github.com/xia-weiyang/SpringUtil")
        credentials {
            username = "你的github账号"
            password = "你的github token"
        }
    }
}
```

- Add the dependency
```gradle
dependencies {
     implementation 'com.jiushig:spring-util:lastVersion'
}
```
see [lastVersion](https://github.com/xia-weiyang/SpringUtil/releases)

#### Use jitpack

- Add it in your root build.gradle at the end of repositories

```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

- Add the dependency
```
dependencies {
     implementation 'com.github.xia-weiyang:SpringUtil:lastVersion'
}
```
see [lastVersion](https://github.com/xia-weiyang/SpringUtil/packages)

## Start
- Add `scanBasePackages = {"com.jiushig"}` param in your `@SpringBootApplication` annotation. 
```
@SpringBootApplication(scanBasePackages = {"com.jiushig"})
public class YourApplication {
    public static void main(String[] args) {
        SpringApplication.run(YourApplication.class, args);
    }
}
```
