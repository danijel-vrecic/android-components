package si.hub.android

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import si.hub.android.base.BaseActivity
import si.hub.android.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnOpenDialog.setOnClickListener {
            startActivityForResult(
                MessageDialogActivity.createIntent(this, "Title", "Subtitle"),
                REQUEST_CODE_MESSAGE_DIALOG
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_MESSAGE_DIALOG -> {
                when (resultCode) {
                    RESULT_OK -> Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()
                    RESULT_CANCELED -> Toast.makeText(this, "CANCELED", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_MESSAGE_DIALOG = 999
    }
}