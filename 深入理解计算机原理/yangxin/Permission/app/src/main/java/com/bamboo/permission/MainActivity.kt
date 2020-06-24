package com.bamboo.permission

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PermissionFragment.requestPermissions(
            this, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE
            )
        ) { success ->
            if (success) {
                Toast.makeText(this, "申请成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "申请失败", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
