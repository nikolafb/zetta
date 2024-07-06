package zetta.util.locale;

import java.util.Locale;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class LocaleUtil {

    private static ResourceBundleMessageSource messageSource;

    LocaleUtil(ResourceBundleMessageSource messageSource) {
        LocaleUtil.messageSource = messageSource;
    }

    public static String getLocaleMassage(String msg) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msg, null, locale);
    }

}