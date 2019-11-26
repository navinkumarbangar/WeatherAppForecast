package com.example.navinbangar.sampleweatherapplication.helper

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager

/**
 * Created by Navin Bangar on 11/26/2019.
 * This class responsible for providing views extension functions
 */

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun View.hideKeyboard(context: Context) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.viewVisibleAnimator() {
    val inAnimation = AnimationUtils.loadAnimation(this.context, android.R.anim.fade_in)
    this.startAnimation(inAnimation)
    this.makeVisible()
}