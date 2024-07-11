<p align="center">
  <a href="https://jspace-fe.vercel.app/"><img src="https://i.ibb.co/Jv6WVXh/jspace-logo.png" alt="JSpace Logo"></a>
</p>

<div align="center" style="background: linear-gradient(to right, white, #4285F4); color: #4285F4; border-radius: 4px; padding: 10px;">
<div align="center" style="font-weight: bold;">
  <span style="font-size: 32px; color: #4285F4;">J</span><span style="font-size: 32px; color: white;">S</span><span style="font-size: 32px; color: #4285F4;">P</span><span style="font-size: 32px; color: white;">A</span><span style="font-size: 32px; color: #4285F4;">C</span><span style="font-size: 32px; color: white;">E</span>
</div>
<div align="center" style="color:#000; font-size: 20px; font-style: italic;">Recruitment and Job Search Management Website</div>
</div>

## Tech Stack - Tools
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)&nbsp;
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)&nbsp;
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)&nbsp;
![Google Cloud](https://img.shields.io/badge/GoogleCloud-%234285F4.svg?style=for-the-badge&logo=google-cloud&logoColor=white)&nbsp;
![Debian](https://img.shields.io/badge/Debian-D70A53?style=for-the-badge&logo=debian&logoColor=white)&nbsp;
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)&nbsp;
![Jenkins](https://img.shields.io/badge/jenkins-%232C5263.svg?style=for-the-badge&logo=jenkins&logoColor=white)&nbsp;
![Nginx](https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white)
![PayPal](https://img.shields.io/badge/PayPal-00457C?style=for-the-badge&logo=paypal&logoColor=white)&nbsp;
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)&nbsp;
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)&nbsp;
![DBeaver](https://img.shields.io/badge/DBeaver-2C8EBB?style=flat-square&logo=dbeaver&logoColor=white)&nbsp;
![StackOverflow](https://img.shields.io/badge/Stack%20Overflow-F58025?style=flat-square&logo=stackoverflow&logoColor=white)&nbsp;


## Development Team

![Backend Developer](https://img.shields.io/badge/Van%20Hien%20Le-Backend%20Developer-brightgreen)</br>
![Frontend Developer](https://img.shields.io/badge/Thanh%20Dien%20Nguyen-Frontend%20Developer-blue)
## Contact

If you have any questions or need further assistance, please contact us!
</br>
![Backend Developer](https://img.shields.io/badge/Van%20Hien%20Le-Backend%20Developer-brightgreen)
<br>
[![LinkedIn](https://img.shields.io/badge/linkedin-%230077B5.svg?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/vanhienle02/)&nbsp;
[![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)](https://github.com/ismhac)&nbsp;
[![Telegram](https://img.shields.io/badge/Telegram-2CA5E0?style=for-the-badge&logo=telegram&logoColor=white)](https://t.me/Captain_Hac)&nbsp;
[![Facebook](https://img.shields.io/badge/Facebook-%231877F2.svg?style=for-the-badge&logo=Facebook&logoColor=white)](https://www.facebook.com/ismhac)



## Objectives and Main Functions

The objective of this project is to build a recruitment and job search management website that is easy to manage and use. The main functions of the website include:

* Job posting and management
* Candidate management
* Resume management
* Job search and filtering
* User registration and authentication
* ...

## System Requirements

* Java version >= 17
* Maven version >= 3.9.5

## Links to Documents or Other Resources
<div align="center">
  <a href="https://docs.spring.io/spring-boot/index.html">
    <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat-square&logo=spring-boot&logoColor=white" alt="Spring Boot">
  </a>
  <a href="https://www.jenkins.io/doc/book/">
    <img src="https://img.shields.io/badge/Jenkins-2C5263?style=flat-square&logo=jenkins&logoColor=white" alt="Jenkins">
  </a>
  <a href="https://cloud.google.com/docs">
    <img src="https://img.shields.io/badge/Google%20Cloud-4285F4?style=flat-square&logo=google-cloud&logoColor=white" alt="Google Cloud">
  </a>
  <a href="https://www.postgresql.org/docs/">
    <img src="https://img.shields.io/badge/PostgreSQL-316192?style=flat-square&logo=postgresql&logoColor=white" alt="PostgreSQL">
  </a>
  <a href="https://stackoverflow.com/">
    <img src="https://img.shields.io/badge/Stack%20Overflow-F58025?style=flat-square&logo=stackoverflow&logoColor=white" alt="Stack Overflow">
  </a>
</div>


## CI/CD with Jenkins

### Install Java (version >= 17) [Debian/Ubuntu]

```bash
sudo apt update
sudo apt install fontconfig openjdk-17-jre
java -version
openjdk version "17.0.8" 2023-07-18
OpenJDK Runtime Environment (build 17.0.8+7-Debian-1deb12u1)
OpenJDK 64-Bit Server VM (build 17.0.8+7-Debian-1deb12u1, mixed mode, sharing)
```

### Install Docker [Debian/Ubuntu]

```bash
# Add Docker's official GPG key:
sudo apt-get update
sudo apt-get install ca-certificates curl
sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/debian/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc

# Add the repository to Apt sources:
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/debian \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update
```

```bash
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
```

```bash
sudo docker run hello-world
```

### Install Jenkins [Debian/Ubuntu]

```bash
sudo wget -O /usr/share/keyrings/jenkins-keyring.asc \
  https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key
echo "deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc]" \
  https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
  /etc/apt/sources.list.d/jenkins.list > /dev/null
sudo apt-get update
sudo apt-get install jenkins
```

### Enable, start and check status of Jenkins [Debian/Ubuntu]

```bash
sudo systemctl enable jenkins
sudo systemctl start jenkins
sudo systemctl status jenkins
```

### Access Jenkins

Access with path: http://[server ip address]:8080

Get Admin password:
```bash
sudo cat /var/lib/jenkins/secrets/initialAdminPassword
```

### Configure Jenkins

#### Create a new Jenkins user

1. Access Jenkins with the admin password
2. Click on "Manage Jenkins" in the top left corner
3. Click on "Manage Users"
4. Click on "Create User"
5. Fill in the required information
6. Click "Create User"

#### Create a new Jenkins job

1. Access Jenkins with the admin password
2. Click on "New Item" in the top left corner
3. Enter a name for the job
4. Select "Freestyle project"
5. Click "OK"
6. Configure the job settings as required

#### Configure Jenkins to build and deploy the project

1. In the Jenkins job configuration, click on "Add build step"
2. Select "Maven"
3. Enter the Maven goals
4. Click "Save"
5. Click on "Add post-build action"
6. Select "Deploy to container"
7. Enter the container details
8. Click "Save"

#### Configure Jenkins to run the job automatically

1. In the Jenkins job configuration, click on "Configure"
2. Click on "Triggers"
3. Select "Poll SCM"
4. Enter the polling schedule
5. Click "Save"
