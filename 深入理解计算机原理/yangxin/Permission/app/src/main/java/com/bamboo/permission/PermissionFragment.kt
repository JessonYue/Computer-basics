package com.bamboo.permission

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity


/**
 *create by yx
 *on 2020/6/21
 */
class PermissionFragment : Fragment() {

    private var mCallback: ((success: Boolean) -> Unit)? = null

    companion object {
        private const val TGA = "PermissionFragment"
        private const val REQUEST = 111//生产请求code
        fun requestPermissions(
            activity: FragmentActivity,
            permissions: Array<String>,
            callback: ((success: Boolean) -> Unit)
        ) {
            val fragment =
                getFragment(
                    activity
                )
            fragment.mCallback = callback
            if (fragment.check(permissions)) {
                callback(true)
            } else {
                fragment.requestPermissions(permissions,
                    REQUEST
                )
            }
        }

        private fun getFragment(
            activity: FragmentActivity
        ): PermissionFragment {
            var temp: Fragment?

            val fragmentManger = activity.supportFragmentManager
            temp = fragmentManger.findFragmentByTag(TGA)

            if (temp == null) {
                temp = PermissionFragment()
                fragmentManger.beginTransaction().add(temp,
                    TGA
                ).commitNow()
            }


            return temp as PermissionFragment
        }
    }


    fun check(permissions: Array<String>): Boolean {
        if (context == null) {
            return false
        }

        var result = true
        for (p in permissions) {
            if (ContextCompat.checkSelfPermission(context!!, p) != PERMISSION_GRANTED) {
                result = false
            }
        }

        return result
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST) {
            var result = true
            for (r in grantResults) {
               if (r != PackageManager.PERMISSION_GRANTED) {
                   result = false
               }
            }
            mCallback?.let { it(result) }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}