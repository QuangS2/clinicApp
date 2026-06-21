package com.clinicmanager.domain.model.patient;

import com.clinicmanager.domain.exception.patient.InvalidPatientDataException;
import java.time.LocalDate;
import java.util.UUID;

public class Patient {
    private final UUID id;
    private final String fullName;
    private final LocalDate dob;
    private final String gender;
    private final String phone;
    private final String address;
    private final String email;

    public Patient(UUID id, String fullName, LocalDate dob, String gender, String phone, String address, String email) {
        this.id = id != null ? id : UUID.randomUUID();
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.email = email;
        validate();
    }

    private void validate() {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new InvalidPatientDataException("Họ tên không được để trống.");
        }
        if (phone == null || phone.trim().length() != 10 || !phone.trim().startsWith("0")) {
            throw new InvalidPatientDataException("Số điện thoại không hợp lệ, phải gồm 10 chữ số và bắt đầu bằng số 0.");
        }
        if (email != null && !email.trim().isEmpty() && !email.contains("@")) {
            throw new InvalidPatientDataException("Email không đúng định dạng.");
        }
    }

    public UUID getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }
}
