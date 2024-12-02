package com.laya.ExcelVersion.constants;

public class ErrorMessages {
    //SSO JWT Error Messages
    public static final String MISSING_EMAIL_ATTRIBUTE = "Email attribute is missing from OIDC user details.";
    public static final String USER_NOT_FOUND = "User not found in the database with email: ";
    public static final String EMAIL_NOT_NULL = "Email cannot be null";
    public static final String PRACTICE_NOT_NULL = "Practice cannot be null";
    public static final String ACCESS_DENIED = "Access denied";

    //Current Maturity Error Messages
    public static final String USER_NOT_FOUND_IN_MASTER_EXCEL = "User not found in master excel with Uid: ";
    public static final String CURRENT_MATURITY_INVALID_DATA = "You are trying to save invalid data in current maturity";
    public static final String CURRENT_MATURITY_INVALID_OPERATION = "You are trying to do an Invalid Operation: ";

    public static final String CORRUPTED_EXCEL_FILE_ERROR = "Error reading excel file, please make sure that the file is not corrupted and also a valid excel file";
    public static final String INVALID_FILE_FORMAT_ERROR = "Invalid file name or format and Please make sure that the file name is in the correct format and also a valid excel file";
    public static final String EMPTY_FILE_ERROR = "Uploaded Excel file is empty and Please make sure that the file is not empty";
    private ErrorMessages() {}
}