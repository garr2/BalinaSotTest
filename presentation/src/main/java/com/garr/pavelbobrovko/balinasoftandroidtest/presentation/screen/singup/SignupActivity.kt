package com.garr.pavelbobrovko.balinasoftandroidtest.presentation.screen.singup

import android.animation.Animator
import android.animation.ValueAnimator
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Rect
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AnimationUtils
import com.garr.pavelbobrovko.balinasoftandroidtest.R
import com.garr.pavelbobrovko.balinasoftandroidtest.databinding.ActivitySignupBinding
import com.garr.pavelbobrovko.balinasoftandroidtest.presentation.base.BaseMvvmActivity
import com.garr.pavelbobrovko.balinasoftandroidtest.presentation.utils.AutoCompleteEmailValidator
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.rxkotlin.subscribeBy

class SignupActivity : BaseMvvmActivity<SignupViewModel,SignupRouter,ActivitySignupBinding>() {

    private var keyboardListenersAttached = false
    private var isViewMoveUp = false

    override fun prodiveViewModel(): SignupViewModel {
        return ViewModelProviders.of(this).get(SignupViewModel::class.java)
    }

    override fun provideRouter(): SignupRouter {
        return SignupRouter(this)
    }

    override fun provideLayoutId(): Int {
        return R.layout.activity_signup
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RxTextView.textChangeEvents(binding.etEmail)
            .subscribeBy(
                onNext = {
                    val list = viewModel.updateArrayAdapter(it.text()
                            .toString())

                    if (!list.isEmpty()){
                        val array = arrayOfNulls<String>(list.size)
                        val validator = AutoCompleteEmailValidator(list.toArray(array))
                        binding.etEmail.validator = validator
                    }
                    else binding.etEmail.validator = null
                }
            )

        attachKeyboardListener()
    }

    private fun attachKeyboardListener(){
        if (keyboardListenersAttached)return

        binding.rootLayout.viewTreeObserver.addOnGlobalLayoutListener(keyboardListener)

        keyboardListenersAttached = true
    }

    override fun onDestroy() {
        super.onDestroy()
        if (keyboardListenersAttached){
            binding.rootLayout.viewTreeObserver.removeOnGlobalLayoutListener(keyboardListener)
        }
    }

    private val keyboardListener = ViewTreeObserver.OnGlobalLayoutListener {

        val r = Rect()
        binding.rootLayout.getWindowVisibleDisplayFrame(r)
        val screenHeight = binding.rootLayout.rootView.height
        val keypadHeight = screenHeight - r.bottom

        if(keypadHeight > screenHeight * 0.15){
            onKeyboardShow()
        }else onKeyboardHide()

    }

    private fun onKeyboardShow(){
        if (!isViewMoveUp){
            binding.ivLogo.visibility = View.GONE

            val animator = ValueAnimator.ofFloat(0f, -220f)
            animator.duration = 750
            animator.start()

            animator.addUpdateListener { animation ->
                val animatedValue = animation.animatedValue as Float
                binding.tvEmail.translationY = animatedValue
                binding.etEmail.translationY = animatedValue
                binding.tvPassword.translationY = animatedValue
                binding.textInputLayout.translationY = animatedValue
                binding.btnRegistry.translationY = animatedValue
            }

            isViewMoveUp = true
        }
    }

    private fun onKeyboardHide(){
        if (isViewMoveUp){

            val animator = ValueAnimator.ofFloat(-220f, 0f)
            animator.duration = 750
            animator.start()

            animator.addUpdateListener { animation ->
                val animatedValue = animation.animatedValue as Float
                binding.tvEmail.translationY = animatedValue
                binding.etEmail.translationY = animatedValue
                binding.tvPassword.translationY = animatedValue
                binding.textInputLayout.translationY = animatedValue
                binding.btnRegistry.translationY = animatedValue
                if(animatedValue > -2){
                    binding.ivLogo.visibility = View.VISIBLE
                }
            }

            isViewMoveUp = false
        }
    }

}
