package com.example.pigxposed;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import java.io.File;
import java.lang.invoke.MutableCallSite;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipOutputStream;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import kotlin.jvm.functions.Function1;

import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;
import static de.robv.android.xposed.XposedHelpers.findField;
import static de.robv.android.xposed.XposedHelpers.getBooleanField;
import static de.robv.android.xposed.XposedHelpers.getObjectField;

public class MainHook implements IXposedHookLoadPackage {
    int i = 0;

    //@Override
//public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
//    XposedBridge.log("Loaded Test app____________________________: " + lpparam.packageName);
//}
    public int totalWidth;
    public int totalHeight;
    public  int index = 0;
    public  int vCN = 0;
    public  int bQU = 0;

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        if(loadPackageParam.packageName.equals("com.font")){
            HideModule.hideModule(loadPackageParam);
          findAndHookMethod("com.font.function.writing.CreateCopybookEditActivity", loadPackageParam.classLoader, "initData",
                  Bundle.class, new XC_MethodHook() {
                      @Override
                      protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                         Bundle bundle = (Bundle) param.args[0];
                         Object activity = param.thisObject;
                         log("bundle是否为空"+ String.valueOf(bundle == null));
                      }
                  });
          findAndHookMethod("com.font.old.a", loadPackageParam.classLoader, "b", new XC_MethodHook() {
              @Override
              protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                int result = (int) param.getResult();
                log("CurrentUserInfoContainer.m3564a().mo27862b "+result);
              }
          });
          findAndHookMethod("com.font.util.ab", loadPackageParam.classLoader, "a", String.class, String.class, boolean.class, new XC_MethodHook() {
                      @Override
                      protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                          String str  = (String) param.args[0];
                          String str1  = (String) param.args[1];
                          log("第一个参数:"+str+"第二个参数:"+str1);
                      }
                  });
        }

        if(loadPackageParam.packageName.equals("com.hxapp.oibgaz")){
            findAndHookMethod("com.snapart.tool.BitmapUtil", loadPackageParam.classLoader, "getFileToPhotos",
                    String.class, boolean.class, boolean.class, int.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            int value = (int) param.args[3];
                            log("outWidth:"+value);
                        }
                    });

            findAndHookMethod("com.snapart.ui.widget.FrameImageView", loadPackageParam.classLoader, "updateFrame",
                    int.class, int.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            Class clazz = XposedHelpers.findClass("com.snapart.ui.widget.FrameImageView", loadPackageParam.classLoader);
                            Field sMoney = clazz.getDeclaredField("mPicUri");
                            sMoney.setAccessible(true);
                            String sMoneyIn = (String) sMoney.get(null);
                            XposedBridge.log("uri地址:"+sMoneyIn);
                        }
                    });
        }


