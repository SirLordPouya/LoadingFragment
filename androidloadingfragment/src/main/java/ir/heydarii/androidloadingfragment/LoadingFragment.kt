package ir.heydarii.androidloadingfragment


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.airbnb.lottie.LottieDrawable
import ir.heydarii.androidloadingfragment.databinding.FragmentLoadingBinding

private const val FILE_NAME = "fileName"
private const val IS_CANCELABLE = "fileName"
private const val FILE_NAME_BUNDLE = "fileNameBundle"
private const val IS_CANCELABLE_BUNDLE = "isCancelableBundle"

class LoadingFragment : DialogFragment() {

    private var _binding: FragmentLoadingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //set cancelable
        setCancelableMethod()

        _binding = FragmentLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setCancelableMethod() {
        //getting cancelable type
        val cancelableType = arguments?.getBundle(IS_CANCELABLE_BUNDLE)?.getBoolean(IS_CANCELABLE)
        cancelableType?.let { isCancelable = it }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //making background color transparent
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        //getting file name
        val fileName = arguments?.getBundle(FILE_NAME_BUNDLE)?.getString(FILE_NAME)

        //setting up lottie
        fileName?.let { setUpLottieView(fileName) }
    }

    private fun setUpLottieView(fileName: String?) = with(binding) {
        lottie.setAnimation(fileName)
        lottie.playAnimation()
        lottie.repeatCount = LottieDrawable.INFINITE
        lottie.repeatMode = LottieDrawable.RESTART
    }

    fun dismissDialog() {
        dismiss()
    }

    companion object {

        private var mLoadingFragment: LoadingFragment? = null

        /**
         * gets a file name that should be available in assets folder
         */
        fun getInstance(
            fileName: String = "loading.json",
            isCancelable: Boolean = false
        ): LoadingFragment {

            if (mLoadingFragment == null) {
                mLoadingFragment = LoadingFragment()
            }

            //Main bundle to add other bundles to it
            val mainBundle = Bundle()

            //file name bundle to add file name
            val fileNameBundle = Bundle()
            fileNameBundle.putString(FILE_NAME, fileName)

            //cancelable bundle to add cancel type
            val isCancelableBundle = Bundle()
            isCancelableBundle.putBoolean(IS_CANCELABLE, isCancelable)

            //adding other bundles to main bundle
            mainBundle.putBundle(FILE_NAME_BUNDLE, fileNameBundle)
            mainBundle.putBundle(IS_CANCELABLE_BUNDLE, isCancelableBundle)

            //set main bundle as fragment's argument
            mLoadingFragment?.arguments = mainBundle

            return mLoadingFragment!!
        }
    }
}
