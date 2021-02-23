package com.blankj.utilcode.util;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Size;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 *     author: desfate
 *     time  : 2021/02/23
 *     desc  : utils about camera info
 * </pre>
 */
public class CameraUtils {

    /**
     * Get a list of logical ids
     * @param context 上下文
     * @return Camera logical id list
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static String[] getCameraLogicId(Context context) throws CameraAccessException {
        CameraManager mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        return mCameraManager.getCameraIdList();
    }


    /**
     * 检查硬件支持级别
     * 每个级别都为前一个级别添加了其他功能，并且始终是前一个级别的严格超集。的顺序是 LEGACY < LIMITED < FULL < LEVEL_3。
     * LEGACY    设备在较旧的Android设备上以向后兼容模式运行，并且功能非常有限
     * LIMITED   设备代表基准功能集，并且还可能包含作为的子集的其他功能FULL
     * FULL      设备还支持对传感器，闪光灯，镜头和后处理设置进行逐帧手动控制，以及高速率的图像捕获
     * LEVEL_3   设备还支持YUV重新处理和RAW图像捕获，以及其他输出流配置
     * EXTERNAL  设备类似于LIMITED设备，但有一些例外，例如未报告某些传感器或镜头信息或帧速率较不稳定
     *
     * 某些功能不是任何特定硬件级别或功能的一部分，必须单独查询
     *
     * 校准时间戳（CameraCharacteristics#SENSOR_INFO_TIMESTAMP_SOURCE == 实时）
     * 精密镜头控制（已CameraCharacteristics#LENS_INFO_FOCUS_DISTANCE_CALIBRATION == 校准）
     * 人脸检测（CameraCharacteristics#STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES）
     * 光学或电子防抖（CameraCharacteristics#LENS_INFO_AVAILABLE_OPTICAL_STABILIZATION， CameraCharacteristics#CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES）
     *
     * @param cameraCharacteristics 相机配置信息
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static int checkHardwareSupport(CameraCharacteristics cameraCharacteristics){
        return cameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
    }

    /**
     * 获取逻辑摄像头和物理摄像头的对应关系
     * @param context 上下文
     * @return 对应关系的 HashMap
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static HashMap<String, List<Integer>> getPhysicalId(Context context) throws CameraAccessException {
        HashMap<String, List<Integer>> cameraList = new HashMap<>();
        CameraManager mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        for (String logicId : mCameraManager.getCameraIdList()){
            CameraCharacteristics characteristics = mCameraManager.getCameraCharacteristics(logicId);
            for (int key : characteristics.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES)){
                if(key == CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_LOGICAL_MULTI_CAMERA){
                    // 摄像头设备是由两个或多个物理摄像头支持的逻辑摄像头
                    if(cameraList.containsKey(logicId)){
                        Objects.requireNonNull(cameraList.get(logicId)).add(key);
                    }else {
                        cameraList.put(logicId, new ArrayList<Integer>(key));
                    }
                }
            }
        }
        return cameraList;
    }

    /**
     * 获取相机前置还是后置
     * @param characteristics 相机配置
     * @return CameraCharacteristics.LENS_FACING_BACK or LENS_FACING_FRONT or LENS_FACING_EXTERNAL
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static int getCameraFront(CameraCharacteristics characteristics){
        return characteristics.get(CameraCharacteristics.LENS_FACING);
    }

    /**
     * 获取当前逻辑相机支持的输出分辨率
     * @param characteristics 相机信息
     * @param format 输出格式
     * @return 分辨率列表
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static List<Size> getCameraSizeSupport(CameraCharacteristics characteristics, int format){
        StreamConfigurationMap streamConfigurationMap = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        Size[] sizes = streamConfigurationMap.getOutputSizes(ImageFormat.JPEG);
        return  Arrays.asList(sizes);
    }

}