//        详细内容参考地址
//        https://blog.csdn.net/zhangyongfeiyong/article/details/53572894
//        XposedBridge.log("test start");
//        XposedBridge.log("----------------------------------" + loadPackageParam.packageName + "---------------------------");
        if(loadPackageParam.packageName.equals("com.tencent.mm")){
//            findAndHookMethod("com.tencent.mm.plugin.nearby.ui.NearbyFriendsUI", loadPackageParam.classLoader, "bQY",
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                            super.afterHookedMethod(param);
//                            log("调用了这个ui的onResume方法");
//                        }
//                    });

//            findAndHookMethod("com.tencent.mm.plugin.nearby.ui.NearbyFriendsUI$b", loadPackageParam.classLoader, "getCount", new XC_MethodHook() {
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    log("当前列表长度:"+param.getResult());
//                }
//            });
//            log("找到微信");
//            findAndHookMethod("com.tencent.mm.protocal.protobuf.awj", loadPackageParam.classLoader, "vCN", new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                             List list = (List) param.getResult();
//                             if(list != null){
//                                 vCN++;
//                                 if(vCN <2){
//                                     for(int i =0;i< list.size();i++){
//                                         Object awe = list.get(i);
//                                         String gfj = (String) getObjectField(awe,"gfj");
//                                         String gfk = (String) getObjectField(awe,"gfk"); //
//                                         String gfl = (String) getObjectField(awe,"gfl");//个性签名
//                                         String gfn = (String) getObjectField(awe,"gfn");
//                                         String gfr = (String) getObjectField(awe,"gfr");
//                                         String gfs = (String) getObjectField(awe,"gfs");
//                                         String jfn = (String) getObjectField(awe,"jfn");
//                                         String jgt = (String) getObjectField(awe,"jgt");
//                                         int uUx = (int) getObjectField(awe,"uUx");
//                                         String vAm = (String) getObjectField(awe,"vAm");
//                                         String vHn = (String) getObjectField(awe,"vHn");
//                                         String vQj = (String) getObjectField(awe,"vQj");
//                                         int vQk = (int) getObjectField(awe,"vQk");
//                                         String vQl = (String) getObjectField(awe,"vQl");
//                                         String vQn = (String) getObjectField(awe,"vQn");
//                                         int vQo = (int) getObjectField(awe,"vQo");
//                                         int vQp = (int) getObjectField(awe,"vQp");
//                                         String vmd = (String) getObjectField(awe,"vmd");
//                                         String vme = (String) getObjectField(awe,"vme");
//                                         //                    public String vQm;
////                    public String vQn;
////                    public int vQo;
////                    public int vQp;
////                    public bzy vQq;
////                    public tt vQr;
////                    public String vmd;
////                    public String vme;
//                                         log("名字:"+gfj+","+gfk+","+gfl+","+gfn+","+gfr+","+gfs+","+jfn+","+jgt+","+uUx+","+vAm+","+vHn+","+
//                                                 vQj+","+vQk+","+vQl+","+vQn+","+vQo+","+vQp+","+vmd+","+","+vme+"\n");
//                                     }
//                                 }
//                             }
//                        }
//                    }
//            );
            findAndHookMethod("com.tencent.mm.protocal.protobuf.awj", loadPackageParam.classLoader, "op",
                    int.class, Object[].class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                           int i = (int) param.args[0];
                           log("i的值:"+i);
                        }
                    });
            findAndHookConstructor("com.tencent.mm.plugin.nearby.a.c", loadPackageParam.classLoader,
                    int.class, float.class, float.class, int.class, int.class, String.class, String.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                          int arg0 = (int) param.args[0];
                          float arg1 =  (float) param.args[1];
                          float arg2 =  (float) param.args[2];
                          int arg3 =  (int) param.args[3];
                          int arg4 = (int)param.args[4];
                          String arg5 = (String) param.args[5];
                          String arg6 = (String) param.args[6];
                          log("参数:"+arg0+","+arg1+","+arg2+","+arg3+","+arg4+","+arg5+","+arg6);
                        }
                    });
           Class clazz = findClass("com.tencent.mm.plugin.nearby.a.c",loadPackageParam.classLoader);

            findAndHookMethod("com.tencent.mm.plugin.nearby.a.c", loadPackageParam.classLoader, "bQU", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    List list  = (List) param.getResult();
                    log("bQU列表:"+list.size());
                    if(list.size()>0){
                        bQU++;
                        if(bQU <2){
                           Object object =  param.thisObject;


                            for(int i =0;i< list.size();i++){
                                Object awe = list.get(i);
                                String gfj = (String) getObjectField(awe,"gfj");
                                String gfk = (String) getObjectField(awe,"gfk"); //
                                String gfl = (String) getObjectField(awe,"gfl");//个性签名
                                String gfn = (String) getObjectField(awe,"gfn");
                                String gfr = (String) getObjectField(awe,"gfr");
                                String gfs = (String) getObjectField(awe,"gfs");
                                String jfn = (String) getObjectField(awe,"jfn");
                                String jgt = (String) getObjectField(awe,"jgt");
                                int uUx = (int) getObjectField(awe,"uUx");
                                String vAm = (String) getObjectField(awe,"vAm");
                                String vHn = (String) getObjectField(awe,"vHn");
                                String vQj = (String) getObjectField(awe,"vQj");
                                int vQk = (int) getObjectField(awe,"vQk");
                                String vQl = (String) getObjectField(awe,"vQl");
                                String vQn = (String) getObjectField(awe,"vQn");
                                int vQo = (int) getObjectField(awe,"vQo");
                                int vQp = (int) getObjectField(awe,"vQp");
                                String vmd = (String) getObjectField(awe,"vmd");
                                String vme = (String) getObjectField(awe,"vme");
                                //                    public String vQm;
//                    public String vQn;
//                    public int vQo;
//                    public int vQp;
//                    public bzy vQq;
//                    public tt vQr;
//                    public String vmd;
//                    public String vme;
                                log("bQU:"+gfj+","+gfk+","+gfl+","+gfn+","+gfr+","+gfs+","+jfn+","+jgt+","+uUx+","+vAm+","+vHn+","+
                                        vQj+","+vQk+","+vQl+","+vQn+","+vQo+","+vQp+","+vmd+","+","+vme+"\n");
                            }

                        }
                    }
                }
            });

            findAndHookMethod("com.tencent.mm.plugin.nearby.ui.NearbyFriendsUI", loadPackageParam.classLoader, "s", findClass("com.tencent.mm.plugin.nearby.ui.NearbyFriendsUI", loadPackageParam.classLoader),

                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                List list  = (List) param.getResult();
                                if(list.size()>0){
                                    index++;
                                    if(index <2){
                                    for(int i =0;i< list.size();i++){
                                        Object awe = list.get(i);
                                       String gfj = (String) getObjectField(awe,"gfj");
                                       String gfk = (String) getObjectField(awe,"gfk"); //
                                       String gfl = (String) getObjectField(awe,"gfl");//个性签名
                                       String gfn = (String) getObjectField(awe,"gfn");
                                       String gfr = (String) getObjectField(awe,"gfr");
                                       String gfs = (String) getObjectField(awe,"gfs");
                                       String jfn = (String) getObjectField(awe,"jfn");
                                       String jgt = (String) getObjectField(awe,"jgt");
                                       int uUx = (int) getObjectField(awe,"uUx");
                                        String vAm = (String) getObjectField(awe,"vAm");
                                        String vHn = (String) getObjectField(awe,"vHn");
                                        String vQj = (String) getObjectField(awe,"vQj");
                                        int vQk = (int) getObjectField(awe,"vQk");
                                        String vQl = (String) getObjectField(awe,"vQl");
                                        String vQn = (String) getObjectField(awe,"vQn");
                                        int vQo = (int) getObjectField(awe,"vQo");
                                        int vQp = (int) getObjectField(awe,"vQp");
                                        String vmd = (String) getObjectField(awe,"vmd");
                                        String vme = (String) getObjectField(awe,"vme");
                                        //                    public String vQm;
//                    public String vQn;
//                    public int vQo;
//                    public int vQp;
//                    public bzy vQq;
//                    public tt vQr;
//                    public String vmd;
//                    public String vme;
                                       log("名字:"+gfj+","+gfk+","+gfl+","+gfn+","+gfr+","+gfs+","+jfn+","+jgt+","+uUx+","+vAm+","+vHn+","+
                                               vQj+","+vQk+","+vQl+","+vQn+","+vQo+","+vQp+","+vmd+","+","+vme+"\n");
                                    }

                                }
                            }
                        }
                    });

            findAndHookMethod("com.tencent.mm.plugin.nearby.ui.NearbyFriendsUI$b", loadPackageParam.classLoader, "getItem",int.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                   int index = (int) param.args[0];
                if(index == 1){
//                    这里只取第二个值

                  Class awe = (Class) param.getResult();
                    Field gfj =  awe.getDeclaredField("gfj");
                    String gfjStr = (String) gfj.get(null);
                    Field gfk = awe.getDeclaredField("gfk");
                    String gfkStr = (String) gfk.get(null);
                    Field gfl = awe.getDeclaredField("gfl");
                    String gflStr = (String) gfl.get(null);
                    Field gfn = awe.getDeclaredField("gfn");
                    String gfnStr = (String) gfn.get(null);
                    Field gfr = awe.getDeclaredField("gfr");
                    String gfrStr = (String) gfr.get(null);
                    Field gfs = awe.getDeclaredField("gfs");
                    String gfsStr = (String) gfs.get(null);
                    Field jfn = awe.getDeclaredField("jfn");
                    String jfnStr = (String) jfn.get(null);
                    Field jgt = awe.getDeclaredField("jgt");
                    String jgtStr = (String) jgt.get(null);
                    Field uUx = awe.getDeclaredField("uUx");
                    int uUxInt = (int) uUx.get(null);
                    Field vAm = awe.getDeclaredField("vAm");
                    String vAmStr = (String) vAm.get(null);
                    Field vHn = awe.getDeclaredField("vHn");
                    String vHnStr = (String) vHn.get(null);
                    Field vQj = awe.getDeclaredField("vQj");
                    String vQjStr = (String) vQj.get(null);
                    Field vQk = awe.getDeclaredField("vQk");
                    int vQkStr = (int) vQk.get(null);
                    Field vQl = awe.getDeclaredField("vQl");
                    String vQlStr = (String) vQl.get(null);
                    log("测试军:"+gfjStr+"/"+gfkStr+"/"+gflStr+"/"+gfnStr+"/"+gfrStr+"/"+gfsStr+"/"+jfnStr+"/"+jgtStr+"/"+uUxInt+"/"+vAmStr+"/"+vHnStr+"/"+vQjStr+"/"+
                            vQkStr+"/"+vQlStr+"\n");
                }


                }
            });
        }




        if (loadPackageParam.packageName.equals("com.example.pigxposed")) {
            //设置静态变量值
            Class clazz = XposedHelpers.findClass("com.example.pigxposed.demo", loadPackageParam.classLoader);
            XposedHelpers.setStaticObjectField(clazz, "sMoney", 110);
//            既可以访问私有变量又可以访问public字段
            Field sMoney = clazz.getDeclaredField("sMoney");
            sMoney.setAccessible(true);
            int sMoneyIn = (int) sMoney.get(null);
            XposedBridge.log((String) String.valueOf(sMoneyIn) + ":设置静态变量值");

            //主动调用内部函数，这里只能针对无参构造方法的调用
            Class clazzd = XposedHelpers.findClass("com.example.pigxposed.demo", loadPackageParam.classLoader);
            XposedHelpers.callMethod(clazzd.newInstance(), "hidden_function");
            XposedBridge.log("调用了hidenfun");
            //这里针对没有无参类， 只有有参数的构造方法
//            demo(String str)
//            Class clazzz = XposedHelpers.findClass("com.example.pigxposed.demo",loadPackageParam.classLoader);
//            Constructor constructor = clazz.getConstructor(String.class);
//            XposedHelpers.callMethod(constructor.newInstance("..."),"hidden_fun");


//            内部类的使用
            XposedHelpers.findAndHookMethod("com.example.pigxposed.demo$innerClass", loadPackageParam.classLoader,

                    "scret", String.class, boolean.class, new XC_MethodHook() {
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            for (int i = 0; i < param.args.length; i++) {
                                XposedBridge.log(" argument is:" + param.args[i]);
                            }

                            int field_result = (int) XposedHelpers.getObjectField(param.thisObject, "pMoney");

                            XposedBridge.log("获取的值:" + String.valueOf(field_result));
                        }
                    });

