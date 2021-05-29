
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt
-keep,includedescriptorclasses class com.example.harajtask.data.parser.model.**$$serializer { *; }
-keep,includedescriptorclasses class com.example.harajtask.data.local.model.**$$serializer { *; }
-keepclassmembers class com.example.harajtask.data.parser.model.** {
    *** Companion;
}
-keepclassmembers class com.example.harajtask.data.local.model.** {
    *** Companion;
}
-keepclasseswithmembers class com.example.harajtask.data.local.model.** {
    kotlinx.serialization.KSerializer serializer(...);
}
-keepclasseswithmembers class com.example.harajtask.data.parser.model.** {
    kotlinx.serialization.KSerializer serializer(...);
}
