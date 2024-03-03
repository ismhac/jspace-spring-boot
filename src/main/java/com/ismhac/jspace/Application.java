package com.ismhac.jspace;

import com.ismhac.jspace.service.AdminService;
import com.ismhac.jspace.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class Application implements CommandLineRunner {

    private final RoleService roleService;
    private final AdminService adminService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        roleService.initRoles();
        adminService.initRootAdmin();
    }
}
