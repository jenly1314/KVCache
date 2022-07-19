-keep class com.king.kvcache.* { *; }
-keep public class * extends com.king.kvcache.cache.Cache.**

#kotlin
-keep class kotlin.** { *; }
-keep class org.jetbrains.** { *; }
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
}