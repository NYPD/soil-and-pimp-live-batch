package live.soilandpimp.batch.annotation;

import org.springframework.context.annotation.Profile;

import live.soilandpimp.batch.util.AppConstants;

@Profile(AppConstants.PRODUCTION_PROFILE)
public @interface ProductionProfile {}
