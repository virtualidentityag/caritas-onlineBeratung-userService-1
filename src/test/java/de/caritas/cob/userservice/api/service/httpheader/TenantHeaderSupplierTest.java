package de.caritas.cob.userservice.api.service.httpheader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ExtendWith(MockitoExtension.class)
class TenantHeaderSupplierTest {


}