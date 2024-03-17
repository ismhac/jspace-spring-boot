package com.ismhac.jspace.config.init;
import com.ismhac.jspace.service.AdminService;
import com.ismhac.jspace.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApplicationInitConfig {

    private final AdminService adminService;

    private final RoleService roleService;

    @Bean
    ApplicationRunner applicationRunner(){
        return args -> {
            roleService.initRoles();
            adminService.initRootAdmin();
        };
    }
}
