package com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.conf;

/*
 * @brief 错误代码定义
 */
public class CMError {

    public static int CM_SUCCESS = 0;///< 表示接口执行成功
    public static int CM_ERROR_UNINIT = 1; ///< 在未初始化的状态调用了某些接口
    public static int CM_ERROR_USBKEY = 2;///< 加密狗认证失败，加密狗不正确或未插加密狗
    public static int CM_ERROR_RUNTIME = 3; ///< 核心库初始化失败，可能的原因运行时库初始化失败
    public static int CM_ERROR_CAMERA = 4; ///< 相机启动失败，或未发现相机设备，或相机被占用等
    public static int CM_PYTHON_INIT_FAILD = 5; ///< python 运行时初始化失败
    public static int CM_LOAD_MODULE_FAILD = 6; ///< 模型文件加载失败
    public static int CM_LOAD_DICT_FAILD = 7; ///< 字典加载失败
    public static int CM_EMOTION_FAILD = 8; ///< 表情检测初始化失败
    public static int CM_AGE_GENDER_FAILD = 9; ///< 年龄性别初始化失败
    public static int CM_WAIT_INIT = 10 ;///< 正在初始化

}
