package live.soilandpimp.batch.annotation;

import org.springframework.context.annotation.Profile;

import live.soilandpimp.batch.util.AppConstants;

@Profile(AppConstants.TEST_PROFILE)
public @interface TestProfile {}