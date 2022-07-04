package org.openmrs.module.licensemanagement.web.taglib;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.openmrs.api.context.Context;
import org.openmrs.module.licensemanagement.api.LicenseService;
import org.openmrs.module.licensemanagement.web.taglib.fieldgen.LicenseHandler;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Context.class})
public class LicenseHandlerTest {

  @Mock private LicenseService licenseService;

  @Before
  public void setUp() {
    mockStatic(Context.class);

    when(Context.getService(LicenseService.class)).thenReturn(licenseService);
  }

  @Test
  public void shouldLoadAdditionalData() {
    new LicenseHandler().loadAdditionalData();

    verify(licenseService).getAllLicenses(true);
  }
}