//            构造函数demo()的处理
//            Class clazz = XposedHelpers.findClass("com.example.inner_class_demo.demo",lpparam.classLoader);
//            XposedHelpers.findAndHookConstructor(clazz, new XC_MethodHook() {
//                @Override
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                    super.beforeHookedMethod(param);
//                }
//            } )

//            进阶部分  同时监控多个构造函数、多个重载函数
//            hookAllConstructors(clazz, new XC_MethodHook()
//            hookAllMethods(clazz, new XC_MethodHook()

        }

        if (loadPackageParam.packageName.equals("com.pixite.pigment")) {
//            Log.e("hook", "ss");
            XposedBridge.log("hook start");
            ApplicationInfo applicationInfo = loadPackageParam.appInfo;

            findAndHookMethod("com.pixite.pigment.system.Device", loadPackageParam.classLoader,
                    "getMaxCanvasSize", Application.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            int maxCanvasSize = (int) param.getResult();
                            log("最大画布大小:"+maxCanvasSize);
                        }
                    });

//           loadPackageParam包含包的基本信息
//            packageName  String  应用包名
//            processName  String  应用加载后的进程名
//            classLoader  ClassLoader  应用的classloader
//            appInfo   ApplicationInfo  	应用的信息，包括verisonCode，uid等

