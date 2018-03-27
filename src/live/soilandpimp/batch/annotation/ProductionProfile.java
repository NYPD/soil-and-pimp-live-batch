package live.soilandpimp.batch.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Profile;

import live.soilandpimp.batch.util.AppConstants;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Profile(AppConstants.PRODUCTION_PROFILE)
public @interface ProductionProfile {}
