package com.mooveit.android.ui.recycler;

import android.view.View;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;

@Implements(
        className = "com.nineoldandroids.view.ViewPropertyAnimatorPreHC"
)
public class ViewPropertyAnimatorShadow {

    private Animator.AnimatorListener mListener;
    private Animator mAnimator;
    private View mView;

    @RealObject
    private ViewPropertyAnimator mRealViewPropertyAnimator;

    public void __constructor__(View view) {
        mView = view;
    }

    @Implementation
    public ViewPropertyAnimator setListener(Animator.AnimatorListener listener) {
        this.mListener = listener;
        return mRealViewPropertyAnimator;
    }

    @Implementation
    public void start() {
        mAnimator = ValueAnimator.ofFloat(1.0f);
    }

    public Animator.AnimatorListener getListener() {
        return mListener;
    }

    public void invokeAnimationEnd() {
        mListener.onAnimationEnd(mAnimator);
    }

    @Implementation
    public ViewPropertyAnimator x(float value) {
        ViewHelper.setX(mView, value);
        return mRealViewPropertyAnimator;
    }

    @Implementation
    public  ViewPropertyAnimator alpha(float value) {
        ViewHelper.setAlpha(mView, value);
        return mRealViewPropertyAnimator;
    }

    @Implementation
    public ViewPropertyAnimator translationX(float value) {
        ViewHelper.setTranslationX(mView, value);
        return mRealViewPropertyAnimator;
    }
}
