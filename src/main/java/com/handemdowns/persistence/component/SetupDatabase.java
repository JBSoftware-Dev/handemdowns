package com.handemdowns.persistence.component;

import com.handemdowns.persistence.dao.*;
import com.handemdowns.persistence.model.Category;
import com.handemdowns.persistence.model.Permission;
import com.handemdowns.persistence.model.Role;
import com.handemdowns.persistence.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;

@Component
public class SetupDatabase implements ApplicationListener<ContextRefreshedEvent> {
	private final Logger log = LoggerFactory.getLogger(getClass());

    private PermissionRepository permissionRepository;
	private RoleRepository roleRepository;
	private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private LocationRepository locationRepository;
	private MapPlotRepository mapPlotRepository;
	private PasswordEncoder passwordEncoder;

    @Value("${server.datasource.initialize}")
    private String initialize;

	@Value("${handemdowns.security.adminAccount}")
	private String adminAccount;

	@Value("${handemdowns.security.adminPassword}")
	private String adminPassword;

    private boolean alreadySetup;

	@Autowired
	public SetupDatabase(PermissionRepository permissionRepository, RoleRepository roleRepository, UserRepository userRepository, CategoryRepository categoryRepository,
						 LocationRepository locationRepository, MapPlotRepository mapPlotRepository, PasswordEncoder passwordEncoder) {
		this.permissionRepository = permissionRepository;
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.categoryRepository = categoryRepository;
		this.locationRepository = locationRepository;
		this.mapPlotRepository = mapPlotRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
		if (initialize.equals("true") && !alreadySetup) {
			log.info("Initializing Handemdowns database");
			createPermissions();
			createRoles();
			createUsers();
			createCategories();
			MapBuilder mapBuilder = new MapBuilder(locationRepository, mapPlotRepository);
			mapBuilder.buildMap();
			log.info("Initialization for Handemdowns database is complete");

			alreadySetup = true;
			return;
		}

		MapBuilder mapBuilder = new MapBuilder(locationRepository, mapPlotRepository);
		mapBuilder.buildMap();
    }

    @Transactional
    private void createPermissions() {
		if (!permissionRepository.findAll().isEmpty()) {
			log.info("Skipping Permissions, table contains data");
			return;
		}
		log.info("Creating {}", permissionRepository.save(Permission.builder("PERMISSION_ADMIN").build()));
		log.info("Creating {}", permissionRepository.save(Permission.builder("PERMISSION_USER").build()));
    }

    @Transactional
    private void createRoles() {
		if (!roleRepository.findAll().isEmpty()) {
			log.info("Skipping Roles, table contains data");
			return;
		}
		log.info("Creating {}", roleRepository.save(Role.builder("ROLE_ADMIN")
				.permissions(Arrays.asList(permissionRepository.findByName("PERMISSION_ADMIN"), permissionRepository.findByName("PERMISSION_USER")))
				.build()));
		log.info("Creating {}", roleRepository.save(Role.builder("ROLE_USER")
				.permissions(Collections.singletonList(permissionRepository.findByName("PERMISSION_USER")))
				.build()));
    }
    
    @Transactional
    private void createUsers() {
		if (!userRepository.findAll().isEmpty()) {
			log.info("Skipping Users, table contains data");
			return;
		}
		log.info("Creating {}", userRepository.save(User.builder(adminAccount, "Admin", passwordEncoder.encode(adminPassword))
				.roles(Arrays.asList(roleRepository.findByName("ROLE_ADMIN"), roleRepository.findByName("ROLE_USER")))
				.enabled(true)
				.tokenExpired(false)
				.build()));
    }
    
    @Transactional
    private void createCategories() {
		if (!categoryRepository.findAll().isEmpty()) {
			log.info("Skipping Categories, table contains data");
			return;
		}
		log.info("Creating {}", categoryRepository.save(Category.builder("APP", "Appliances").build()));
		log.info("Creating {}", categoryRepository.save(Category.builder("BBY", "Baby Items").build()));
		log.info("Creating {}", categoryRepository.save(Category.builder("BKS", "Books").build()));
		log.info("Creating {}", categoryRepository.save(Category.builder("CLO", "Clothes").build()));
		log.info("Creating {}", categoryRepository.save(Category.builder("ELE", "Electronics").build()));
		log.info("Creating {}", categoryRepository.save(Category.builder("FNT", "Furniture").build()));
		log.info("Creating {}", categoryRepository.save(Category.builder("IEQ", "Industrial Equipment").build()));
		log.info("Creating {}", categoryRepository.save(Category.builder("MUS", "Music Instruments").build()));
		log.info("Creating {}", categoryRepository.save(Category.builder("SEQ", "Sports Equipment").build()));
		log.info("Creating {}", categoryRepository.save(Category.builder("TCK", "Tickets").build()));
		log.info("Creating {}", categoryRepository.save(Category.builder("TLS", "Tools").build()));
		log.info("Creating {}", categoryRepository.save(Category.builder("TYS", "Toys").build()));
		log.info("Creating {}", categoryRepository.save(Category.builder("OTH", "Other").build()));
    }
}