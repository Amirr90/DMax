package com.criteriontech.digidoctormax.interfaces;

import com.criteriontech.digidoctormax.model.OnlineAppointmentModel;

public interface BookAppointmentInterface {
    void onAppointmentBooked(OnlineAppointmentModel appointmentModel);

    void onFailed(String msg);

    void onError(String errorMsg);

}
