# AndroidCommonLib
Android Common Library
* Logcat를 파일로 redirect 하는 기능

# Library 사용법
## repository 추가
* [build.gradle](https://github.com/guriguri/AndroidCommonLib/blob/master/build.gradle)
```
allprojects {
    repositories {
        jcenter()

        maven {
            url "https://jitpack.io"
        }
    }
}
```

## dependency 추가
* [app/build.gradle](https://github.com/guriguri/AndroidCommonLib/blob/master/app/build.gradle)
```
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.android.support:appcompat-v7:21.0.3'

    // for internal module
    // compile project(':common')

    // for JitPack.io repository
    compile 'com.github.guriguri:AndroidCommonLib:-SNAPSHOT'
}
```

