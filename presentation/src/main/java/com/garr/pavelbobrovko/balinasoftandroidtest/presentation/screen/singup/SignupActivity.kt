package com.garr.pavelbobrovko.balinasoftandroidtest.presentation.screen.singup

import android.animation.ValueAnimator
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.garr.pavelbobrovko.balinasoftandroidtest.R
import com.garr.pavelbobrovko.balinasoftandroidtest.databinding.ActivitySignupBinding
import com.garr.pavelbobrovko.balinasoftandroidtest.presentation.base.BaseMvvmActivity
import com.garr.pavelbobrovko.balinasoftandroidtest.presentation.utils.AutoCompleteEmailValidator
import com.garr.pavelbobrovko.balinasoftandroidtest.presentation.utils.OpenKeyboardListener
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.rxkotlin.subscribeBy

class SignupActivity : BaseMvvmActivity<SignupViewModel,SignupRouter,ActivitySignupBinding>() {

    private var keyboardListenersAttached = false
    private var isViewMoveUp = false

    private val emailList = ArrayList<String>()
    private var isListNotEmpty = false
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private lateinit var keyboardListener: OpenKeyboardListener

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

        arrayAdapter = ArrayAdapter<String>(this
            ,R.layout.autocomplete_adapter_item
            , emailList)
        arrayAdapter.setNotifyOnChange(true)
        binding.etEmail.setAdapter(arrayAdapter)

        val disposable = RxTextView.textChangeEvents(binding.etEmail)
            .subscribeBy(
                onNext = {
                    val list = setAutocompleteList(it.text().toString())
                    setAutocompleteValidator(list)
                }
            )
        addToDisposable(disposable)
        initKeyboardListener()
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

    private fun setAutocompleteList(str: String): ArrayList<String> {

        if (str.endsWith("@", true)) {

            val tempArr = str.split("@")
            val emailPath = tempArr[0]
            val emailArray = resources.getStringArray(R.array.email_list)

            for (item in emailArray) {
                emailList.add("$emailPath@$item")
            }

            arrayAdapter.clear()
            arrayAdapter.addAll(emailList)
            isListNotEmpty = true

        } else if (isListNotEmpty && !str.contains("@")) {

            emailList.clear()
            arrayAdapter.clear()
            isListNotEmpty = false
        }

        return emailList
    }

    private fun setAutocompleteValidator(list: ArrayList<String>){
        if (!list.isEmpty()){
            val array = arrayOfNulls<String>(list.size)
            val validator = AutoCompleteEmailValidator(list.toArray(array))
            binding.etEmail.validator = validator
        }
        else binding.etEmail.validator = null
    }

    private fun initKeyboardListener(){
        keyboardListener = object : OpenKeyboardListener(binding.rootLayout){

            override fun onKeyboardShow() {
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

            override fun onKeyboardHide() {
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
    }


}