//         findAndHookConstructor("com.ryanharter.android.gl.WritableTexture", loadPackageParam.classLoader, int.class, int.class,
//                  boolean.class, boolean.class, int.class, new XC_MethodHook() {
//                      @Override
//                      protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                          int width = (int) param.args[0];
//                          int height = (int) param.args[1];
//                          totalWidth += width;
//                          totalHeight +=  height;
//                          log("宽度+高度:"+width+","+height);  //64  64
//                          log("总宽度:"+totalWidth+",高度:"+totalHeight); //128
//                          i++;
//                          log(""+i);  //256
//                      }
//                  });

//         findAndHookMethod("com.pixite.pigment.features.editor.tiles.TilePool", loadPackageParam.classLoader, "getTile", new XC_MethodHook() {
//             @Override
//             protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                 Class clazz = loadPackageParam.classLoader.loadClass("com.pixite.pigment.features.editor.tiles.TilePool");
//                 Field field = clazz.getDeclaredField("maxTiles");
//                 field.setAccessible(true);
//                 int ints = (int) field.get(param.thisObject);
//                 log("最大值为:"+ints);  //1170  jpg 4681
//             }
//         });

//         findAndHookMethod("android.view.MotionEvent", loadPackageParam.classLoader, "getHistorySize", new XC_MethodHook() {
//             @Override
//             protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                 int ints = (int) param.getResult();
//                 log("getHistorySize:"+ints);
//             }
//         });

