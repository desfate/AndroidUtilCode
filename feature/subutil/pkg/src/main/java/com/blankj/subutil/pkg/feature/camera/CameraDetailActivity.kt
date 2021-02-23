package com.blankj.subutil.pkg.feature.camera

import android.content.Context
import android.content.Intent
import android.os.Build
import com.blankj.common.activity.CommonActivity
import com.blankj.common.helper.PermissionHelper
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemTitle
import com.blankj.subutil.pkg.R
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.*
import java.util.ArrayList

/**
 * <pre>
 *     author: desfate
 *     time  : 2021/02/23
 *     desc  : demo about CameraUtils
 * </pre>
 */
class CameraDetailActivity : CommonActivity(){

    override fun bindTitleRes(): Int {
        return R.string.demo_camera
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        val items = arrayListOf<CommonItem<*>>();

        return items
    }




}