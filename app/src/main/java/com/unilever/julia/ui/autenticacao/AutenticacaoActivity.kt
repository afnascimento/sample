package com.unilever.julia.ui.autenticacao

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat
import com.unilever.julia.R
import com.unilever.julia.ui.base.BaseViewActivity
import com.unilever.julia.utils.MaterialDialog
import com.unilever.julia.utils.RedirectIntents
import com.unilever.julia.utils.Utils

abstract class AutenticacaoActivity : BaseViewActivity(), AutenticacaoView {

    companion object {
        private const val CODE_PERMISSION_READ_PHONE_STATE = 100
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CODE_PERMISSION_READ_PHONE_STATE) {
            val permissionGranted = (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
            onRequestPermissionsReadPhoneState(permissionGranted)
        } else {
            handleInteractiveRequestRedirect(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CODE_PERMISSION_READ_PHONE_STATE) {
            val permissionGranted = (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            onRequestPermissionsReadPhoneState(permissionGranted)
        }
    }

    @SuppressLint("HardwareIds")
    override fun getImeiDevice(): String {
        val telephonyManager : TelephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            val rationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)

            if (rationale) {
                MaterialDialog(this)
                    .setTitle(getString(R.string.dialog_permission_title))
                    .setMessage(getString(R.string.dialog_permission_imei))
                    .setPositiveButton(getString(R.string.dialog_permission_ok)) { dialog ->
                        dialog.dismiss()
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE), CODE_PERMISSION_READ_PHONE_STATE)
                    }
                    .show()
            } else {
                MaterialDialog(this)
                    .setTitle(getString(R.string.dialog_permission_title))
                    .setMessage(Utils.getTextFromHtml(getString(R.string.dialos_permission_imei_denied)))
                    .setPositiveButton(getString(R.string.dialog_permission_ok)) { dialog ->
                        dialog.dismiss()
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivityForResult(intent, CODE_PERMISSION_READ_PHONE_STATE)
                    }
                    .show()
            }
        } else {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                telephonyManager.imei
            } else {
                telephonyManager.deviceId
            }
        }
        return ""
    }

    override fun redirectLogin() {
        RedirectIntents.redirectLoginActivity(this)
    }

    override fun redirectTutorial() {
        RedirectIntents.redirectTutorialActivity(this)
    }
}