//         findAndHookMethod("com.pixite.pigment.features.editor.GestureTransformer", loadPackageParam.classLoader, "onTouchEvent", MotionEvent.class, new XC_MethodHook() {
//             @Override
//             protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                 boolean bool = (boolean) param.getResult();
//                 log("是否为true:"+ bool);
//             }
//         });
//         findAndHookMethod("com.pixite.pigment.backend.projects.PigmentProject", loadPackageParam.classLoader, "writeZip", ZipOutputStream.class,
//                 new XC_MethodHook() {
//                     @Override
//                     protected void afterHookedMethod(MethodHookParam param) throws Throwable {
////                            导出的时候的操作
//                         log("这是导出的时候操作");
//
//                     }
//                 });
//        findAndHookMethod("com.pixite.pigment.backend.projects.TileSaveTransaction", loadPackageParam.classLoader, "saveTile", int.class, Bitmap.class,
//                new XC_MethodHook() {
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        int i = (int) param.args[0];
//                        log("savTile的索引号:"+i);
//                    }
//                });
//        findAndHookMethod("com.pixite.pigment.views.MoveGestureDetector", loadPackageParam.classLoader, "onTouchEvent",
//                MotionEvent.class, new XC_MethodHook() {
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        log("开始hook手势操作");
//                        log(""+(true == getBooleanField( param.thisObject,"inProgress")));
//                    }
//                });
//           findAndHookMethod("com.pixite.pigment.model.Palette", loadPackageParam.classLoader, "premium", new XC_MethodHook() {
//               @Override
//               protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                   param.setResult(false);
//               }
//           });
//            findAndHookMethod("com.pixite.pigment.features.editor.brushes.Brush", loadPackageParam.classLoader, "getPremium", new XC_MethodHook() {
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    param.setResult(false);
//                }
//            });
//            findAndHookMethod("com.pixite.pigment.features.editor.tools.brushpicker.AvailableBrush", loadPackageParam.classLoader, "getOwned",
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                            param.setResult(true);
//                        }
//                    });

//            Class clazzd = XposedHelpers.findClass("com.pixite.pigment.features.editor.brushes.Brush", loadPackageParam.classLoader);
//            Field field =  clazzd.getDeclaredField("premium");
//            field.setAccessible(true);
//            XposedHelpers.setBooleanField(field,"premium",false);
//            boolean bools = (boolean) field.get(null);
//            XposedBridge.log(bools+"....");
//            Class clazz = XposedHelpers.findClass("com.pixite.pigment.features.editor.brushes.Brush", loadPackageParam.classLoader);
//            XposedHelpers.setBooleanField(clazz, "premium", false);
//         findAndHookMethod("com.pixite.pigment.features.editor.canvas.BrushingCanvas", loadPackageParam.classLoader, "addMask", int.class,
//                 int.class, new XC_MethodHook() {
//                     @Override
//                     protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                         log("两个值:"+param.args[0]+","+param.args[1]);
//                     }
//                 }
//         );

//         findAndHookMethod("com.pixite.pigment.features.editor.tiles.TilePool", loadPackageParam.classLoader, "getTile", new XC_MethodHook() {
//             @Override
//             protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                 super.afterHookedMethod(param);
//                 log("调用了+"+i);
//                 i++;
//             }
//         });

