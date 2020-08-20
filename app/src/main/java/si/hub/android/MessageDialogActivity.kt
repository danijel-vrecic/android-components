package si.hub.android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import si.hub.android.databinding.ActivityMessageDialogBinding
import si.hub.android.dialog.BaseDialogActivity

class MessageDialogActivity : BaseDialogActivity() {
    private val binding: ActivityMessageDialogBinding by lazy {
        ActivityMessageDialogBinding.inflate(
            layoutInflater
        )
    }
    override val contentViewBinding: ViewBinding
        get() = binding

    override fun onCreateEx(savedInstanceState: Bundle?) {
        intent.extras?.let {
            binding.title.text = it.getString(ARG_TITLE)
            binding.subtitle.text = it.getString(ARG_SUBTITLE)
        }
        binding.btnYes.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
        binding.btnCancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_SUBTITLE = "subtitle"

        fun createIntent(context: Context, title: String, subtitle: String): Intent {
            return Intent(context, MessageDialogActivity::class.java).apply {
                putExtra(ARG_TITLE, title)
                putExtra(ARG_SUBTITLE, subtitle)
            }
        }
    }
}