package de.rindus.ringdus.presentation

import android.os.Bundle
import android.util.TypedValue
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.rindus.ringdus.R
import de.rindus.ringdus.data.error.Failure
import de.rindus.ringdus.presentation.viewmodel.OpenDoorViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getViewModel<OpenDoorViewModel>().run {
            observeFailure().observe(this@MainActivity, Observer {
                when (it) {
                    Failure.OutOfWifi -> R.string.out_of_wifi to R.color.blue
                    Failure.NotAuthorizedFailure -> R.string.not_authorized to android.R.color.holo_red_dark
                    else -> R.string.error_general to android.R.color.holo_red_dark
                }.also {
                    showSnackBar(it.first, it.second)
                }
            })

            observeSuccess().observe(this@MainActivity, Observer {
                showSnackBar(R.string.door_open, R.color.rindus_green)
            })

            openButton.setOnClickListener {
                this.doOpenDoor()
                startRotation()
            }
        }
    }

    private fun showSnackBar(message: Int, backgroundColor: Int) {
        Snackbar.make(
            this@MainActivity.findViewById(android.R.id.content),
            message, Snackbar.LENGTH_LONG
        ).apply {
            view.setBackgroundColor(ContextCompat.getColor(context, backgroundColor))
            view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            }
        }.show()
    }

    private fun startRotation() {
        RotateAnimation(
            0f, 360f, Animation.RELATIVE_TO_SELF,
            0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 400
            interpolator = LinearInterpolator()
        }.also { openButton.startAnimation(it) }
    }
}
