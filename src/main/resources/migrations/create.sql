
    create table tbl_admin (
        created_by integer,
        email_verified boolean,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        type varchar(255) not null,
        primary key (user_id)
    );

    create table tbl_admin_forgot_password_token (
        created_by integer,
        id serial not null,
        updated_by integer,
        user_id integer,
        created_at timestamp(6) with time zone,
        otp_created_date_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255) unique,
        primary key (id)
    );

    create table tbl_admin_request_verify_email (
        created_by integer,
        id serial not null,
        updated_by integer,
        user_id integer,
        created_at timestamp(6) with time zone,
        otp_created_date_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255) unique,
        primary key (id)
    );

    create table tbl_candidate (
        created_by integer,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (user_id)
    );

    create table tbl_candidate_follow_company (
        candidate_id integer not null,
        company_id integer not null,
        created_by integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (candidate_id, company_id)
    );

    create table tbl_candidate_post (
        candidate_id integer not null,
        created_by integer,
        post_id integer not null,
        resume_id integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        apply_status varchar(255),
        primary key (candidate_id, post_id)
    );

    create table tbl_company (
        created_by integer,
        email_verified boolean,
        id serial not null,
        updated_by integer,
        verified_by_admin boolean,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        address varchar(255),
        background varchar(255),
        company_link varchar(255),
        description text,
        email varchar(255),
        logo varchar(255),
        name varchar(255) unique,
        phone varchar(255),
        primary key (id)
    );

    create table tbl_company_request_review (
        admin_id integer,
        company_id integer not null,
        created_by integer,
        employee_id integer,
        reviewed boolean,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (company_id)
    );

    create table tbl_employee (
        company_id integer,
        created_by integer,
        updated_by integer,
        user_id integer not null,
        verified_by_company boolean,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (user_id)
    );

    create table tbl_file (
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        size bigint not null,
        updated_at timestamp(6) with time zone,
        name varchar(255),
        path varchar(255),
        public_id varchar(255),
        type varchar(255),
        primary key (id)
    );

    create table tbl_invalidated_token (
        expiry_time timestamp(6) not null,
        id varchar(255) not null,
        primary key (id)
    );

    create table tbl_notification (
        created_by integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        id bigserial not null,
        updated_at timestamp(6) with time zone,
        content text,
        primary key (id)
    );

    create table tbl_post (
        close_date date,
        company_id integer,
        created_by integer,
        id serial not null,
        open_date date,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        employee_email varchar(255),
        post_status varchar(255),
        primary key (id)
    );

    create table tbl_post_detail (
        created_by integer,
        post_id integer not null,
        quantity integer not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        description varchar(255),
        job_type varchar(255),
        location varchar(255),
        pay varchar(255),
        title varchar(255),
        primary key (post_id)
    );

    create table tbl_refresh_token (
        id serial not null,
        user_id integer not null,
        token varchar(255) not null,
        primary key (id)
    );

    create table tbl_resume (
        candidate_id integer not null,
        file_id integer not null unique,
        id serial not null,
        name varchar(255),
        primary key (id)
    );

    create table tbl_role (
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        code varchar(255) not null unique,
        description varchar(255),
        name varchar(255),
        primary key (id)
    );

    create table tbl_user (
        activated boolean not null,
        created_by integer,
        id serial not null,
        role_id integer not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        email varchar(255) unique,
        name varchar(255),
        password varchar(255),
        phone varchar(255),
        picture varchar(255),
        username varchar(255) unique,
        primary key (id)
    );

    create table tbl_user_notification (
        created_by integer,
        read boolean not null,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        nofication_id bigint not null,
        updated_at timestamp(6) with time zone,
        primary key (user_id, nofication_id)
    );

    alter table if exists tbl_admin 
       add constraint FK1hjgauplayhyy10toijoa5ujo 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_admin_forgot_password_token 
       add constraint FK5ti0ti2ojrtvec2ky7qjyfk14 
       foreign key (user_id) 
       references tbl_admin;

    alter table if exists tbl_admin_request_verify_email 
       add constraint FKo48tfkwtjv3js9pq3lrw4h1tp 
       foreign key (user_id) 
       references tbl_admin;

    alter table if exists tbl_candidate 
       add constraint FKatxf0ytfjvpx4o26n3rrvxyav 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_candidate_follow_company 
       add constraint FKbcmjghvkl07ka0xbwgufbhhjq 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_candidate_follow_company 
       add constraint FK22ydy65pl3r5wygpymqnfk7ca 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_candidate_post 
       add constraint FKs4oddlafl6o7r1ditiaerf226 
       foreign key (post_id) 
       references tbl_post;

    alter table if exists tbl_candidate_post 
       add constraint FKd4rht4el66amy6ylar7csw9aa 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_company_request_review 
       add constraint FKsg1k6jc7trtm3uk78n9kklyej 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_employee 
       add constraint FK22fpoc2qf3iejud3165h0puvn 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_employee 
       add constraint FK6dl42na80xlc82nim6nxyo17e 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_post 
       add constraint FK65prhl7wkffcf27dyhdgh78tm 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_post_detail 
       add constraint FKdqs2859t4ut8j73wigrag0m3q 
       foreign key (post_id) 
       references tbl_post;

    alter table if exists tbl_refresh_token 
       add constraint FKjy4oisva8l5wptq3sslq6x5d1 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_resume 
       add constraint FKe6gtemy0owewsk5rltj2clcis 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_resume 
       add constraint FKbfgf4ym3gjq13upmd0mfmh3tn 
       foreign key (file_id) 
       references tbl_file;

    alter table if exists tbl_user 
       add constraint FKqyhp9ytkc0o8uapy1vtqmw350 
       foreign key (role_id) 
       references tbl_role;

    alter table if exists tbl_user_notification 
       add constraint FKrox0cgl34rmd5g5ny6m4xe7d9 
       foreign key (nofication_id) 
       references tbl_notification;

    alter table if exists tbl_user_notification 
       add constraint FKe74x5o60m3wqvulgju9duj8yf 
       foreign key (user_id) 
       references tbl_user;

    create table tbl_admin (
        created_by integer,
        email_verified boolean,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        type varchar(255) not null,
        primary key (user_id)
    );

    create table tbl_admin_forgot_password_token (
        created_by integer,
        id serial not null,
        updated_by integer,
        user_id integer,
        created_at timestamp(6) with time zone,
        otp_created_date_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255) unique,
        primary key (id)
    );

    create table tbl_admin_request_verify_email (
        created_by integer,
        id serial not null,
        updated_by integer,
        user_id integer,
        created_at timestamp(6) with time zone,
        otp_created_date_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255) unique,
        primary key (id)
    );

    create table tbl_candidate (
        created_by integer,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (user_id)
    );

    create table tbl_candidate_follow_company (
        candidate_id integer not null,
        company_id integer not null,
        created_by integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (candidate_id, company_id)
    );

    create table tbl_candidate_post (
        candidate_id integer not null,
        created_by integer,
        post_id integer not null,
        resume_id integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        apply_status varchar(255),
        primary key (candidate_id, post_id)
    );

    create table tbl_company (
        created_by integer,
        email_verified boolean,
        id serial not null,
        updated_by integer,
        verified_by_admin boolean,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        address varchar(255),
        background varchar(255),
        company_link varchar(255),
        description text,
        email varchar(255),
        logo varchar(255),
        name varchar(255) unique,
        phone varchar(255),
        primary key (id)
    );

    create table tbl_company_request_review (
        admin_id integer,
        company_id integer not null,
        created_by integer,
        employee_id integer,
        reviewed boolean,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (company_id)
    );

    create table tbl_employee (
        company_id integer,
        created_by integer,
        updated_by integer,
        user_id integer not null,
        verified_by_company boolean,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (user_id)
    );

    create table tbl_file (
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        size bigint not null,
        updated_at timestamp(6) with time zone,
        name varchar(255),
        path varchar(255),
        public_id varchar(255),
        type varchar(255),
        primary key (id)
    );

    create table tbl_invalidated_token (
        expiry_time timestamp(6) not null,
        id varchar(255) not null,
        primary key (id)
    );

    create table tbl_notification (
        created_by integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        id bigserial not null,
        updated_at timestamp(6) with time zone,
        content text,
        primary key (id)
    );

    create table tbl_post (
        close_date date,
        company_id integer,
        created_by integer,
        id serial not null,
        open_date date,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        employee_email varchar(255),
        post_status varchar(255),
        primary key (id)
    );

    create table tbl_post_detail (
        created_by integer,
        post_id integer not null,
        quantity integer not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        description varchar(255),
        job_type varchar(255),
        location varchar(255),
        pay varchar(255),
        title varchar(255),
        primary key (post_id)
    );

    create table tbl_refresh_token (
        id serial not null,
        user_id integer not null,
        token varchar(255) not null,
        primary key (id)
    );

    create table tbl_resume (
        candidate_id integer not null,
        file_id integer not null unique,
        id serial not null,
        name varchar(255),
        primary key (id)
    );

    create table tbl_role (
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        code varchar(255) not null unique,
        description varchar(255),
        name varchar(255),
        primary key (id)
    );

    create table tbl_user (
        activated boolean not null,
        created_by integer,
        id serial not null,
        role_id integer not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        email varchar(255) unique,
        name varchar(255),
        password varchar(255),
        phone varchar(255),
        picture varchar(255),
        username varchar(255) unique,
        primary key (id)
    );

    create table tbl_user_notification (
        created_by integer,
        read boolean not null,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        nofication_id bigint not null,
        updated_at timestamp(6) with time zone,
        primary key (user_id, nofication_id)
    );

    alter table if exists tbl_admin 
       add constraint FK1hjgauplayhyy10toijoa5ujo 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_admin_forgot_password_token 
       add constraint FK5ti0ti2ojrtvec2ky7qjyfk14 
       foreign key (user_id) 
       references tbl_admin;

    alter table if exists tbl_admin_request_verify_email 
       add constraint FKo48tfkwtjv3js9pq3lrw4h1tp 
       foreign key (user_id) 
       references tbl_admin;

    alter table if exists tbl_candidate 
       add constraint FKatxf0ytfjvpx4o26n3rrvxyav 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_candidate_follow_company 
       add constraint FKbcmjghvkl07ka0xbwgufbhhjq 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_candidate_follow_company 
       add constraint FK22ydy65pl3r5wygpymqnfk7ca 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_candidate_post 
       add constraint FKs4oddlafl6o7r1ditiaerf226 
       foreign key (post_id) 
       references tbl_post;

    alter table if exists tbl_candidate_post 
       add constraint FKd4rht4el66amy6ylar7csw9aa 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_company_request_review 
       add constraint FKsg1k6jc7trtm3uk78n9kklyej 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_employee 
       add constraint FK22fpoc2qf3iejud3165h0puvn 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_employee 
       add constraint FK6dl42na80xlc82nim6nxyo17e 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_post 
       add constraint FK65prhl7wkffcf27dyhdgh78tm 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_post_detail 
       add constraint FKdqs2859t4ut8j73wigrag0m3q 
       foreign key (post_id) 
       references tbl_post;

    alter table if exists tbl_refresh_token 
       add constraint FKjy4oisva8l5wptq3sslq6x5d1 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_resume 
       add constraint FKe6gtemy0owewsk5rltj2clcis 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_resume 
       add constraint FKbfgf4ym3gjq13upmd0mfmh3tn 
       foreign key (file_id) 
       references tbl_file;

    alter table if exists tbl_user 
       add constraint FKqyhp9ytkc0o8uapy1vtqmw350 
       foreign key (role_id) 
       references tbl_role;

    alter table if exists tbl_user_notification 
       add constraint FKrox0cgl34rmd5g5ny6m4xe7d9 
       foreign key (nofication_id) 
       references tbl_notification;

    alter table if exists tbl_user_notification 
       add constraint FKe74x5o60m3wqvulgju9duj8yf 
       foreign key (user_id) 
       references tbl_user;

    create table tbl_admin (
        created_by integer,
        email_verified boolean,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        type varchar(255) not null,
        primary key (user_id)
    );

    create table tbl_admin_forgot_password_token (
        created_by integer,
        id serial not null,
        updated_by integer,
        user_id integer,
        created_at timestamp(6) with time zone,
        otp_created_date_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255) unique,
        primary key (id)
    );

    create table tbl_admin_request_verify_email (
        created_by integer,
        id serial not null,
        updated_by integer,
        user_id integer,
        created_at timestamp(6) with time zone,
        otp_created_date_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255) unique,
        primary key (id)
    );

    create table tbl_candidate (
        created_by integer,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (user_id)
    );

    create table tbl_candidate_follow_company (
        candidate_id integer not null,
        company_id integer not null,
        created_by integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (candidate_id, company_id)
    );

    create table tbl_candidate_post (
        candidate_id integer not null,
        created_by integer,
        post_id integer not null,
        resume_id integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        apply_status varchar(255),
        primary key (candidate_id, post_id)
    );

    create table tbl_company (
        created_by integer,
        email_verified boolean,
        id serial not null,
        updated_by integer,
        verified_by_admin boolean,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        address varchar(255),
        background varchar(255),
        company_link varchar(255),
        description text,
        email varchar(255),
        logo varchar(255),
        name varchar(255) unique,
        phone varchar(255),
        primary key (id)
    );

    create table tbl_company_request_review (
        company_id integer not null,
        created_by integer,
        reviewed boolean,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (company_id)
    );

    create table tbl_company_verify_email_request_history (
        company_id integer not null,
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        expiry_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255),
        primary key (id)
    );

    create table tbl_employee (
        company_id integer,
        created_by integer,
        updated_by integer,
        user_id integer not null,
        verified_by_company boolean,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (user_id)
    );

    create table tbl_employee_history_request_company_verify (
        id serial not null,
        user_id integer,
        expiry_time timestamp(6),
        token varchar(255),
        primary key (id)
    );

    create table tbl_file (
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        size bigint not null,
        updated_at timestamp(6) with time zone,
        name varchar(255),
        path varchar(255),
        public_id varchar(255),
        type varchar(255),
        primary key (id)
    );

    create table tbl_invalidated_token (
        expiry_time timestamp(6) not null,
        id varchar(255) not null,
        primary key (id)
    );

    create table tbl_notification (
        created_by integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        id bigserial not null,
        updated_at timestamp(6) with time zone,
        content text,
        primary key (id)
    );

    create table tbl_post (
        close_date date,
        company_id integer,
        created_by integer,
        id serial not null,
        open_date date,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        employee_email varchar(255),
        post_status varchar(255),
        primary key (id)
    );

    create table tbl_post_detail (
        created_by integer,
        post_id integer not null,
        quantity integer not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        description varchar(255),
        job_type varchar(255),
        location varchar(255),
        pay varchar(255),
        title varchar(255),
        primary key (post_id)
    );

    create table tbl_refresh_token (
        id serial not null,
        user_id integer not null,
        token varchar(255) not null,
        primary key (id)
    );

    create table tbl_resume (
        candidate_id integer not null,
        file_id integer not null unique,
        id serial not null,
        name varchar(255),
        primary key (id)
    );

    create table tbl_role (
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        code varchar(255) not null unique,
        description varchar(255),
        name varchar(255),
        primary key (id)
    );

    create table tbl_user (
        activated boolean not null,
        created_by integer,
        id serial not null,
        role_id integer not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        email varchar(255) unique,
        name varchar(255),
        password varchar(255),
        phone varchar(255),
        picture varchar(255),
        username varchar(255) unique,
        primary key (id)
    );

    create table tbl_user_notification (
        created_by integer,
        read boolean not null,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        nofication_id bigint not null,
        updated_at timestamp(6) with time zone,
        primary key (user_id, nofication_id)
    );

    alter table if exists tbl_admin 
       add constraint FK1hjgauplayhyy10toijoa5ujo 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_admin_forgot_password_token 
       add constraint FK5ti0ti2ojrtvec2ky7qjyfk14 
       foreign key (user_id) 
       references tbl_admin;

    alter table if exists tbl_admin_request_verify_email 
       add constraint FKo48tfkwtjv3js9pq3lrw4h1tp 
       foreign key (user_id) 
       references tbl_admin;

    alter table if exists tbl_candidate 
       add constraint FKatxf0ytfjvpx4o26n3rrvxyav 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_candidate_follow_company 
       add constraint FKbcmjghvkl07ka0xbwgufbhhjq 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_candidate_follow_company 
       add constraint FK22ydy65pl3r5wygpymqnfk7ca 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_candidate_post 
       add constraint FKs4oddlafl6o7r1ditiaerf226 
       foreign key (post_id) 
       references tbl_post;

    alter table if exists tbl_candidate_post 
       add constraint FKd4rht4el66amy6ylar7csw9aa 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_company_request_review 
       add constraint FKsg1k6jc7trtm3uk78n9kklyej 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_company_verify_email_request_history 
       add constraint FKr8lle50718w1gfarugo8n2fx7 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_employee 
       add constraint FK22fpoc2qf3iejud3165h0puvn 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_employee 
       add constraint FK6dl42na80xlc82nim6nxyo17e 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_employee_history_request_company_verify 
       add constraint FK8w93jka23ytxwja8747b2l6ww 
       foreign key (user_id) 
       references tbl_employee;

    alter table if exists tbl_post 
       add constraint FK65prhl7wkffcf27dyhdgh78tm 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_post_detail 
       add constraint FKdqs2859t4ut8j73wigrag0m3q 
       foreign key (post_id) 
       references tbl_post;

    alter table if exists tbl_refresh_token 
       add constraint FKjy4oisva8l5wptq3sslq6x5d1 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_resume 
       add constraint FKe6gtemy0owewsk5rltj2clcis 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_resume 
       add constraint FKbfgf4ym3gjq13upmd0mfmh3tn 
       foreign key (file_id) 
       references tbl_file;

    alter table if exists tbl_user 
       add constraint FKqyhp9ytkc0o8uapy1vtqmw350 
       foreign key (role_id) 
       references tbl_role;

    alter table if exists tbl_user_notification 
       add constraint FKrox0cgl34rmd5g5ny6m4xe7d9 
       foreign key (nofication_id) 
       references tbl_notification;

    alter table if exists tbl_user_notification 
       add constraint FKe74x5o60m3wqvulgju9duj8yf 
       foreign key (user_id) 
       references tbl_user;

    create table tbl_admin (
        created_by integer,
        email_verified boolean,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        type varchar(255) not null,
        primary key (user_id)
    );

    create table tbl_admin_forgot_password_token (
        created_by integer,
        id serial not null,
        updated_by integer,
        user_id integer,
        created_at timestamp(6) with time zone,
        otp_created_date_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255) unique,
        primary key (id)
    );

    create table tbl_admin_request_verify_email (
        created_by integer,
        id serial not null,
        updated_by integer,
        user_id integer,
        created_at timestamp(6) with time zone,
        otp_created_date_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255) unique,
        primary key (id)
    );

    create table tbl_candidate (
        created_by integer,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (user_id)
    );

    create table tbl_candidate_follow_company (
        candidate_id integer not null,
        company_id integer not null,
        created_by integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (candidate_id, company_id)
    );

    create table tbl_candidate_post (
        candidate_id integer not null,
        created_by integer,
        post_id integer not null,
        resume_id integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        apply_status varchar(255),
        primary key (candidate_id, post_id)
    );

    create table tbl_company (
        created_by integer,
        email_verified boolean,
        id serial not null,
        updated_by integer,
        verified_by_admin boolean,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        address varchar(255),
        background varchar(255),
        company_link varchar(255),
        description text,
        email varchar(255),
        logo varchar(255),
        name varchar(255) unique,
        phone varchar(255),
        primary key (id)
    );

    create table tbl_company_request_review (
        company_id integer not null,
        created_by integer,
        reviewed boolean,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (company_id)
    );

    create table tbl_company_verify_email_request_history (
        company_id integer not null,
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        expiry_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255),
        primary key (id)
    );

    create table tbl_employee (
        company_id integer,
        created_by integer,
        updated_by integer,
        user_id integer not null,
        verified_by_company boolean,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (user_id)
    );

    create table tbl_employee_history_request_company_verify (
        id serial not null,
        user_id integer,
        expiry_time timestamp(6),
        token varchar(255),
        primary key (id)
    );

    create table tbl_file (
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        size bigint not null,
        updated_at timestamp(6) with time zone,
        name varchar(255),
        path varchar(255),
        public_id varchar(255),
        type varchar(255),
        primary key (id)
    );

    create table tbl_invalidated_token (
        expiry_time timestamp(6) not null,
        id varchar(255) not null,
        primary key (id)
    );

    create table tbl_notification (
        created_by integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        id bigserial not null,
        updated_at timestamp(6) with time zone,
        content text,
        primary key (id)
    );

    create table tbl_post (
        close_date date,
        company_id integer,
        created_by integer,
        id serial not null,
        open_date date,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        employee_email varchar(255),
        post_status varchar(255),
        primary key (id)
    );

    create table tbl_post_detail (
        created_by integer,
        post_id integer not null,
        quantity integer not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        description varchar(255),
        job_type varchar(255),
        location varchar(255),
        pay varchar(255),
        title varchar(255),
        primary key (post_id)
    );

    create table tbl_refresh_token (
        id serial not null,
        user_id integer not null,
        token varchar(255) not null,
        primary key (id)
    );

    create table tbl_resume (
        candidate_id integer not null,
        file_id integer not null unique,
        id serial not null,
        name varchar(255),
        primary key (id)
    );

    create table tbl_role (
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        code varchar(255) not null unique,
        description varchar(255),
        name varchar(255),
        primary key (id)
    );

    create table tbl_user (
        activated boolean not null,
        created_by integer,
        id serial not null,
        role_id integer not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        email varchar(255) unique,
        name varchar(255),
        password varchar(255),
        phone varchar(255),
        picture varchar(255),
        username varchar(255) unique,
        primary key (id)
    );

    create table tbl_user_notification (
        created_by integer,
        read boolean not null,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        nofication_id bigint not null,
        updated_at timestamp(6) with time zone,
        primary key (user_id, nofication_id)
    );

    alter table if exists tbl_admin 
       add constraint FK1hjgauplayhyy10toijoa5ujo 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_admin_forgot_password_token 
       add constraint FK5ti0ti2ojrtvec2ky7qjyfk14 
       foreign key (user_id) 
       references tbl_admin;

    alter table if exists tbl_admin_request_verify_email 
       add constraint FKo48tfkwtjv3js9pq3lrw4h1tp 
       foreign key (user_id) 
       references tbl_admin;

    alter table if exists tbl_candidate 
       add constraint FKatxf0ytfjvpx4o26n3rrvxyav 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_candidate_follow_company 
       add constraint FKbcmjghvkl07ka0xbwgufbhhjq 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_candidate_follow_company 
       add constraint FK22ydy65pl3r5wygpymqnfk7ca 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_candidate_post 
       add constraint FKs4oddlafl6o7r1ditiaerf226 
       foreign key (post_id) 
       references tbl_post;

    alter table if exists tbl_candidate_post 
       add constraint FKd4rht4el66amy6ylar7csw9aa 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_company_request_review 
       add constraint FKsg1k6jc7trtm3uk78n9kklyej 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_company_verify_email_request_history 
       add constraint FKr8lle50718w1gfarugo8n2fx7 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_employee 
       add constraint FK22fpoc2qf3iejud3165h0puvn 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_employee 
       add constraint FK6dl42na80xlc82nim6nxyo17e 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_employee_history_request_company_verify 
       add constraint FK8w93jka23ytxwja8747b2l6ww 
       foreign key (user_id) 
       references tbl_employee;

    alter table if exists tbl_post 
       add constraint FK65prhl7wkffcf27dyhdgh78tm 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_post_detail 
       add constraint FKdqs2859t4ut8j73wigrag0m3q 
       foreign key (post_id) 
       references tbl_post;

    alter table if exists tbl_refresh_token 
       add constraint FKjy4oisva8l5wptq3sslq6x5d1 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_resume 
       add constraint FKe6gtemy0owewsk5rltj2clcis 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_resume 
       add constraint FKbfgf4ym3gjq13upmd0mfmh3tn 
       foreign key (file_id) 
       references tbl_file;

    alter table if exists tbl_user 
       add constraint FKqyhp9ytkc0o8uapy1vtqmw350 
       foreign key (role_id) 
       references tbl_role;

    alter table if exists tbl_user_notification 
       add constraint FKrox0cgl34rmd5g5ny6m4xe7d9 
       foreign key (nofication_id) 
       references tbl_notification;

    alter table if exists tbl_user_notification 
       add constraint FKe74x5o60m3wqvulgju9duj8yf 
       foreign key (user_id) 
       references tbl_user;

    create table tbl_admin (
        created_by integer,
        email_verified boolean,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        type varchar(255) not null,
        primary key (user_id)
    );

    create table tbl_admin_forgot_password_token (
        created_by integer,
        id serial not null,
        updated_by integer,
        user_id integer,
        created_at timestamp(6) with time zone,
        otp_created_date_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255) unique,
        primary key (id)
    );

    create table tbl_admin_request_verify_email (
        created_by integer,
        id serial not null,
        updated_by integer,
        user_id integer,
        created_at timestamp(6) with time zone,
        otp_created_date_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255) unique,
        primary key (id)
    );

    create table tbl_candidate (
        created_by integer,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (user_id)
    );

    create table tbl_candidate_follow_company (
        candidate_id integer not null,
        company_id integer not null,
        created_by integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (candidate_id, company_id)
    );

    create table tbl_candidate_post (
        candidate_id integer not null,
        created_by integer,
        post_id integer not null,
        resume_id integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        apply_status varchar(255),
        primary key (candidate_id, post_id)
    );

    create table tbl_company (
        created_by integer,
        email_verified boolean,
        id serial not null,
        updated_by integer,
        verified_by_admin boolean,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        address varchar(255),
        background varchar(255),
        company_link varchar(255),
        description text,
        email varchar(255),
        logo varchar(255),
        name varchar(255) unique,
        phone varchar(255),
        primary key (id)
    );

    create table tbl_company_request_review (
        company_id integer not null,
        created_by integer,
        reviewed boolean,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (company_id)
    );

    create table tbl_company_verify_email_request_history (
        company_id integer not null,
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        expiry_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255),
        primary key (id)
    );

    create table tbl_employee (
        company_id integer,
        created_by integer,
        updated_by integer,
        user_id integer not null,
        verified_by_company boolean,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (user_id)
    );

    create table tbl_employee_history_request_company_verify (
        id serial not null,
        user_id integer,
        expiry_time timestamp(6),
        token varchar(255),
        primary key (id)
    );

    create table tbl_file (
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        size bigint not null,
        updated_at timestamp(6) with time zone,
        name varchar(255),
        path varchar(255),
        public_id varchar(255),
        type varchar(255),
        primary key (id)
    );

    create table tbl_invalidated_token (
        expiry_time timestamp(6) not null,
        id varchar(255) not null,
        primary key (id)
    );

    create table tbl_notification (
        created_by integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        id bigserial not null,
        updated_at timestamp(6) with time zone,
        content text,
        primary key (id)
    );

    create table tbl_post (
        close_date date,
        company_id integer,
        created_by integer,
        id serial not null,
        open_date date,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        employee_email varchar(255),
        post_status varchar(255),
        primary key (id)
    );

    create table tbl_post_detail (
        created_by integer,
        post_id integer not null,
        quantity integer not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        description varchar(255),
        job_type varchar(255),
        location varchar(255),
        pay varchar(255),
        title varchar(255),
        primary key (post_id)
    );

    create table tbl_refresh_token (
        id serial not null,
        user_id integer not null,
        token varchar(255) not null,
        primary key (id)
    );

    create table tbl_resume (
        candidate_id integer not null,
        file_id integer not null unique,
        id serial not null,
        name varchar(255),
        primary key (id)
    );

    create table tbl_role (
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        code varchar(255) not null unique,
        description varchar(255),
        name varchar(255),
        primary key (id)
    );

    create table tbl_user (
        activated boolean not null,
        created_by integer,
        id serial not null,
        role_id integer not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        email varchar(255) unique,
        name varchar(255),
        password varchar(255),
        phone varchar(255),
        picture varchar(255),
        username varchar(255) unique,
        primary key (id)
    );

    create table tbl_user_notification (
        created_by integer,
        read boolean not null,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        nofication_id bigint not null,
        updated_at timestamp(6) with time zone,
        primary key (user_id, nofication_id)
    );

    alter table if exists tbl_admin 
       add constraint FK1hjgauplayhyy10toijoa5ujo 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_admin_forgot_password_token 
       add constraint FK5ti0ti2ojrtvec2ky7qjyfk14 
       foreign key (user_id) 
       references tbl_admin;

    alter table if exists tbl_admin_request_verify_email 
       add constraint FKo48tfkwtjv3js9pq3lrw4h1tp 
       foreign key (user_id) 
       references tbl_admin;

    alter table if exists tbl_candidate 
       add constraint FKatxf0ytfjvpx4o26n3rrvxyav 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_candidate_follow_company 
       add constraint FKbcmjghvkl07ka0xbwgufbhhjq 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_candidate_follow_company 
       add constraint FK22ydy65pl3r5wygpymqnfk7ca 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_candidate_post 
       add constraint FKs4oddlafl6o7r1ditiaerf226 
       foreign key (post_id) 
       references tbl_post;

    alter table if exists tbl_candidate_post 
       add constraint FKd4rht4el66amy6ylar7csw9aa 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_company_request_review 
       add constraint FKsg1k6jc7trtm3uk78n9kklyej 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_company_verify_email_request_history 
       add constraint FKr8lle50718w1gfarugo8n2fx7 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_employee 
       add constraint FK22fpoc2qf3iejud3165h0puvn 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_employee 
       add constraint FK6dl42na80xlc82nim6nxyo17e 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_employee_history_request_company_verify 
       add constraint FK8w93jka23ytxwja8747b2l6ww 
       foreign key (user_id) 
       references tbl_employee;

    alter table if exists tbl_post 
       add constraint FK65prhl7wkffcf27dyhdgh78tm 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_post_detail 
       add constraint FKdqs2859t4ut8j73wigrag0m3q 
       foreign key (post_id) 
       references tbl_post;

    alter table if exists tbl_refresh_token 
       add constraint FKjy4oisva8l5wptq3sslq6x5d1 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_resume 
       add constraint FKe6gtemy0owewsk5rltj2clcis 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_resume 
       add constraint FKbfgf4ym3gjq13upmd0mfmh3tn 
       foreign key (file_id) 
       references tbl_file;

    alter table if exists tbl_user 
       add constraint FKqyhp9ytkc0o8uapy1vtqmw350 
       foreign key (role_id) 
       references tbl_role;

    alter table if exists tbl_user_notification 
       add constraint FKrox0cgl34rmd5g5ny6m4xe7d9 
       foreign key (nofication_id) 
       references tbl_notification;

    alter table if exists tbl_user_notification 
       add constraint FKe74x5o60m3wqvulgju9duj8yf 
       foreign key (user_id) 
       references tbl_user;

    create table tbl_admin (
        created_by integer,
        email_verified boolean,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        type varchar(255) not null,
        primary key (user_id)
    );

    create table tbl_admin_forgot_password_token (
        created_by integer,
        id serial not null,
        updated_by integer,
        user_id integer,
        created_at timestamp(6) with time zone,
        otp_created_date_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255) unique,
        primary key (id)
    );

    create table tbl_admin_request_verify_email (
        created_by integer,
        id serial not null,
        updated_by integer,
        user_id integer,
        created_at timestamp(6) with time zone,
        otp_created_date_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255) unique,
        primary key (id)
    );

    create table tbl_candidate (
        created_by integer,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (user_id)
    );

    create table tbl_candidate_follow_company (
        candidate_id integer not null,
        company_id integer not null,
        created_by integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (candidate_id, company_id)
    );

    create table tbl_candidate_post (
        candidate_id integer not null,
        created_by integer,
        post_id integer not null,
        resume_id integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        apply_status varchar(255),
        primary key (candidate_id, post_id)
    );

    create table tbl_company (
        created_by integer,
        email_verified boolean,
        id serial not null,
        updated_by integer,
        verified_by_admin boolean,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        address varchar(255),
        background varchar(255),
        company_link varchar(255),
        company_size varchar(255),
        description text,
        email varchar(255),
        logo varchar(255),
        name varchar(255) unique,
        phone varchar(255),
        primary key (id)
    );

    create table tbl_company_request_review (
        company_id integer not null,
        created_by integer,
        request_date date,
        reviewed boolean,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (company_id)
    );

    create table tbl_company_verify_email_request_history (
        company_id integer not null,
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        expiry_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255),
        primary key (id)
    );

    create table tbl_employee (
        company_id integer,
        created_by integer,
        updated_by integer,
        user_id integer not null,
        verified_by_company boolean,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (user_id)
    );

    create table tbl_employee_history_request_company_verify (
        id serial not null,
        user_id integer,
        expiry_time timestamp(6),
        token varchar(255),
        primary key (id)
    );

    create table tbl_file (
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        size bigint not null,
        updated_at timestamp(6) with time zone,
        name varchar(255),
        path varchar(255),
        public_id varchar(255),
        type varchar(255),
        primary key (id)
    );

    create table tbl_invalidated_token (
        expiry_time timestamp(6) not null,
        id varchar(255) not null,
        primary key (id)
    );

    create table tbl_notification (
        created_by integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        id bigserial not null,
        updated_at timestamp(6) with time zone,
        content text,
        primary key (id)
    );

    create table tbl_post (
        close_date date,
        company_id integer,
        created_by integer,
        id serial not null,
        open_date date,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        employee_email varchar(255),
        post_status varchar(255),
        primary key (id)
    );

    create table tbl_post_detail (
        created_by integer,
        post_id integer not null,
        quantity integer not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        description varchar(255),
        job_type varchar(255),
        location varchar(255),
        pay varchar(255),
        title varchar(255),
        primary key (post_id)
    );

    create table tbl_refresh_token (
        id serial not null,
        user_id integer not null,
        token varchar(255) not null,
        primary key (id)
    );

    create table tbl_resume (
        candidate_id integer not null,
        file_id integer not null unique,
        id serial not null,
        name varchar(255),
        primary key (id)
    );

    create table tbl_role (
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        code varchar(255) not null unique,
        description varchar(255),
        name varchar(255),
        primary key (id)
    );

    create table tbl_user (
        activated boolean not null,
        created_by integer,
        id serial not null,
        role_id integer not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        email varchar(255) unique,
        name varchar(255),
        password varchar(255),
        phone varchar(255),
        picture varchar(255),
        username varchar(255) unique,
        primary key (id)
    );

    create table tbl_user_notification (
        created_by integer,
        read boolean not null,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        nofication_id bigint not null,
        updated_at timestamp(6) with time zone,
        primary key (user_id, nofication_id)
    );

    alter table if exists tbl_admin 
       add constraint FK1hjgauplayhyy10toijoa5ujo 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_admin_forgot_password_token 
       add constraint FK5ti0ti2ojrtvec2ky7qjyfk14 
       foreign key (user_id) 
       references tbl_admin;

    alter table if exists tbl_admin_request_verify_email 
       add constraint FKo48tfkwtjv3js9pq3lrw4h1tp 
       foreign key (user_id) 
       references tbl_admin;

    alter table if exists tbl_candidate 
       add constraint FKatxf0ytfjvpx4o26n3rrvxyav 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_candidate_follow_company 
       add constraint FKbcmjghvkl07ka0xbwgufbhhjq 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_candidate_follow_company 
       add constraint FK22ydy65pl3r5wygpymqnfk7ca 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_candidate_post 
       add constraint FKs4oddlafl6o7r1ditiaerf226 
       foreign key (post_id) 
       references tbl_post;

    alter table if exists tbl_candidate_post 
       add constraint FKd4rht4el66amy6ylar7csw9aa 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_company_request_review 
       add constraint FKsg1k6jc7trtm3uk78n9kklyej 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_company_verify_email_request_history 
       add constraint FKr8lle50718w1gfarugo8n2fx7 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_employee 
       add constraint FK22fpoc2qf3iejud3165h0puvn 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_employee 
       add constraint FK6dl42na80xlc82nim6nxyo17e 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_employee_history_request_company_verify 
       add constraint FK8w93jka23ytxwja8747b2l6ww 
       foreign key (user_id) 
       references tbl_employee;

    alter table if exists tbl_post 
       add constraint FK65prhl7wkffcf27dyhdgh78tm 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_post_detail 
       add constraint FKdqs2859t4ut8j73wigrag0m3q 
       foreign key (post_id) 
       references tbl_post;

    alter table if exists tbl_refresh_token 
       add constraint FKjy4oisva8l5wptq3sslq6x5d1 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_resume 
       add constraint FKe6gtemy0owewsk5rltj2clcis 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_resume 
       add constraint FKbfgf4ym3gjq13upmd0mfmh3tn 
       foreign key (file_id) 
       references tbl_file;

    alter table if exists tbl_user 
       add constraint FKqyhp9ytkc0o8uapy1vtqmw350 
       foreign key (role_id) 
       references tbl_role;

    alter table if exists tbl_user_notification 
       add constraint FKrox0cgl34rmd5g5ny6m4xe7d9 
       foreign key (nofication_id) 
       references tbl_notification;

    alter table if exists tbl_user_notification 
       add constraint FKe74x5o60m3wqvulgju9duj8yf 
       foreign key (user_id) 
       references tbl_user;

    create table tbl_admin (
        created_by integer,
        email_verified boolean,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        type varchar(255) not null,
        primary key (user_id)
    );

    create table tbl_admin_forgot_password_token (
        created_by integer,
        id serial not null,
        updated_by integer,
        user_id integer,
        created_at timestamp(6) with time zone,
        otp_created_date_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255) unique,
        primary key (id)
    );

    create table tbl_admin_request_verify_email (
        created_by integer,
        id serial not null,
        updated_by integer,
        user_id integer,
        created_at timestamp(6) with time zone,
        otp_created_date_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255) unique,
        primary key (id)
    );

    create table tbl_candidate (
        created_by integer,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (user_id)
    );

    create table tbl_candidate_follow_company (
        candidate_id integer not null,
        company_id integer not null,
        created_by integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (candidate_id, company_id)
    );

    create table tbl_candidate_post (
        candidate_id integer not null,
        created_by integer,
        post_id integer not null,
        resume_id integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        apply_status varchar(255),
        primary key (candidate_id, post_id)
    );

    create table tbl_company (
        created_by integer,
        email_verified boolean,
        id serial not null,
        updated_by integer,
        verified_by_admin boolean,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        address varchar(255),
        background varchar(255),
        company_link varchar(255),
        company_size varchar(255),
        description text,
        email varchar(255),
        logo varchar(255),
        name varchar(255) unique,
        phone varchar(255),
        primary key (id)
    );

    create table tbl_company_request_review (
        company_id integer not null,
        created_by integer,
        request_date date,
        reviewed boolean,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (company_id)
    );

    create table tbl_company_verify_email_request_history (
        company_id integer not null,
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        expiry_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255),
        primary key (id)
    );

    create table tbl_employee (
        company_id integer,
        created_by integer,
        updated_by integer,
        user_id integer not null,
        verified_by_company boolean,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (user_id)
    );

    create table tbl_employee_history_request_company_verify (
        id serial not null,
        user_id integer,
        expiry_time timestamp(6),
        token varchar(255),
        primary key (id)
    );

    create table tbl_file (
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        size bigint not null,
        updated_at timestamp(6) with time zone,
        name varchar(255),
        path varchar(255),
        public_id varchar(255),
        type varchar(255),
        primary key (id)
    );

    create table tbl_invalidated_token (
        expiry_time timestamp(6) not null,
        id varchar(255) not null,
        primary key (id)
    );

    create table tbl_notification (
        created_by integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        id bigserial not null,
        updated_at timestamp(6) with time zone,
        content text,
        primary key (id)
    );

    create table tbl_post (
        close_date date,
        company_id integer,
        created_by integer,
        id serial not null,
        open_date date,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        employee_email varchar(255),
        post_status varchar(255),
        primary key (id)
    );

    create table tbl_post_detail (
        created_by integer,
        post_id integer not null,
        quantity integer not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        description varchar(255),
        job_type varchar(255),
        location varchar(255),
        pay varchar(255),
        title varchar(255),
        primary key (post_id)
    );

    create table tbl_refresh_token (
        id serial not null,
        user_id integer not null,
        token varchar(255) not null,
        primary key (id)
    );

    create table tbl_resume (
        candidate_id integer not null,
        file_id integer not null unique,
        id serial not null,
        name varchar(255),
        primary key (id)
    );

    create table tbl_role (
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        code varchar(255) not null unique,
        description varchar(255),
        name varchar(255),
        primary key (id)
    );

    create table tbl_user (
        activated boolean not null,
        created_by integer,
        id serial not null,
        role_id integer not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        email varchar(255) unique,
        name varchar(255),
        password varchar(255),
        phone varchar(255),
        picture varchar(255),
        username varchar(255) unique,
        primary key (id)
    );

    create table tbl_user_notification (
        created_by integer,
        read boolean not null,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        nofication_id bigint not null,
        updated_at timestamp(6) with time zone,
        primary key (user_id, nofication_id)
    );

    alter table if exists tbl_admin 
       add constraint FK1hjgauplayhyy10toijoa5ujo 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_admin_forgot_password_token 
       add constraint FK5ti0ti2ojrtvec2ky7qjyfk14 
       foreign key (user_id) 
       references tbl_admin;

    alter table if exists tbl_admin_request_verify_email 
       add constraint FKo48tfkwtjv3js9pq3lrw4h1tp 
       foreign key (user_id) 
       references tbl_admin;

    alter table if exists tbl_candidate 
       add constraint FKatxf0ytfjvpx4o26n3rrvxyav 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_candidate_follow_company 
       add constraint FKbcmjghvkl07ka0xbwgufbhhjq 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_candidate_follow_company 
       add constraint FK22ydy65pl3r5wygpymqnfk7ca 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_candidate_post 
       add constraint FKs4oddlafl6o7r1ditiaerf226 
       foreign key (post_id) 
       references tbl_post;

    alter table if exists tbl_candidate_post 
       add constraint FKd4rht4el66amy6ylar7csw9aa 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_company_request_review 
       add constraint FKsg1k6jc7trtm3uk78n9kklyej 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_company_verify_email_request_history 
       add constraint FKr8lle50718w1gfarugo8n2fx7 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_employee 
       add constraint FK22fpoc2qf3iejud3165h0puvn 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_employee 
       add constraint FK6dl42na80xlc82nim6nxyo17e 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_employee_history_request_company_verify 
       add constraint FK8w93jka23ytxwja8747b2l6ww 
       foreign key (user_id) 
       references tbl_employee;

    alter table if exists tbl_post 
       add constraint FK65prhl7wkffcf27dyhdgh78tm 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_post_detail 
       add constraint FKdqs2859t4ut8j73wigrag0m3q 
       foreign key (post_id) 
       references tbl_post;

    alter table if exists tbl_refresh_token 
       add constraint FKjy4oisva8l5wptq3sslq6x5d1 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_resume 
       add constraint FKe6gtemy0owewsk5rltj2clcis 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_resume 
       add constraint FKbfgf4ym3gjq13upmd0mfmh3tn 
       foreign key (file_id) 
       references tbl_file;

    alter table if exists tbl_user 
       add constraint FKqyhp9ytkc0o8uapy1vtqmw350 
       foreign key (role_id) 
       references tbl_role;

    alter table if exists tbl_user_notification 
       add constraint FKrox0cgl34rmd5g5ny6m4xe7d9 
       foreign key (nofication_id) 
       references tbl_notification;

    alter table if exists tbl_user_notification 
       add constraint FKe74x5o60m3wqvulgju9duj8yf 
       foreign key (user_id) 
       references tbl_user;

    create table tbl_admin (
        created_by integer,
        email_verified boolean,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        type varchar(255) not null,
        primary key (user_id)
    );

    create table tbl_admin_forgot_password_token (
        created_by integer,
        id serial not null,
        updated_by integer,
        user_id integer,
        created_at timestamp(6) with time zone,
        otp_created_date_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255) unique,
        primary key (id)
    );

    create table tbl_admin_request_verify_email (
        created_by integer,
        id serial not null,
        updated_by integer,
        user_id integer,
        created_at timestamp(6) with time zone,
        otp_created_date_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255) unique,
        primary key (id)
    );

    create table tbl_candidate (
        created_by integer,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (user_id)
    );

    create table tbl_candidate_follow_company (
        candidate_id integer not null,
        company_id integer not null,
        created_by integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (candidate_id, company_id)
    );

    create table tbl_candidate_post (
        candidate_id integer not null,
        created_by integer,
        post_id integer not null,
        resume_id integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        apply_status varchar(255),
        primary key (candidate_id, post_id)
    );

    create table tbl_company (
        created_by integer,
        email_verified boolean,
        id serial not null,
        updated_by integer,
        verified_by_admin boolean,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        address varchar(255),
        background varchar(255),
        company_link varchar(255),
        company_size varchar(255),
        description text,
        email varchar(255),
        logo varchar(255),
        name varchar(255) unique,
        phone varchar(255),
        primary key (id)
    );

    create table tbl_company_request_review (
        company_id integer not null,
        created_by integer,
        request_date date,
        reviewed boolean,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (company_id)
    );

    create table tbl_company_verify_email_request_history (
        company_id integer not null,
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        expiry_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255),
        primary key (id)
    );

    create table tbl_employee (
        company_id integer,
        created_by integer,
        updated_by integer,
        user_id integer not null,
        verified_by_company boolean,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (user_id)
    );

    create table tbl_employee_history_request_company_verify (
        id serial not null,
        user_id integer,
        expiry_time timestamp(6),
        token varchar(255),
        primary key (id)
    );

    create table tbl_file (
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        size bigint not null,
        updated_at timestamp(6) with time zone,
        name varchar(255),
        path varchar(255),
        public_id varchar(255),
        type varchar(255),
        primary key (id)
    );

    create table tbl_invalidated_token (
        expiry_time timestamp(6) not null,
        id varchar(255) not null,
        primary key (id)
    );

    create table tbl_notification (
        created_by integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        id bigserial not null,
        updated_at timestamp(6) with time zone,
        content text,
        primary key (id)
    );

    create table tbl_post (
        close_date date,
        company_id integer,
        created_by integer,
        id serial not null,
        open_date date,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        employee_email varchar(255),
        post_status varchar(255),
        primary key (id)
    );

    create table tbl_post_detail (
        created_by integer,
        post_id integer not null,
        quantity integer not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        description varchar(255),
        job_type varchar(255),
        location varchar(255),
        pay varchar(255),
        title varchar(255),
        primary key (post_id)
    );

    create table tbl_refresh_token (
        id serial not null,
        user_id integer not null,
        token varchar(255) not null,
        primary key (id)
    );

    create table tbl_resume (
        candidate_id integer not null,
        file_id integer not null unique,
        id serial not null,
        name varchar(255),
        primary key (id)
    );

    create table tbl_role (
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        code varchar(255) not null unique,
        description varchar(255),
        name varchar(255),
        primary key (id)
    );

    create table tbl_user (
        activated boolean not null,
        created_by integer,
        id serial not null,
        role_id integer not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        email varchar(255) unique,
        name varchar(255),
        password varchar(255),
        phone varchar(255),
        picture varchar(255),
        username varchar(255) unique,
        primary key (id)
    );

    create table tbl_user_notification (
        created_by integer,
        read boolean not null,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        nofication_id bigint not null,
        updated_at timestamp(6) with time zone,
        primary key (user_id, nofication_id)
    );

    alter table if exists tbl_admin 
       add constraint FK1hjgauplayhyy10toijoa5ujo 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_admin_forgot_password_token 
       add constraint FK5ti0ti2ojrtvec2ky7qjyfk14 
       foreign key (user_id) 
       references tbl_admin;

    alter table if exists tbl_admin_request_verify_email 
       add constraint FKo48tfkwtjv3js9pq3lrw4h1tp 
       foreign key (user_id) 
       references tbl_admin;

    alter table if exists tbl_candidate 
       add constraint FKatxf0ytfjvpx4o26n3rrvxyav 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_candidate_follow_company 
       add constraint FKbcmjghvkl07ka0xbwgufbhhjq 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_candidate_follow_company 
       add constraint FK22ydy65pl3r5wygpymqnfk7ca 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_candidate_post 
       add constraint FKs4oddlafl6o7r1ditiaerf226 
       foreign key (post_id) 
       references tbl_post;

    alter table if exists tbl_candidate_post 
       add constraint FKd4rht4el66amy6ylar7csw9aa 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_company_request_review 
       add constraint FKsg1k6jc7trtm3uk78n9kklyej 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_company_verify_email_request_history 
       add constraint FKr8lle50718w1gfarugo8n2fx7 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_employee 
       add constraint FK22fpoc2qf3iejud3165h0puvn 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_employee 
       add constraint FK6dl42na80xlc82nim6nxyo17e 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_employee_history_request_company_verify 
       add constraint FK8w93jka23ytxwja8747b2l6ww 
       foreign key (user_id) 
       references tbl_employee;

    alter table if exists tbl_post 
       add constraint FK65prhl7wkffcf27dyhdgh78tm 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_post_detail 
       add constraint FKdqs2859t4ut8j73wigrag0m3q 
       foreign key (post_id) 
       references tbl_post;

    alter table if exists tbl_refresh_token 
       add constraint FKjy4oisva8l5wptq3sslq6x5d1 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_resume 
       add constraint FKe6gtemy0owewsk5rltj2clcis 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_resume 
       add constraint FKbfgf4ym3gjq13upmd0mfmh3tn 
       foreign key (file_id) 
       references tbl_file;

    alter table if exists tbl_user 
       add constraint FKqyhp9ytkc0o8uapy1vtqmw350 
       foreign key (role_id) 
       references tbl_role;

    alter table if exists tbl_user_notification 
       add constraint FKrox0cgl34rmd5g5ny6m4xe7d9 
       foreign key (nofication_id) 
       references tbl_notification;

    alter table if exists tbl_user_notification 
       add constraint FKe74x5o60m3wqvulgju9duj8yf 
       foreign key (user_id) 
       references tbl_user;

    create table tbl_admin (
        created_by integer,
        email_verified boolean,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        type varchar(255) not null,
        primary key (user_id)
    );

    create table tbl_admin_forgot_password_token (
        created_by integer,
        id serial not null,
        updated_by integer,
        user_id integer,
        created_at timestamp(6) with time zone,
        otp_created_date_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255) unique,
        primary key (id)
    );

    create table tbl_admin_request_verify_email (
        created_by integer,
        id serial not null,
        updated_by integer,
        user_id integer,
        created_at timestamp(6) with time zone,
        otp_created_date_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255) unique,
        primary key (id)
    );

    create table tbl_candidate (
        created_by integer,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (user_id)
    );

    create table tbl_candidate_follow_company (
        candidate_id integer not null,
        company_id integer not null,
        created_by integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (candidate_id, company_id)
    );

    create table tbl_candidate_post (
        candidate_id integer not null,
        created_by integer,
        post_id integer not null,
        resume_id integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        apply_status varchar(255),
        primary key (candidate_id, post_id)
    );

    create table tbl_company (
        created_by integer,
        email_verified boolean,
        id serial not null,
        updated_by integer,
        verified_by_admin boolean,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        address varchar(255),
        background varchar(255),
        company_link varchar(255),
        company_size varchar(255),
        description text,
        email varchar(255),
        logo varchar(255),
        name varchar(255) unique,
        phone varchar(255),
        primary key (id)
    );

    create table tbl_company_request_review (
        company_id integer not null,
        created_by integer,
        request_date date,
        reviewed boolean,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (company_id)
    );

    create table tbl_company_verify_email_request_history (
        company_id integer not null,
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        expiry_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255),
        primary key (id)
    );

    create table tbl_employee (
        company_id integer,
        created_by integer,
        updated_by integer,
        user_id integer not null,
        verified_by_company boolean,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (user_id)
    );

    create table tbl_employee_history_request_company_verify (
        id serial not null,
        user_id integer,
        expiry_time timestamp(6),
        token varchar(255),
        primary key (id)
    );

    create table tbl_file (
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        size bigint not null,
        updated_at timestamp(6) with time zone,
        name varchar(255),
        path varchar(255),
        public_id varchar(255),
        type varchar(255),
        primary key (id)
    );

    create table tbl_invalidated_token (
        expiry_time timestamp(6) not null,
        id varchar(255) not null,
        primary key (id)
    );

    create table tbl_notification (
        created_by integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        id bigserial not null,
        updated_at timestamp(6) with time zone,
        content text,
        primary key (id)
    );

    create table tbl_post (
        close_date date,
        company_id integer,
        created_by integer,
        id serial not null,
        open_date date,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        employee_email varchar(255),
        post_status varchar(255),
        primary key (id)
    );

    create table tbl_post_detail (
        created_by integer,
        post_id integer not null,
        quantity integer not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        description varchar(255),
        job_type varchar(255),
        location varchar(255),
        pay varchar(255),
        title varchar(255),
        primary key (post_id)
    );

    create table tbl_refresh_token (
        id serial not null,
        user_id integer not null,
        token varchar(255) not null,
        primary key (id)
    );

    create table tbl_resume (
        candidate_id integer not null,
        file_id integer not null unique,
        id serial not null,
        name varchar(255),
        primary key (id)
    );

    create table tbl_role (
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        code varchar(255) not null unique,
        description varchar(255),
        name varchar(255),
        primary key (id)
    );

    create table tbl_user (
        activated boolean not null,
        created_by integer,
        id serial not null,
        role_id integer not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        email varchar(255) unique,
        name varchar(255),
        password varchar(255),
        phone varchar(255),
        picture varchar(255),
        username varchar(255) unique,
        primary key (id)
    );

    create table tbl_user_notification (
        created_by integer,
        read boolean not null,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        nofication_id bigint not null,
        updated_at timestamp(6) with time zone,
        primary key (user_id, nofication_id)
    );

    alter table if exists tbl_admin 
       add constraint FK1hjgauplayhyy10toijoa5ujo 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_admin_forgot_password_token 
       add constraint FK5ti0ti2ojrtvec2ky7qjyfk14 
       foreign key (user_id) 
       references tbl_admin;

    alter table if exists tbl_admin_request_verify_email 
       add constraint FKo48tfkwtjv3js9pq3lrw4h1tp 
       foreign key (user_id) 
       references tbl_admin;

    alter table if exists tbl_candidate 
       add constraint FKatxf0ytfjvpx4o26n3rrvxyav 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_candidate_follow_company 
       add constraint FKbcmjghvkl07ka0xbwgufbhhjq 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_candidate_follow_company 
       add constraint FK22ydy65pl3r5wygpymqnfk7ca 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_candidate_post 
       add constraint FKs4oddlafl6o7r1ditiaerf226 
       foreign key (post_id) 
       references tbl_post;

    alter table if exists tbl_candidate_post 
       add constraint FKd4rht4el66amy6ylar7csw9aa 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_company_request_review 
       add constraint FKsg1k6jc7trtm3uk78n9kklyej 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_company_verify_email_request_history 
       add constraint FKr8lle50718w1gfarugo8n2fx7 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_employee 
       add constraint FK22fpoc2qf3iejud3165h0puvn 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_employee 
       add constraint FK6dl42na80xlc82nim6nxyo17e 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_employee_history_request_company_verify 
       add constraint FK8w93jka23ytxwja8747b2l6ww 
       foreign key (user_id) 
       references tbl_employee;

    alter table if exists tbl_post 
       add constraint FK65prhl7wkffcf27dyhdgh78tm 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_post_detail 
       add constraint FKdqs2859t4ut8j73wigrag0m3q 
       foreign key (post_id) 
       references tbl_post;

    alter table if exists tbl_refresh_token 
       add constraint FKjy4oisva8l5wptq3sslq6x5d1 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_resume 
       add constraint FKe6gtemy0owewsk5rltj2clcis 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_resume 
       add constraint FKbfgf4ym3gjq13upmd0mfmh3tn 
       foreign key (file_id) 
       references tbl_file;

    alter table if exists tbl_user 
       add constraint FKqyhp9ytkc0o8uapy1vtqmw350 
       foreign key (role_id) 
       references tbl_role;

    alter table if exists tbl_user_notification 
       add constraint FKrox0cgl34rmd5g5ny6m4xe7d9 
       foreign key (nofication_id) 
       references tbl_notification;

    alter table if exists tbl_user_notification 
       add constraint FKe74x5o60m3wqvulgju9duj8yf 
       foreign key (user_id) 
       references tbl_user;

    create table tbl_admin (
        created_by integer,
        email_verified boolean,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        type varchar(255) not null,
        primary key (user_id)
    );

    create table tbl_admin_forgot_password_token (
        created_by integer,
        id serial not null,
        updated_by integer,
        user_id integer,
        created_at timestamp(6) with time zone,
        otp_created_date_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255) unique,
        primary key (id)
    );

    create table tbl_admin_request_verify_email (
        created_by integer,
        id serial not null,
        updated_by integer,
        user_id integer,
        created_at timestamp(6) with time zone,
        otp_created_date_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255) unique,
        primary key (id)
    );

    create table tbl_candidate (
        created_by integer,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (user_id)
    );

    create table tbl_candidate_follow_company (
        candidate_id integer not null,
        company_id integer not null,
        created_by integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (candidate_id, company_id)
    );

    create table tbl_candidate_post (
        candidate_id integer not null,
        created_by integer,
        post_id integer not null,
        resume_id integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        apply_status varchar(255),
        primary key (candidate_id, post_id)
    );

    create table tbl_company (
        created_by integer,
        email_verified boolean,
        id serial not null,
        updated_by integer,
        verified_by_admin boolean,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        address varchar(255),
        background varchar(255),
        company_link varchar(255),
        company_size varchar(255),
        description text,
        email varchar(255),
        logo varchar(255),
        name varchar(255) unique,
        phone varchar(255),
        primary key (id)
    );

    create table tbl_company_request_review (
        company_id integer not null,
        created_by integer,
        request_date date,
        reviewed boolean,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (company_id)
    );

    create table tbl_company_verify_email_request_history (
        company_id integer not null,
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        expiry_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255),
        primary key (id)
    );

    create table tbl_employee (
        company_id integer,
        created_by integer,
        updated_by integer,
        user_id integer not null,
        verified_by_company boolean,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (user_id)
    );

    create table tbl_employee_history_request_company_verify (
        id serial not null,
        user_id integer,
        expiry_time timestamp(6),
        token varchar(255),
        primary key (id)
    );

    create table tbl_file (
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        size bigint not null,
        updated_at timestamp(6) with time zone,
        name varchar(255),
        path varchar(255),
        public_id varchar(255),
        type varchar(255),
        primary key (id)
    );

    create table tbl_invalidated_token (
        expiry_time timestamp(6) not null,
        id varchar(255) not null,
        primary key (id)
    );

    create table tbl_notification (
        created_by integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        id bigserial not null,
        updated_at timestamp(6) with time zone,
        content text,
        primary key (id)
    );

    create table tbl_post (
        close_date date,
        company_id integer,
        created_by integer,
        id serial not null,
        open_date date,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        employee_email varchar(255),
        post_status varchar(255),
        primary key (id)
    );

    create table tbl_post_detail (
        created_by integer,
        post_id integer not null,
        quantity integer not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        description varchar(255),
        job_type varchar(255),
        location varchar(255),
        pay varchar(255),
        title varchar(255),
        primary key (post_id)
    );

    create table tbl_refresh_token (
        id serial not null,
        user_id integer not null,
        token varchar(255) not null,
        primary key (id)
    );

    create table tbl_resume (
        candidate_id integer not null,
        file_id integer not null unique,
        id serial not null,
        name varchar(255),
        primary key (id)
    );

    create table tbl_role (
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        code varchar(255) not null unique,
        description varchar(255),
        name varchar(255),
        primary key (id)
    );

    create table tbl_user (
        activated boolean not null,
        created_by integer,
        id serial not null,
        role_id integer not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        email varchar(255) unique,
        name varchar(255),
        password varchar(255),
        phone varchar(255),
        picture varchar(255),
        username varchar(255) unique,
        primary key (id)
    );

    create table tbl_user_notification (
        created_by integer,
        read boolean not null,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        nofication_id bigint not null,
        updated_at timestamp(6) with time zone,
        primary key (user_id, nofication_id)
    );

    alter table if exists tbl_admin 
       add constraint FK1hjgauplayhyy10toijoa5ujo 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_admin_forgot_password_token 
       add constraint FK5ti0ti2ojrtvec2ky7qjyfk14 
       foreign key (user_id) 
       references tbl_admin;

    alter table if exists tbl_admin_request_verify_email 
       add constraint FKo48tfkwtjv3js9pq3lrw4h1tp 
       foreign key (user_id) 
       references tbl_admin;

    alter table if exists tbl_candidate 
       add constraint FKatxf0ytfjvpx4o26n3rrvxyav 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_candidate_follow_company 
       add constraint FKbcmjghvkl07ka0xbwgufbhhjq 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_candidate_follow_company 
       add constraint FK22ydy65pl3r5wygpymqnfk7ca 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_candidate_post 
       add constraint FKs4oddlafl6o7r1ditiaerf226 
       foreign key (post_id) 
       references tbl_post;

    alter table if exists tbl_candidate_post 
       add constraint FKd4rht4el66amy6ylar7csw9aa 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_company_request_review 
       add constraint FKsg1k6jc7trtm3uk78n9kklyej 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_company_verify_email_request_history 
       add constraint FKr8lle50718w1gfarugo8n2fx7 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_employee 
       add constraint FK22fpoc2qf3iejud3165h0puvn 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_employee 
       add constraint FK6dl42na80xlc82nim6nxyo17e 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_employee_history_request_company_verify 
       add constraint FK8w93jka23ytxwja8747b2l6ww 
       foreign key (user_id) 
       references tbl_employee;

    alter table if exists tbl_post 
       add constraint FK65prhl7wkffcf27dyhdgh78tm 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_post_detail 
       add constraint FKdqs2859t4ut8j73wigrag0m3q 
       foreign key (post_id) 
       references tbl_post;

    alter table if exists tbl_refresh_token 
       add constraint FKjy4oisva8l5wptq3sslq6x5d1 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_resume 
       add constraint FKe6gtemy0owewsk5rltj2clcis 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_resume 
       add constraint FKbfgf4ym3gjq13upmd0mfmh3tn 
       foreign key (file_id) 
       references tbl_file;

    alter table if exists tbl_user 
       add constraint FKqyhp9ytkc0o8uapy1vtqmw350 
       foreign key (role_id) 
       references tbl_role;

    alter table if exists tbl_user_notification 
       add constraint FKrox0cgl34rmd5g5ny6m4xe7d9 
       foreign key (nofication_id) 
       references tbl_notification;

    alter table if exists tbl_user_notification 
       add constraint FKe74x5o60m3wqvulgju9duj8yf 
       foreign key (user_id) 
       references tbl_user;

    create table tbl_admin (
        created_by integer,
        email_verified boolean,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        type varchar(255) not null,
        primary key (user_id)
    );

    create table tbl_admin_forgot_password_token (
        created_by integer,
        id serial not null,
        updated_by integer,
        user_id integer,
        created_at timestamp(6) with time zone,
        otp_created_date_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255) unique,
        primary key (id)
    );

    create table tbl_admin_request_verify_email (
        created_by integer,
        id serial not null,
        updated_by integer,
        user_id integer,
        created_at timestamp(6) with time zone,
        otp_created_date_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255) unique,
        primary key (id)
    );

    create table tbl_candidate (
        created_by integer,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (user_id)
    );

    create table tbl_candidate_follow_company (
        candidate_id integer not null,
        company_id integer not null,
        created_by integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (candidate_id, company_id)
    );

    create table tbl_candidate_post (
        candidate_id integer not null,
        created_by integer,
        post_id integer not null,
        resume_id integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        apply_status varchar(255),
        primary key (candidate_id, post_id)
    );

    create table tbl_company (
        created_by integer,
        email_verified boolean,
        id serial not null,
        updated_by integer,
        verified_by_admin boolean,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        address varchar(255),
        background varchar(255),
        company_link varchar(255),
        company_size varchar(255),
        description text,
        email varchar(255),
        logo varchar(255),
        name varchar(255) unique,
        phone varchar(255),
        primary key (id)
    );

    create table tbl_company_request_review (
        company_id integer not null,
        created_by integer,
        request_date date,
        reviewed boolean,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (company_id)
    );

    create table tbl_company_verify_email_request_history (
        company_id integer not null,
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        expiry_time timestamp(6),
        updated_at timestamp(6) with time zone,
        token varchar(255),
        primary key (id)
    );

    create table tbl_employee (
        company_id integer,
        created_by integer,
        updated_by integer,
        user_id integer not null,
        verified_by_company boolean,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        primary key (user_id)
    );

    create table tbl_employee_history_request_company_verify (
        id serial not null,
        user_id integer,
        expiry_time timestamp(6),
        token varchar(255),
        primary key (id)
    );

    create table tbl_file (
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        size bigint not null,
        updated_at timestamp(6) with time zone,
        name varchar(255),
        path varchar(255),
        public_id varchar(255),
        type varchar(255),
        primary key (id)
    );

    create table tbl_invalidated_token (
        expiry_time timestamp(6) not null,
        id varchar(255) not null,
        primary key (id)
    );

    create table tbl_notification (
        created_by integer,
        updated_by integer,
        created_at timestamp(6) with time zone,
        id bigserial not null,
        updated_at timestamp(6) with time zone,
        content text,
        primary key (id)
    );

    create table tbl_post (
        close_date date,
        company_id integer,
        created_by integer,
        id serial not null,
        open_date date,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        employee_email varchar(255),
        post_status varchar(255),
        primary key (id)
    );

    create table tbl_post_detail (
        created_by integer,
        post_id integer not null,
        quantity integer not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        description varchar(255),
        job_type varchar(255),
        location varchar(255),
        pay varchar(255),
        title varchar(255),
        primary key (post_id)
    );

    create table tbl_refresh_token (
        id serial not null,
        user_id integer not null,
        token varchar(255) not null,
        primary key (id)
    );

    create table tbl_resume (
        candidate_id integer not null,
        file_id integer not null unique,
        id serial not null,
        name varchar(255),
        primary key (id)
    );

    create table tbl_role (
        created_by integer,
        id serial not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        code varchar(255) not null unique,
        description varchar(255),
        name varchar(255),
        primary key (id)
    );

    create table tbl_user (
        activated boolean not null,
        created_by integer,
        id serial not null,
        role_id integer not null,
        updated_by integer,
        created_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        email varchar(255) unique,
        name varchar(255),
        password varchar(255),
        phone varchar(255),
        picture varchar(255),
        username varchar(255) unique,
        primary key (id)
    );

    create table tbl_user_notification (
        created_by integer,
        read boolean not null,
        updated_by integer,
        user_id integer not null,
        created_at timestamp(6) with time zone,
        nofication_id bigint not null,
        updated_at timestamp(6) with time zone,
        primary key (user_id, nofication_id)
    );

    alter table if exists tbl_admin 
       add constraint FK1hjgauplayhyy10toijoa5ujo 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_admin_forgot_password_token 
       add constraint FK5ti0ti2ojrtvec2ky7qjyfk14 
       foreign key (user_id) 
       references tbl_admin;

    alter table if exists tbl_admin_request_verify_email 
       add constraint FKo48tfkwtjv3js9pq3lrw4h1tp 
       foreign key (user_id) 
       references tbl_admin;

    alter table if exists tbl_candidate 
       add constraint FKatxf0ytfjvpx4o26n3rrvxyav 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_candidate_follow_company 
       add constraint FKbcmjghvkl07ka0xbwgufbhhjq 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_candidate_follow_company 
       add constraint FK22ydy65pl3r5wygpymqnfk7ca 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_candidate_post 
       add constraint FKs4oddlafl6o7r1ditiaerf226 
       foreign key (post_id) 
       references tbl_post;

    alter table if exists tbl_candidate_post 
       add constraint FKd4rht4el66amy6ylar7csw9aa 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_company_request_review 
       add constraint FKsg1k6jc7trtm3uk78n9kklyej 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_company_verify_email_request_history 
       add constraint FKr8lle50718w1gfarugo8n2fx7 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_employee 
       add constraint FK22fpoc2qf3iejud3165h0puvn 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_employee 
       add constraint FK6dl42na80xlc82nim6nxyo17e 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_employee_history_request_company_verify 
       add constraint FK8w93jka23ytxwja8747b2l6ww 
       foreign key (user_id) 
       references tbl_employee;

    alter table if exists tbl_post 
       add constraint FK65prhl7wkffcf27dyhdgh78tm 
       foreign key (company_id) 
       references tbl_company;

    alter table if exists tbl_post_detail 
       add constraint FKdqs2859t4ut8j73wigrag0m3q 
       foreign key (post_id) 
       references tbl_post;

    alter table if exists tbl_refresh_token 
       add constraint FKjy4oisva8l5wptq3sslq6x5d1 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_resume 
       add constraint FKe6gtemy0owewsk5rltj2clcis 
       foreign key (candidate_id) 
       references tbl_candidate;

    alter table if exists tbl_resume 
       add constraint FKbfgf4ym3gjq13upmd0mfmh3tn 
       foreign key (file_id) 
       references tbl_file;

    alter table if exists tbl_user 
       add constraint FKqyhp9ytkc0o8uapy1vtqmw350 
       foreign key (role_id) 
       references tbl_role;

    alter table if exists tbl_user_notification 
       add constraint FKrox0cgl34rmd5g5ny6m4xe7d9 
       foreign key (nofication_id) 
       references tbl_notification;

    alter table if exists tbl_user_notification 
       add constraint FKe74x5o60m3wqvulgju9duj8yf 
       foreign key (user_id) 
       references tbl_user;
