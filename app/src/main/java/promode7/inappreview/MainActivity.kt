package promode7.inappreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var reviewManager: ReviewManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        reviewManager = ReviewManagerFactory.create(this@MainActivity)
        btnRateNow.setOnClickListener { showRateDialog() }
    }

    /**
     * This creates a request to Show rate app bottom sheet using the in-app-review api.
     * We have no control over the fact that this review alert will be shown or not.
     * @see https://developer.android.com/guide/playcore/in-app-review/kotlin-java for more
     * detailed information.
     */
    private fun showRateDialog() {
        val request = reviewManager.requestReviewFlow()
        request.addOnCompleteListener { request ->
            if (request.isSuccessful) {
                // We got the ReviewInfo object
                val reviewInfo = request.result
                val flow = reviewManager.launchReviewFlow(this@MainActivity, reviewInfo)
                flow.addOnCompleteListener { _ ->
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown. Thus, no
                    // matter the result, we continue our app flow.
                }
            } else {
                // There was some problem, continue regardless of the result.
                // you can show your own rate dialog alert and redirect user to your app page
                // on play store.
            }
        }
    }
}