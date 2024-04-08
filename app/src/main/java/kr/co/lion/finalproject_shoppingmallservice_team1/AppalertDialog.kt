package kr.co.lion.finalproject_shoppingmallservice_team1

import android.app.AlertDialog
import android.content.Context
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat

class AppAlertDialog(
    private val context: Context,
    private val title: String? = null,
    private val msg: String?= null,
    private val postive:String? = null,
    private val negative:String? = null,
    private val theme:Int = 0
) {
    private lateinit var positiveListener: () -> Unit
    private lateinit var negativeListener: () -> Unit
    private val dialog: AlertDialog.Builder by lazy {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(msg)
            .setPositiveButton(postive) { _, _ ->
                if (::positiveListener.isInitialized) positiveListener()
            }
            .setNegativeButton(negative) { _, _ ->
                if (::negativeListener.isInitialized) negativeListener()
            }.setNeutralButton("") { _, _ ->

            }
    }

    fun show(onClickNegative: () -> Unit = {}, onClickPositive: () -> Unit = {}) {
        this.negativeListener = onClickNegative
        this.positiveListener = onClickPositive
        dialog.show().apply {
            /**
             * Title TextView
             */
            findViewById<TextView>(androidx.appcompat.R.id.alertTitle)?.apply {
                textSize = 10f
                setTextColor(context.getColor(R.color.black))
            }
            /**
             * Message TextView
             */
            findViewById<TextView>(android.R.id.message)?.apply {
                setTextColor(context.getColor(R.color.Writing_Color))
            }

            /**
             * Positive Button
             */
            // 신고, 삭제인 경우
            if (theme == 1){
                findViewById<TextView>(android.R.id.button1)?.apply {
                    textSize = 18f
                    setTextColor(context.getColor(R.color.red))
                }
            }
            else {
                findViewById<TextView>(android.R.id.button1)?.apply {
                    textSize = 18f
                    setTextColor(context.getColor(R.color.Pup_Color))
                }
            }

            /**
             * Negative Button
             */
            findViewById<TextView>(android.R.id.button2)?.apply {
                textSize = 18f
                setTextColor(context.getColor(R.color.black))
            }
            /**
             * Neutral Button
             */
            findViewById<TextView>(android.R.id.button3)?.apply {
                textSize = 18f
                setTextColor(context.getColor(R.color.black))
            }
        }
    }
}