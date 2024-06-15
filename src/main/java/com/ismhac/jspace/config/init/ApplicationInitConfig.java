package com.ismhac.jspace.config.init;

import com.ismhac.jspace.service.AdminService;
import com.ismhac.jspace.service.RoleService;
import com.ismhac.jspace.service.common.PaypalUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {
    private final AdminService adminService;
    private final RoleService roleService;
    private final PaypalUtils paypalUtils;

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            roleService.initRoles();
            adminService.initRootAdmin();
            try {
                paypalUtils.deleteAllWebhooks();
                paypalUtils.registerWebhook();
            } catch (IOException e) {
                log.error(e.getLocalizedMessage());
                e.printStackTrace();
            }
        };
    }
}
