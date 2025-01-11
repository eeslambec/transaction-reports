package uz.pdp.transactionreports.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Callback {
    REPORTS("reports"),
    SETTINGS_AND_LOGIN_RIGHTS("settings-and-login-rights"),
    MONTHLY_INCOME("monthly-income"),
    MONTHLY_EXPENSE("monthly-expense"),
    MORE_REPORTS("more-reports"),
    BY_PERIOD("by-period"),
    BY_EXPENSE_CATEGORY("by-expense-category"),
    BY_CUSTOMER_PHONE_NUMBER("by-customer-phone-number"),
    BY_SERVICE_NAME("by-service-name"),
    CHANGE_PROFILE_DETAILS("change-profile-details"),
    CHANGE_PASSWORD("change-password"),
    PERMISSIONS("permissions"),
    ;

    private String callback;
    public static final Map<String, Callback> map = new HashMap<>();
    static {
        for (Callback c: Callback.values()) {
            map.put(c.getCallback(),c);
        }
    }
    public static Callback of(String data){
        System.out.println(data);
        return map.get(data);
    }
}
