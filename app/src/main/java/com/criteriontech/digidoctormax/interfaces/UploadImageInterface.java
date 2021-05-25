package com.criteriontech.digidoctormax.interfaces;

import java.util.List;

public interface UploadImageInterface {


    void onSuccess(List<?> o);

    void onError(String s);

    void onFailed(Throwable throwable);
}
