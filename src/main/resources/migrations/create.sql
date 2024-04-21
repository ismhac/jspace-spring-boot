
    create table tbl_admin (
        created_at timestamp(6) with time zone,
        created_by integer,
        updated_at timestamp(6) with time zone,
        updated_by integer,
        type varchar(255) not null,
        user_id integer not null,
        primary key (user_id)
    );

    create table tbl_admin_forgot_password_token (
        id serial not null,
        created_at timestamp(6) with time zone,
        created_by integer,
        updated_at timestamp(6) with time zone,
        updated_by integer,
        otp_created_date_time timestamp(6),
        token varchar(255),
        user_id integer,
        primary key (id)
    );

    create table tbl_candidate (
        created_at timestamp(6) with time zone,
        created_by integer,
        updated_at timestamp(6) with time zone,
        updated_by integer,
        user_id integer not null,
        primary key (user_id)
    );

    create table tbl_candidate_follow_company (
        created_at timestamp(6) with time zone,
        created_by integer,
        updated_at timestamp(6) with time zone,
        updated_by integer,
        company_id integer not null,
        candidate_id integer not null,
        primary key (candidate_id, company_id)
    );

    create table tbl_candidate_post (
        created_at timestamp(6) with time zone,
        created_by integer,
        updated_at timestamp(6) with time zone,
        updated_by integer,
        apply_status varchar(255),
        resume_id integer,
        post_id integer not null,
        candidate_id integer not null,
        primary key (candidate_id, post_id)
    );

    create table tbl_company (
        id serial not null,
        created_at timestamp(6) with time zone,
        created_by integer,
        updated_at timestamp(6) with time zone,
        updated_by integer,
        address varchar(255),
        background varchar(255),
        company_link varchar(255),
        description text,
        email varchar(255),
        logo varchar(255),
        name varchar(255),
        phone varchar(255),
        verified boolean,
        primary key (id)
    );

    create table tbl_company_request_review (
        created_at timestamp(6) with time zone,
        created_by integer,
        updated_at timestamp(6) with time zone,
        updated_by integer,
        employee_id integer,
        admin_id integer,
        reviewed boolean,
        company_id integer not null,
        primary key (company_id)
    );

    create table tbl_employee (
        created_at timestamp(6) with time zone,
        created_by integer,
        updated_at timestamp(6) with time zone,
        updated_by integer,
        user_id integer not null,
        company_id integer,
        primary key (user_id)
    );

    create table tbl_file (
        id serial not null,
        created_at timestamp(6) with time zone,
        created_by integer,
        updated_at timestamp(6) with time zone,
        updated_by integer,
        name varchar(255),
        path varchar(255),
        public_id varchar(255),
        size bigint not null,
        type varchar(255),
        primary key (id)
    );

    create table tbl_invalidated_token (
        id varchar(255) not null,
        expiry_time timestamp(6) not null,
        primary key (id)
    );

    create table tbl_notification (
        id bigserial not null,
        created_at timestamp(6) with time zone,
        created_by integer,
        updated_at timestamp(6) with time zone,
        updated_by integer,
        content text,
        primary key (id)
    );

    create table tbl_post (
        id serial not null,
        created_at timestamp(6) with time zone,
        created_by integer,
        updated_at timestamp(6) with time zone,
        updated_by integer,
        close_date date,
        employee_email varchar(255),
        open_date date,
        post_status varchar(255),
        company_id integer,
        primary key (id)
    );

    create table tbl_post_detail (
        created_at timestamp(6) with time zone,
        created_by integer,
        updated_at timestamp(6) with time zone,
        updated_by integer,
        description varchar(255),
        job_type varchar(255),
        location varchar(255),
        pay varchar(255),
        quantity integer not null,
        title varchar(255),
        post_id integer not null,
        primary key (post_id)
    );

    create table tbl_refresh_token (
        id serial not null,
        token varchar(255) not null,
        user_id integer not null,
        primary key (id)
    );

    create table tbl_resume (
        id serial not null,
        name varchar(255),
        candidate_id integer not null,
        file_id integer not null,
        primary key (id)
    );

    create table tbl_role (
        id serial not null,
        created_at timestamp(6) with time zone,
        created_by integer,
        updated_at timestamp(6) with time zone,
        updated_by integer,
        code varchar(255) not null,
        description varchar(255),
        name varchar(255),
        primary key (id)
    );

    create table tbl_user (
        id serial not null,
        created_at timestamp(6) with time zone,
        created_by integer,
        updated_at timestamp(6) with time zone,
        updated_by integer,
        activated boolean not null,
        email varchar(255),
        name varchar(255),
        password varchar(255),
        phone varchar(255),
        picture varchar(255),
        username varchar(255),
        role_id integer not null,
        primary key (id)
    );

    create table tbl_user_notification (
        created_at timestamp(6) with time zone,
        created_by integer,
        updated_at timestamp(6) with time zone,
        updated_by integer,
        read boolean not null,
        nofication_id bigint not null,
        user_id integer not null,
        primary key (nofication_id, user_id)
    );

    alter table if exists tbl_admin_forgot_password_token 
       drop constraint if exists UK_78h992sm7kufpjgtrc414t15b;

    alter table if exists tbl_admin_forgot_password_token 
       add constraint UK_78h992sm7kufpjgtrc414t15b unique (token);

    alter table if exists tbl_company 
       drop constraint if exists UK_bnhaad76wxwf29cgml9opl6gx;

    alter table if exists tbl_company 
       add constraint UK_bnhaad76wxwf29cgml9opl6gx unique (name);

    alter table if exists tbl_resume 
       drop constraint if exists UK_ha7bav6ty3acqoesq0wso29go;

    alter table if exists tbl_resume 
       add constraint UK_ha7bav6ty3acqoesq0wso29go unique (file_id);

    alter table if exists tbl_role 
       drop constraint if exists UK_f1hvgsc7amy6prolpjst8dd5p;

    alter table if exists tbl_role 
       add constraint UK_f1hvgsc7amy6prolpjst8dd5p unique (code);

    alter table if exists tbl_user 
       drop constraint if exists UK_npn1wf1yu1g5rjohbek375pp1;

    alter table if exists tbl_user 
       add constraint UK_npn1wf1yu1g5rjohbek375pp1 unique (email);

    alter table if exists tbl_user 
       drop constraint if exists UK_k0bty7tbcye41jpxam88q5kj2;

    alter table if exists tbl_user 
       add constraint UK_k0bty7tbcye41jpxam88q5kj2 unique (username);

    alter table if exists tbl_admin 
       add constraint FK1hjgauplayhyy10toijoa5ujo 
       foreign key (user_id) 
       references tbl_user;

    alter table if exists tbl_admin_forgot_password_token 
       add constraint FK5ti0ti2ojrtvec2ky7qjyfk14 
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

    alter table if exists tbl_employee 
       add column verified boolean;

    alter table if exists tbl_employee 
       add column verified boolean;

    alter table if exists tbl_employee 
       add column verified boolean;

    alter table if exists tbl_employee 
       add column verified boolean;

    alter table if exists tbl_employee 
       add column verified boolean;

    alter table if exists tbl_employee 
       add column verified boolean;
