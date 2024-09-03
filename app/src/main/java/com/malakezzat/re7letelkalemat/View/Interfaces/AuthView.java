package com.malakezzat.re7letelkalemat.View.Interfaces;

public interface AuthView {
    void showLoading();
    void hideLoading();
    void showToast(String message);
    void navigateToHome(String email);
    void setEmailError(String error);
    void setPasswordError(String error);
}
