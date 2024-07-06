package zetta.config.locale;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class CustomLocaleResolver extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {

    private static final String EN_LANGUAGE = "en";
    private static final String BG_LANGUAGE = "bg";

    private static final String ACCEPT_LANGUAGE_HEADER = "Accept-Language";
    private static final String CLASSPATH_LOCALE_FILES = "locale/messages";
    private static final String DEFAULT_ENCODING = "UTF-8";
    

    List<Locale> locales = Arrays.asList(new Locale(EN_LANGUAGE), new Locale(BG_LANGUAGE));

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String headerLang = request.getHeader(ACCEPT_LANGUAGE_HEADER);
        return headerLang == null || headerLang.isEmpty() ? Locale.getDefault()
                : Locale.lookup(Locale.LanguageRange.parse(headerLang), locales);
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setBasename(CLASSPATH_LOCALE_FILES);
        rs.setDefaultEncoding(DEFAULT_ENCODING);
        rs.setUseCodeAsDefaultMessage(true);
        return rs;
    }

}