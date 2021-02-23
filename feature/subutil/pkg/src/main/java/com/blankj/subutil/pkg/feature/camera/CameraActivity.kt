package com.blankj.subutil.pkg.feature.camera

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.blankj.common.activity.CommonActivity
import com.blankj.common.helper.PermissionHelper
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemTitle
import com.blankj.subutil.pkg.R
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.*


/**
 * <pre>
 *     author: desfate
 *     time  : 2021/02/23
 *     desc  : demo about CameraUtils
 * </pre>
 */
class CameraActivity : CommonActivity(){

    companion object{
        fun start(context: Context) {
            PermissionHelper.request(context, object : PermissionUtils.SimpleCallback {
                override fun onGranted() {
                    val starter = Intent(context, CameraActivity::class.java)
                    context.startActivity(starter)
                }
                override fun onDenied() {
                }
            }, PermissionConstants.CAMERA)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_camera
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        val items = arrayListOf<CommonItem<*>>();
        var cameraLogicIds = "";
        var cameraPhysicalId = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cameraLogicIds = CameraUtils.getCameraLogicId(this).toList()
                    .toString().replace("[", "").replace("]", "");
            cameraPhysicalId = CameraUtils.getPhysicalId(this).toString()
        } else {
            // android lollipop to use camera but now we only support camera2
            ToastUtils.showShort("you should to change your phone")
        }
        items.add(CommonItemTitle("CameraLogicIds", cameraLogicIds))
        items.add(CommonItemTitle("CameraPhysicalId", cameraPhysicalId))
        cameraLogicIds.split(",").forEach {
            items.add(CommonItemClick("logic camera id = $it"){
//                ActivityUtils.startActivity(CameraActivity::class.java, CameraDetailActivity::class.java, Bundle().putString("CameraId", it))
            })
        }
        return items
    }




}