package org.yosua.binfood.utils;

public class Constants {
    public static final String CHANGE_PASSWORD_SUCCESS = "Password changed successfully";
    public static final String CHANGE_PASSWORD_FAILED = "Password change failed";
    public static final String UPDATE_USER_SUCCESS = "User updated successfully";
    public static final String UPDATE_USER_FAILED = "User update failed";
    public static final String GET_AUTHENTICATED_USER_FAILED = "Failed to get authenticated user";
    public static final String GET_AUTHENTICATED_USER_SUCCESS = "Successfully get authenticated user";
    public static final String DELETE_USER_SUCCESS = "User deleted successfully";

    public static String merchantNotFoundMessage(String id) {
        return "Merchant with id " + id + " not found";
    }
    public static String productNotFoundMessage(String id) {
        return "Product with id " + id + " not found";
    }
    public static String productNameAlreadyExistsMessage(String name) {
        return "Product with name " + name + " already exists";
    }
}