//            findAndHookMethod("com.pixite.pigment.activities.BaseActivity", loadPackageParam.classLoader, "getSystemService", String.class, new XC_MethodHook() {
////
//                @Override
//                protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
//                    Class clazz = loadPackageParam.classLoader.loadClass("com.pixite.pigment.features.editor.tiles.TiledCanvas");
//                    Field field = clazz.getDeclaredField("tiles");
//                    field.setAccessible(true);
//                    List list = (List) field.get(param.thisObject);
//                    log("tiles长度:"+list.size());

//                    Activity activity = (Activity) param.thisObject;
//                    XposedBridge.log("查看这个class的名字:"+activity.getLocalClassName());
//                    XposedBridge.log("fixed finished");
//                    String str = (String) param.args[0];
//                    XposedBridge.log("names:" + str);
//                    Class c = loadPackageParam.classLoader.loadClass("com.treeanimals.max.myapplication.MainActivity");
//                    Field field = c.getDeclaredField("showIMEI");
//                    field.setAccessible(true);
//                    TextView textView = (TextView)field.get(param.thisObject);
//                    textView.setText("Hello World");
//                    Class c = loadPackageParam.classLoader.loadClass("com.pixite.pigment.features.editor.brushes.FillBrush");
//                    Field field = c.getDeclaredField("SHADER_FRAGMENT");
//                    Field field3 = c.getDeclaredField("SHADER_VERTEX");
//                    field.setAccessible(true);
//                    field3.setAccessible(true);
//                    String text = (String) field.get(param.thisObject);
//                    String text1 = (String) field3.get(param.thisObject);
//                    XposedBridge.log(" FillBrush SHADER_FRAGMENT:" + text);
//                    XposedBridge.log(" FillBrush SHADER_VERTEX:" + text1);

//                    com.pixite.pigment.features.editor.brushes
//                    Class d = loadPackageParam.classLoader.loadClass("com.pixite.pigment.features.imports.ImportActivity");
//                    Field field1 = d.getDeclaredField("IMAGE_URI_KEY");
//                    field1.setAccessible(true);
//                    String text2 = (String) field1.get(param.thisObject);
//                    XposedBridge.log("IMAGE_URI_KEY:" + text2);
//                    XposedBridge.log("--------------------------------");
            ////////
//                    Class e = loadPackageParam.classLoader.loadClass("com.pixite.pigment.features.editor.brushes.BrushManager");
//                    Field[] fields = e.getDeclaredFields();
//                    for (int i = 0; i < fields.length; i++) {
//                        fields[i].setAccessible(true);
//                        if (fields[i].getName().equals("DEFAULT_PHONE_BRUSH_NAME")) {
//                            XposedBridge.log("BrushManager DEFAULT_PHONE_BRUSH_NAME:" + fields[i].get(param.thisObject));
//                        }
//                        if (fields[i].getName().equals("DEFAULT_TABLET_BRUSH_NAME")) {
//                            XposedBridge.log("BrushManager DEFAULT_TABLET_BRUSH_NAME:" + fields[i].get(param.thisObject));
//                        }
//                    }
            //////////////////////////////////////
//                    Class f = loadPackageParam.classLoader.loadClass("com.pixite.pigment.features.editor.brushes.GradientBrush");
//                    Field[] fields1 = f.getDeclaredFields();
//                    for (int j = 0; j < fields1.length; j++) {
//                        fields1[j].setAccessible(true);
//                        if (fields1[j].getName().equals("SHADER_FRAGMENT")) {
//                            XposedBridge.log("GradientBrush SHADER_FRAGMENT:" + fields1[j].get(param.thisObject));
//                        }
//                        if (fields1[j].getName().equals("SHADER_VERTEX")) {
//                            XposedBridge.log("GradientBrush SHADER_VERTEX:" + fields1[j].get(param.thisObject));
//                        }
//
//                    }
            ///////


//                    XSharedPreferences xSharedPreferences = new XSharedPreferences("com.pixite.pigment");
//                    XposedBridge.log("上一次笔刷:"+xSharedPreferences.getString("last_brush",""));

