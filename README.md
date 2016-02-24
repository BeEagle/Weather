# Weather
一个小的天气App,主要用于学习Retrofit+OkHttp+RxJava+Gson

##项目中使用的Key
使用的是[和风天气](http://www.heweather.com/)接口，申请后获得一个key，在weatherapp/src/main/java/cchao/org/weatherapp/api中新建一个Key.java

```java
public class Key {
    public static String KEY = "你的key";
}
```

##使用的开源项目
**[https://github.com/rey5137/material](https://github.com/rey5137/material)**

A library to bring fully animated Material Design components to pre-Lolipop Android.

**[https://github.com/google/gson](https://github.com/google/gson)**

A Java serialization library that can convert Java Objects into JSON and back.

**[https://github.com/square/okhttp](https://github.com/square/okhttp)**

An HTTP+SPDY client for Android and Java applications.

**[https://github.com/square/retrofit](https://github.com/square/retrofit)**

Type-safe HTTP client for Android and Java by Square, Inc.

**[https://github.com/ReactiveX/RxAndroid](https://github.com/ReactiveX/RxAndroid)**

RxJava bindings for Android.

###License

```
Copyright (c) 2015 shucc

Licensed under the Apache License, Version 2.0 (the "License”);
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
   
   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
