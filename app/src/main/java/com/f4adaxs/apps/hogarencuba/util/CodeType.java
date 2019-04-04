package com.f4adaxs.apps.hogarencuba.util;


/**
 * Created by rigo on 3/23/18.
 */

public class CodeType {
    //token
    public final static String error_invalid_token = "error_invalid_token";
    public final static String error_token_is_required = "error_token_is_required";
    public final static String error_token_expired = "error_token_expired";
    public final static String error_token_before_valid = "error_token_before_valid";
    public final static String error_token_signature_invalid = "error_token_signature_invalid";
    //login
    public final static String error_user_and_password_required = "error_user_and_password_required";
    public final static String error_user_invalid = "error_user_invalid";
    public final static String error_password_invalid = "error_password_invalid";
    public final static String error_user_status_ban = "error_user_status_ban";
    public final static String error_user_status_noactivi = "error_user_status_noactivi";

    //inventory
    public final static String error_inventory_nivel_required = "error_inventory_nivel_required";
    public final static String error_inventory_nivel_invalid = "error_inventory_nivel_invalid";
    public final static String error_inventory_code_required = "error_inventory_code_required";
    public final static String error_inventory_code_invalid = "error_inventory_code_invalid";
    public final static String error_inventory_update_description_or_image_required = "error_inventory_update_description_or_image_required";
    public final static String error_inventory_not_uploaded_image = "error_inventory_not_uploaded_image";

    //general
    public final static String error_invalid_request = "error_invalid_request";

    public static int getError(String error) {
        int result = -1;
        switch (error) {
//            case CodeType.error_invalid_token:
//                result = R.string.error_invalid_token;
//                break;
//            case CodeType.error_token_is_required:
//                result = R.string.error_token_is_required;
//                break;
//            case CodeType.error_token_expired:
//                result = R.string.error_token_expired;
//                break;
//            case CodeType.error_token_before_valid:
//                result = R.string.error_token_before_valid;
//                break;
//            case CodeType.error_token_signature_invalid:
//                result = R.string.error_token_signature_invalid;
//                break;
//            case CodeType.error_user_and_password_required:
//                result = R.string.error_user_and_password_required;
//                break;
//            case CodeType.error_user_invalid:
//                result = R.string.error_user_invalid;
//                break;
//            case CodeType.error_password_invalid:
//                result = R.string.error_password_invalid;
//                break;
//            case CodeType.error_user_status_ban:
//                result = R.string.error_user_status_ban;
//                break;
//            case CodeType.error_user_status_noactivi:
//                result = R.string.error_user_status_noactivi;
//                break;
//            case CodeType.error_inventory_nivel_required:
//                result = R.string.error_inventory_nivel_required;
//                break;
//            case CodeType.error_inventory_nivel_invalid:
//                result = R.string.error_inventory_nivel_invalid;
//                break;
//            case CodeType.error_inventory_code_required:
//                result = R.string.error_inventory_code_required;
//                break;
//            case CodeType.error_inventory_code_invalid:
//                result = R.string.error_inventory_code_invalid;
//                break;
//            case CodeType.error_inventory_update_description_or_image_required:
//                result = R.string.error_inventory_update_description_or_image_required;
//                break;
//            case CodeType.error_inventory_not_uploaded_image:
//                result = R.string.error_inventory_not_uploaded_image;
//                break;
//            case CodeType.error_invalid_request:
//                result = R.string.error_invalid_request;
//                break;
        }
        return result;
    }
}