//                    Class projectFileProvider = loadPackageParam.classLoader.loadClass("com.pixite.pigment.system.ProjectFileProvider");
//                    Field field =projectFileProvider.getDeclaredField("MIME_TYPE");
//                    field.setAccessible(true);
//                    XposedBridge.log("MIME_TYPE:"+field.get(param.thisObject));
//                    Class home = loadPackageParam.classLoader.loadClass("com.pixite.pigment.features.home.HomeActivity");
//                    Field field = home.getDeclaredField("PURCHASE_KEY");
//                    field.setAccessible(true);
//                    XposedBridge.log("PURCHASE_KEY:"+field.get(param.thisObject));
//                    Class second = loadPackageParam.classLoader.loadClass("com.pixite.pigment.features.home.library.LibraryFragment");
//                    Field field =  second.getDeclaredField("EXTRA_BOOK");
//                    field.setAccessible(true);
//                    XposedBridge.log("EXTRA_BOOK:"+field.get(param.thisObject));
//                    Field field1 = second.getDeclaredField("EXTRA_CATEGORY");
//                    field1.setAccessible(true);
//                    XposedBridge.log("EXTRA_CATEGORY:"+field1.get(param.thisObject));
//                    Field field2 = second.getDeclaredField("PURCHASE_KEY");
//                    field2.setAccessible(true);
//                    XposedBridge.log("EXTRA_CATEGORY:"+field2.get(param.thisObject));

//                    Class clazz1 =  loadPackageParam.classLoader.loadClass("com.pixite.pigment.features.editor.tiles.TiledCanvas");
//                    Field field3 = clazz1.getDeclaredField("tiles");
//                    field3.setAccessible(true);
//                   List list = (List) field3.get(param.thisObject);
//                    XposedBridge.log("list 长度:"+ list.size());
//

//                }
//
//
//
//            });
//            Class projects = loadPackageParam.classLoader.loadClass("com.pixite.pigment.data.PigmentProject");
//            findAndHookMethod("com.pixite.pigment.features.editor.GlRenderer", loadPackageParam.classLoader, "setProject", projects.getClass(), new XC_MethodHook() {
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    Class project = (Class) param.args[0];
//                   Field field =   project.getDeclaredField("projectId");
//                   XposedBridge.log("这个工程的Id:"+ field.get(param.thisObject));
//                }
//            });

        }
//        if(loadPackageParam.packageName.equals("com.pixite.pigment")){
//            XposedBridge.log("hook 下厨房");
//            RuntimeException e = new RuntimeException();
//            findAndHookMethod("java.lang.RuntimeException", loadPackageParam.classLoader, "getStackTrace", new XC_MethodHook() {
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    StackTraceElement[] newStack = new StackTraceElement[10];
//                    StackTraceElement[]  stackTraceElements = (StackTraceElement[]) param.getResult();
//                    for(StackTraceElement stackTraceElement:stackTraceElements){
//                        if(!stackTraceElement.getClassName().contains("XposedBridge")  && !stackTraceElement.getClassName().contains("XposedBridge")) {
//                        }
//                    }
//                    super.afterHookedMethod(param);
//                }
//            });
//        }


//        if(loadPackageParam.packageName.equals("com.fungamesforfree.colorfy")){
//            XposedBridge.log("begin hook colorfy");
//            findAndHookMethod("d.a.a.a.b.a", loadPackageParam.classLoader, "a", new XC_MethodHook() {
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    XposedBridge.log("AdaptiveThresholdFilter:\n"+param.getResult());
//                }
//            });
//
//
//            findAndHookMethod("d.a.a.a.d.a", loadPackageParam.classLoader, "a", new XC_MethodHook() {
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                   XposedBridge.log("BoxBlurFilter:\n"+param.getResult());
//                }
//            });
//
//            findAndHookMethod("d.a.a.a.b.i", loadPackageParam.classLoader, "a", new XC_MethodHook() {
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    XposedBridge.log("LuminanceThresholdFilter:\n"+param.getResult());
//                }
//            });
//
//            findAndHookMethod("d.a.a.a.d.c", loadPackageParam.classLoader, "a", new XC_MethodHook() {
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                  XposedBridge.log("GaussianBlurFilter:\n"+param.getResult());
//                }
//            });
//            findAndHookMethod("d.a.a.a.b.c", loadPackageParam.classLoader, "a", new XC_MethodHook() {
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    XposedBridge.log("ColourInvertFilter:\n"+param.getResult());
//                }
//            });
//
//            findAndHookMethod("d.a.a.a.b.h", loadPackageParam.classLoader, "a", new XC_MethodHook() {
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    XposedBridge.log("GreyScaleFilter:\n"+param.getResult());
//                }
//            });
//
//
//        }
    }

    public void log(String content) {
        XposedBridge.log(content);
    }

}

