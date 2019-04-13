#include <jni.h>
#include <string>
#include <assert.h>
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_pigxposed_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

//以下是动态注册native方法的内容 start

/**
 * 定义native方法
 */
JNIEXPORT jstring JNICALL native_hello(JNIEnv *env, jclass clazz) {
    printf("hello in c native code./n");
    return env->NewStringUTF("hello world returned.");
}

// 指定要注册的类
#define JNIREG_CLASS "com/example/pigxposed/MainActivity"

// 定义一个JNINativeMethod数组，其中的成员就是Java代码中对应的native方法
static JNINativeMethod gMethods[] = {
        {"hello", "()Ljava/lang/String;", (void *) native_hello},
//        这里需要参数 一个方法签名 ,可以
//        通过Build -> Analyze APK,找到这个类  就可以看到 这个类的方法啦。
//       .method public native hello()Ljava/lang/String; 所以签名为  ()Ljava/lang/String;
};


static int registerNativeMethods(JNIEnv *env, const char *className,
                                 JNINativeMethod *gMethods, int numMethods) {
    jclass clazz;
    clazz = env->FindClass(className);
    if (clazz == NULL) {
        return JNI_FALSE;
    }
    if (env->RegisterNatives(clazz, gMethods, numMethods) < 0) {
        return JNI_FALSE;
    }
    return JNI_TRUE;
}

/***
 * 注册native方法
 */
static int registerNatives(JNIEnv *env) {
    if (!registerNativeMethods(env, JNIREG_CLASS, gMethods, sizeof(gMethods) / sizeof(gMethods[0]))) {
        return JNI_FALSE;
    }
    return JNI_TRUE;
}

/**
 * 如果要实现动态注册，这个方法一定要实现
 * 动态注册工作在这里进行
 */
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;
    jint result = -1;

    if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        return -1;
    }
    assert(env != NULL);

    if (!registerNatives(env)) { //注册
        return -1;
    }
    result = JNI_VERSION_1_4;

    return result;
}
//以下是动态注册native方法的内容 end
