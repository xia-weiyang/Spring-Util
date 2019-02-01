# SpringUtil

Provide some basic methods and functions in Spring Boot.

## Setup

- Add it in your root build.gradle at the end of repositories

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://www.jitpack.io' }
    }
}
```

- Add the dependency
```gradle
dependencies {
        compile 'com.github.otjiushig:SpringUtil:lastVersion'
}
```
see [lastVersion](https://github.com/otjiushig/SpringUtil/releases)


- Add `scanBasePackages = {"com.jiushig"}` param in your `@SpringBootApplication` annotation. 
```
@SpringBootApplication(scanBasePackages = {"com.jiushig"})
public class YourApplication {
    public static void main(String[] args) {
        SpringApplication.run(YourApplication.class, args);
    }
}
```
